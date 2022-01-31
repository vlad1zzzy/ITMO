package dijkstra

import kotlinx.atomicfu.atomic
import java.util.*
import java.util.concurrent.Phaser
import kotlin.Comparator
import kotlin.concurrent.thread

private val NODE_DISTANCE_COMPARATOR = Comparator<Node> { o1, o2 -> Integer.compare(o1!!.distance, o2!!.distance) }

// Returns `Integer.MAX_VALUE` if a path has not been found.
fun shortestPathParallel(start: Node) {
    val workers = Runtime.getRuntime().availableProcessors()
    // The distance to the start node is `0`
    start.distance = 0
    // Create a priority (by distance) queue and add the start node into it
    val q = MultiQueue(workers, NODE_DISTANCE_COMPARATOR) // TODO replace me with a multi-queue based PQ!
    q.add(start)
    // Run worker threads and wait until the total work is done
    val onFinish = Phaser(workers + 1) // `arrive()` should be invoked at the end by each worker
    repeat(workers) {
        thread {
            while (q.getSize() > 0) {
                val cur = q.poll() ?: continue
                for (e in cur.outgoingEdges) {
                    while (true) {
                        val old = e.to.distance
                        val new = cur.distance + e.weight
                        if (new < old && e.to.casDistance(old, new)) {
                            q.add(e.to)
                            continue
                        }
                        break
                    }
                }
                q.dec()
            }
            onFinish.arrive()
        }
    }
    onFinish.arriveAndAwaitAdvance()
}

class MultiQueue<T>(private val workers: Int, private var comparator: Comparator<T>) {
    private val queues: MutableList<PriorityQueue<T>> = Collections.nCopies(workers, PriorityQueue(comparator))
    private val random = Random(0)
    private val nodes = atomic(0)

    fun poll(): T? {
        val (i, j) = genRandomPair()
        synchronized(queues[i]) {
            synchronized(queues[j]) {
                val firstEmpty = queues[i].isEmpty()
                val secondEmpty = queues[i].isEmpty()
                if (firstEmpty && secondEmpty) return null
                if (firstEmpty) return queues[j].poll()
                if (secondEmpty) return queues[i].poll()
                return queues[if (comparator.compare(queues[i].peek(), queues[j].peek()) >= 0) i else j].poll()
            }
        }
    }

    private fun genRandomPair(): Pair<Int, Int> {
        while (true) {
            val i = random.nextInt(workers)
            val j = random.nextInt(workers)
            if (i == j) return Pair(i, j)
        }
    }

    fun add(element: T) {
        val index = random.nextInt(workers)
        synchronized(queues[index]) {
            queues[index].add(element)
        }
        inc()
    }

    fun inc() {
        nodes.incrementAndGet()
    }

    fun dec() {
        nodes.decrementAndGet()
    }

    fun getSize(): Int {
        return nodes.value
    }
}