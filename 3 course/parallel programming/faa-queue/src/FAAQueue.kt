import kotlinx.atomicfu.*

class FAAQueue<T> {
    private val head: AtomicRef<Segment> // Head pointer, similarly to the Michael-Scott queue (but the first node is _not_ sentinel)
    private val tail: AtomicRef<Segment> // Tail pointer, similarly to the Michael-Scott queue

    init {
        val firstNode = Segment()
        head = atomic(firstNode)
        tail = atomic(firstNode)
    }

    /**
     * Adds the specified element [x] to the queue.
     */
    fun enqueue(x: T) {
        while (true) {
            val curTail = this.tail.value
            val nextTail = curTail.next.value
            if (nextTail == null) {
                val curIndex = curTail.enqIdx.getAndIncrement()
                if (curIndex >= SEGMENT_SIZE) {
                    if (curTail == this.tail.value && curTail.next.compareAndSet(null, Segment(x))) {
                        return
                    }
                } else if (curTail.elements[curIndex].compareAndSet(null, x)) {
                    return
                }
            } else {
                this.tail.compareAndSet(curTail, nextTail)
            }
        }
    }

    /**
     * Retrieves the first element from the queue
     * and returns it; returns `null` if the queue
     * is empty.
     */
    fun dequeue(): T? {
        while (true) {
            val curHead = this.head.value
            if (curHead.isEmpty) {
                val nextHead = curHead.next.value ?: return null
                this.head.compareAndSet(curHead, nextHead)
                continue
            }
            val curIndex = curHead.deqIdx.getAndIncrement()
            if (curIndex < SEGMENT_SIZE) {
                val element = curHead.elements[curIndex].getAndSet(DONE) ?: continue
                return element as T
            }
        }
    }

    /**
     * Returns `true` if this queue is empty;
     * `false` otherwise.
     */
    val isEmpty: Boolean get() {
        while (true) {
            if (head.value.isEmpty) {
                head.value = head.value.next.value!!
                continue
            } else {
                return false
            }
        }
    }
}

private class Segment {
    val next: AtomicRef<Segment?> = atomic(null)
    val enqIdx = atomic(0) // index for the next enqueue operation
    val deqIdx = atomic(0) // index for the next dequeue operation
    val elements = atomicArrayOfNulls<Any>(SEGMENT_SIZE)

    constructor() // for the first segment creation

    constructor(x: Any?) { // each next new segment should be constructed with an element
        enqIdx.incrementAndGet()
        elements[0].getAndSet(x)
    }

    val isEmpty: Boolean get() = deqIdx.value >= SEGMENT_SIZE

}

private val DONE = Any() // Marker for the "DONE" slot state; to avoid memory leaks
const val SEGMENT_SIZE = 2 // DO NOT CHANGE, IMPORTANT FOR TESTS

