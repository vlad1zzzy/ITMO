import kotlinx.atomicfu.*
import java.util.*

class FCPriorityQueue<E : Comparable<E>> {
    private val q = PriorityQueue<E>()
    private val combiner = atomicArrayOfNulls<Operation<E>>(16)
    private val lock = atomic(false)

    enum class Status {
        POLL, ADD, DONE
    }

    class Operation<E>(val element: E?, val op: Status)

    private fun combine(operation: Operation<E>): E? {
        var position = -1
        while (true) {
            if (position == -1) {
                for (pos in 0..15) {
                    if (combiner[pos].compareAndSet(null, operation)) {
                        position = pos
                        break
                    }
                }
            } else {
                process()
                val result = combiner[position].value
                if (result != null && result.op == Status.DONE) {
                    combiner[position].compareAndSet(result, null)
                    return result.element
                }
            }
        }
    }

    private fun process() {
        if (!lock.compareAndSet(expect = false, update = true)) return
        for (pos in 0..15) {
            val operation = combiner[pos].value
            if (operation != null && operation.op != Status.DONE) {
                combiner[pos].getAndSet(processOperation(operation))
            }
        }
        lock.compareAndSet(expect = true, update = false)
    }

    private fun processOperation(operation: Operation<E>): Operation<E> {
        if (operation.op == Status.POLL) {
            return Operation(q.poll(), Status.DONE)
        }
        q.add(operation.element)
        return Operation(operation.element, Status.DONE)
    }

    /**
     * Retrieves the element with the highest priority
     * and returns it as the result of this function;
     * returns `null` if the queue is empty.
     */
    fun poll(): E? {
        return combine(Operation(null, Status.POLL))
    }

    /**
     * Returns the element with the highest priority
     * or `null` if the queue is empty.
     */
    fun peek(): E? {
        return q.peek()
    }

    /**
     * Adds the specified element to the queue.
     */
    fun add(element: E) {
        combine(Operation(element, Status.ADD))
    }
}