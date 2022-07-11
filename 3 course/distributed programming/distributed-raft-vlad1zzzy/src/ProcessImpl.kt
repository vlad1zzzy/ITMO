package raft

import raft.Message.*
import java.lang.Integer.min
import java.lang.Integer.max
import java.util.*
import kotlin.collections.HashMap

/**
 * Raft algorithm implementation.
 * All functions are called from the single main thread.
 *
 * @author Vladislav Kryukov
 */
class ProcessImpl(private val env: Environment) : Process {
    private val storage = env.storage
    private val machine = env.machine

    private enum class ROLE {
        LEADER, FOLLOWER, CANDIDATE
    }

    private var role = ROLE.FOLLOWER
    private var currentLeader: Int? = null

    private var commitIndex: Int = 0
    private var lastApplied: Int = 0

    private var voted = 0

    private lateinit var nextIndex: IntArray
    private lateinit var matchIndex: IntArray

    private var commandToId: MutableMap<Command, Int> = HashMap()

    private var commandsToLeader = ArrayDeque<Command>()
    private var commandsSaved = ArrayDeque<Command>()

    init {
        env.startTimeout(Timeout.ELECTION_TIMEOUT)
    }

    override fun onTimeout() {
        role == ROLE.LEADER && return sendHeartbeat()
        role = ROLE.CANDIDATE
        voted = 0
        storage.writePersistentState(PersistentState(getTerm() + 1, env.processId))
        broadcast(::sendRequestVoteRpc)
        env.startTimeout(Timeout.ELECTION_TIMEOUT)
    }

    override fun onMessage(srcId: Int, message: Message) {
        when (message) {
            is AppendEntryRpc -> onAppendEntryRpc(srcId, message)
            is AppendEntryResult -> onAppendEntryResult(srcId, message)
            is RequestVoteRpc -> onRequestVoteRpc(srcId, message)
            is RequestVoteResult -> onRequestVoteResult(srcId, message)
            is ClientCommandRpc -> onClientCommandRpc(srcId, message)
            is ClientCommandResult -> onClientCommandResult(srcId, message)
        }
    }

    override fun onClientCommand(command: Command) {
        role == ROLE.LEADER && return onClientCommandLeader(command)
        commandsToLeader.add(command)
        role == ROLE.FOLLOWER && return sendForward()
    }

    private fun onAppendEntryRpc(srcId: Int, message: AppendEntryRpc) {
        val result = getAppendEntryResult(srcId, message)
        apply()
        env.send(srcId, result)
        sendForward()
        result.lastIndex?.let { env.startTimeout(Timeout.ELECTION_TIMEOUT) }
    }

    private fun getAppendEntryResult(srcId: Int, message: AppendEntryRpc): AppendEntryResult {
        message.term < getTerm() && return AppendEntryResult(getTerm(), null)
        currentLeader = srcId
        nextTerm(srcId, message)
        makeFollower()
        val entry = message.entry ?: run {
            if (message.leaderCommit > commitIndex) {
                commitIndex = min(message.leaderCommit, storage.readLastLogId().index)
            }
            return AppendEntryResult(getTerm(), message.prevLogId.index)
        }

        (storage.readLastLogId().index + 1 < entry.id.index || message.prevLogId.index + 1 != entry.id.index) &&
                return AppendEntryResult(getTerm(), null)

        if (storage.readLog(entry.id.index)?.id != entry.id) storage.appendLogEntry(entry)

        return AppendEntryResult(getTerm(), entry.id.index)
    }

    private fun onAppendEntryResult(srcId: Int, message: AppendEntryResult) {
        nextTerm(srcId, message)
        val (_, lastIndex) = message
        role != ROLE.LEADER && return
        lastIndex ?: nextIndex[srcId - 1]--
        lastIndex?.let { index ->
            if (index >= nextIndex[srcId - 1]) {
                matchIndex[srcId - 1] = index
                nextIndex[srcId - 1] = index + 1
                commitIndex = (storage.readLastLogId().index downTo commitIndex).firstOrNull(::isPrior) ?: commitIndex
                apply()
            }
        }
        sendAppendEntryRpc(srcId)
    }

    private fun isPrior(index: Int): Boolean {
        return matchIndex.count { it >= index } >= matchIndex.size / 2
    }

    private fun onRequestVoteRpc(srcId: Int, message: RequestVoteRpc) {
        message.term < getTerm() && return env.send(srcId, RequestVoteResult(getTerm(), false))
        nextTerm(srcId, message)
        val (_, votedFor) = storage.readPersistentState()
        if ((votedFor ?: srcId) == srcId && upToTerm(message.lastLogId, storage.readLastLogId())) {
            storage.writePersistentState(PersistentState(getTerm(), srcId))
            role = ROLE.CANDIDATE
            return env.send(srcId, RequestVoteResult(getTerm(), true))
        }
        return env.send(srcId, RequestVoteResult(getTerm(), false))
    }

    private fun onRequestVoteResult(srcId: Int, message: RequestVoteResult) {
        nextTerm(srcId, message)
        role != ROLE.CANDIDATE && return
        voted += message.voteGranted.compareTo(false)
        voted >= env.nProcesses / 2 && return makeLeader()
    }

    private fun onClientCommandRpc(srcId: Int, message: ClientCommandRpc) {
        nextTerm(srcId, message)
        commandToId[message.command] = srcId
        role != ROLE.LEADER && return onClientCommand(message.command)
        onClientCommandLeader(message.command)
    }


    private fun onClientCommandResult(srcId: Int, message: ClientCommandResult) {
        val command = commandsSaved.pollFirst()
        val id = commandToId.remove(command)
        id?.let { env.send(it, message) }
        id ?: env.onClientCommandResult(message.result)
        nextTerm(srcId, message)
        sendForward()
    }

    private fun sendForward() {
        currentLeader?.let {
            commandsSaved.addAll(commandsToLeader)
            processCommands(onClientCommandForward(it))
        }
    }

    private fun onClientCommandForward(leader: Int): (Command) -> Unit {
        fun curry(command: Command) {
            env.send(leader, ClientCommandRpc(getTerm(), command))
        }
        return ::curry
    }

    private fun upToTerm(first: LogId, second: LogId): Boolean {
        first.term != second.term && return first.term >= second.term
        return first.index >= second.index
    }

    private fun apply() {
        (lastApplied + 1..commitIndex).forEach { index ->
            val (_, command) = storage.readLog(index) ?: return@forEach
            val result = machine.apply(command)
            if (role == ROLE.LEADER) {
                commandToId.remove(command)?.let {
                    if (it == env.processId) {
                        env.onClientCommandResult(result)
                    } else {
                        env.send(it, ClientCommandResult(getTerm(), result))
                    }
                }
            }
        }
        lastApplied = commitIndex
    }

    private fun makeFollower() {
        role = ROLE.FOLLOWER
        env.startTimeout(Timeout.ELECTION_TIMEOUT)
    }

    private fun nextTerm(srcId: Int, message: Message) {
        if (message.term > getTerm()) {
            storage.writePersistentState(PersistentState(message.term, null))
            currentLeader = srcId
            makeFollower()
        }
    }

    private fun makeLeader() {
        role = ROLE.LEADER
        nextIndex = IntArray(env.nProcesses) { storage.readLastLogId().index + 1 }
        matchIndex = IntArray(env.nProcesses) { 0 }
        currentLeader = env.processId
        sendHeartbeat()
        processCommands(::onClientCommandLeader)
        broadcast(::sendAppendEntryRpc)
    }

    private fun sendAppendEntryRpc(destId: Int) {
        storage.readLog(nextIndex[destId - 1])?.let {
            storage.readLastLogId().index >= nextIndex[destId - 1] && return sendAppendEntryRpc(it)(destId)
        }
    }

    private fun sendHeartbeat() {
        env.startTimeout(Timeout.LEADER_HEARTBEAT_PERIOD)
        broadcast(sendAppendEntryRpc(null))
    }

    private fun sendAppendEntryRpc(logEntry: LogEntry?): (Int) -> Unit {
        fun curry(destId: Int) {
            env.send(destId, AppendEntryRpc(getTerm(), getPrevLogId(logEntry), commitIndex, logEntry))
        }
        return ::curry
    }

    private fun sendRequestVoteRpc(destId: Int) {
        env.send(destId, RequestVoteRpc(getTerm(), storage.readLastLogId()))
    }

    private fun onClientCommandLeader(command: Command) {
        val logEntry = LogEntry(LogId(storage.readLastLogId().index + 1, getTerm()), command)
        broadcast(trySendAppendEntryRpc(logEntry))
        commandToId.putIfAbsent(command, env.processId)
        storage.appendLogEntry(logEntry)
    }

    private fun trySendAppendEntryRpc(logEntry: LogEntry?): (Int) -> Unit {
        fun curry(destId: Int) {
            if (nextIndex[destId - 1] > 0) {
                sendAppendEntryRpc(logEntry)(destId)
                nextIndex[destId - 1] = 0
            }
        }
        return ::curry
    }

    private fun getTerm() = storage.readPersistentState().currentTerm

    private fun broadcast(send: (_: Int) -> Unit) = (1..env.nProcesses).filter { it != env.processId }.forEach(send)

    private fun processCommands(process: (command: Command) -> Unit) {
        generateSequence { commandsToLeader.pollFirst() }.forEach(process)
    }

    private fun getPrevLogId(logEntry: LogEntry?): LogId {
        val logId = logEntry?.id ?: return storage.readLastLogId()
        return storage.readLog(logId.index - 1)?.id ?: LogId(max(0, logId.index - 1), 0)
    }
}