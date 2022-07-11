import system.DataHolderEnvironment

class DataHolderImpl<T : Comparable<T>>(
    private val keys: List<T>,
    private val dataHolderEnvironment: DataHolderEnvironment
) : DataHolder<T> {
    private var save = 0
    private var current = 0

    override fun checkpoint() {
        save = current
    }

    override fun rollBack() {
        current = save
    }

    override fun getBatch(): List<T> {
        val batch = ArrayList<T>()
        (0 until dataHolderEnvironment.batchSize).forEach { _ ->
            run {
                if (current < keys.size) {
                    batch.add(keys[current++])
                }
            }
        }
        return batch
    }
}