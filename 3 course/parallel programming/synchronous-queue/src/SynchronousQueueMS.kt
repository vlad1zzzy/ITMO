import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import java.util.concurrent.atomic.AtomicReference

class SynchronousQueueMS<E> : SynchronousQueue<E> {
    private companion object {
        const val RETRY = "RETRY"
    }

    private val head: AtomicReference<Node>
    private val tail: AtomicReference<Node>

    init {
        val node = Node()
        head = AtomicReference(node)
        tail = AtomicReference(node)
    }

    override suspend fun send(element: E) {
        while (true) {
            val t = tail.get()
            if ((t == head.get() || t is Sender<*>)) {
                if (processSend(element) != RETRY) {
                    return
                }
            } else {
                val h = head.get()
                if (skip(h)) {
                    continue
                }
                val next = h.next.get()
                if (next is Receiver<*> && head.compareAndSet(h, next)) {
                    (next.action as Continuation<E>).resume(element)
                    return
                }
            }
        }
    }

    override suspend fun receive(): E {
        while (true) {
            val t = tail.get()
            if (t == head.get() || t is Receiver<*>) {
                val result = processReceive()
                if (result != null) {
                    return result
                }
            } else {
                val h = head.get()
                if (skip(h)) {
                    continue
                }
                val next = h.next.get()
                if (next is Sender<*> && h != tail.get() && head.compareAndSet(h, next)) {
                    next.action.resume(Unit)
                    return next.element as E
                }
            }
        }
    }

    private suspend fun processSend(element: E): Any {
        return suspendCoroutine sc@{ cont ->
            val offer = Sender(element, cont)
            val t = tail.get()
            if ((t == head.get() || t is Sender<*>) && t.next.compareAndSet(null, offer)) {
                tail.compareAndSet(t, offer)
            } else {
                cont.resume(RETRY)
                return@sc
            }
        }
    }

    private suspend fun processReceive(): E? {
        return suspendCoroutine sc@{ cont ->
            val offer = Receiver(cont)
            val t = tail.get()
            if ((t == head.get() || t is Receiver<*>) && t.next.compareAndSet(null, offer)) {
                tail.compareAndSet(t, offer)
            } else {
                cont.resume(null)
                return@sc
            }
        }
    }

    private fun skip(head: Node): Boolean {
        if (head == tail.get() || head.next.get() == null) {
            return true
        }
        return false
    }

    private open class Node {
        val next = AtomicReference<Node>(null)
    }

    private class Sender<E>(val element: E, val action: Continuation<Unit>) : Node()

    private class Receiver<E>(val action: Continuation<E>) : Node()
}
