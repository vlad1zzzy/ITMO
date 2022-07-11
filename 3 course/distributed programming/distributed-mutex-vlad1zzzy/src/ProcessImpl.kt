package mutex

/**
 * Distributed mutual exclusion implementation.
 * All functions are called from the single main thread.
 *
 * @author Vladislav Kryukov
 */
class ProcessImpl(private val env: Environment) : Process {
    private val forks = Array(env.nProcesses + 1) { ForkStatus.DIRTY }
        .apply {
            for (i in env.processId + 1..env.nProcesses) {
                this[i] = ForkStatus.ABSENT
            }
        }
    private val forksInProgress = BooleanArray(env.nProcesses + 1)
    private var lockRequested = false
    private var locked = false

    override fun onMessage(srcId: Int, message: Message) {
        message.parse {
            when (readEnum<MessageStatus>()) {
                MessageStatus.TO -> {
                    forks[srcId] = ForkStatus.CLEAN
                    tryLock()
                }
                MessageStatus.FROM -> {
                    forksInProgress[srcId] = locked || forks[srcId] != ForkStatus.DIRTY
                    forksInProgress[srcId] && return
                    forks[srcId] = ForkStatus.ABSENT
                    lockRequested && send(srcId, MessageStatus.FROM)
                    send(srcId, MessageStatus.TO)
                }
            }
        }
    }

    override fun onLockRequest() {
        if (!tryLock()) {
            forks.forEachIndexed { i, f -> f == ForkStatus.ABSENT && send(i, MessageStatus.FROM) }
        }
    }

    override fun onUnlockRequest() {
        unlocked()
        forks.forEachIndexed { i, _ -> forks[i] = ForkStatus.DIRTY }
        forksInProgress.forEachIndexed { i, inProgress ->
            if (inProgress) {
                forksInProgress[i] = !send(i, MessageStatus.TO)
                forks[i] = ForkStatus.ABSENT
            }
        }
    }

    private fun tryLock(): Boolean {
        lockRequested = true
        return !forks.any { it == ForkStatus.ABSENT } && locked()
    }

    private fun send(srcId: Int, status: MessageStatus): Boolean {
        env.send(srcId) { writeEnum(status) }
        return true
    }

    private fun locked(): Boolean {
        locked = true
        env.locked()
        return true
    }

    private fun unlocked() {
        lockRequested = false
        locked = false
        env.unlocked()
    }

    enum class MessageStatus { FROM, TO }
    enum class ForkStatus { ABSENT, CLEAN, DIRTY }
}
