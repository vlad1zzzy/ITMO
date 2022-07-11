import system.MergerEnvironment

class MergerImpl<T : Comparable<T>>(
    private val mergerEnvironment: MergerEnvironment<T>,
    prevStepBatches: Map<Int, List<T>>?
) : Merger<T> {
    private val batches: HashMap<Int, List<T>> = run {
        val batches: HashMap<Int, List<T>> = HashMap()
        prevStepBatches?.entries?.forEach { batches[it.key] = it.value }
        prevStepBatches ?: run {
            (0 until mergerEnvironment.dataHoldersCount).forEach {
                batches[it] = mergerEnvironment.requestBatch(it)
            }
        }
        batches
    }

    override fun mergeStep(): T? {
        val (key, batch) = batches.minByOrNull { it.value.first() } ?: return null
        if (batch.size == 1) {
            val requestedBatch = mergerEnvironment.requestBatch(key)
            if (requestedBatch.isEmpty()) {
                batches.remove(key)
            } else {
                batches[key] = requestedBatch
            }
        } else {
            batches[key] = batch.subList(1, batch.size)
        }
        return batch.first()
    }

    override fun getRemainingBatches(): Map<Int, List<T>> {
        return batches
    }
}