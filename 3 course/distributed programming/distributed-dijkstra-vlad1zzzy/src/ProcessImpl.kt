package dijkstra

import dijkstra.messages.*
import dijkstra.system.environment.Environment
import java.lang.Long.MAX_VALUE

class ProcessImpl(private val environment: Environment) : Process {
    private var distance: Long? = null
    private var parent: Int? = null
    private var children = 0
    private var balance = 0

    override fun onMessage(srcId: Int, message: Message) {
        when (message) {
            is MessageWithDistance -> {
                if (message.distance < (distance ?: MAX_VALUE)) {
                    sendRemove(srcId)
                    sendCreate(srcId)
                    distance = message.distance
                    return notifyNeighbours()
                }
                return sendReject(srcId)
            }
            is MessageCreate -> children++
            is MessageRemove -> children--
            is MessageReject -> balance--
        }
        check()
    }

    override fun getDistance(): Long? {
        return distance
    }

    override fun startComputation() {
        distance = 0
        notifyNeighbours()
    }

    private fun notifyNeighbours() {
        environment.neighbours.forEach { environment.send(it.key, MessageWithDistance(distance!! + it.value)) }
        balance += environment.neighbours.size
        check()
    }

    private fun check() {
        if (children + balance == 0) {
            parent ?: return environment.finishExecution(); sendRemove(null)
        }
    }

    private fun sendCreate(id: Int) {
        environment.send(id, MessageCreate)
        sendReject(id)
    }

    private fun sendRemove(id: Int?) {
        if (parent != null) environment.send(parent!!, MessageRemove); parent = id
    }

    private fun sendReject(id: Int) {
        environment.send(id, MessageReject)
    }
}