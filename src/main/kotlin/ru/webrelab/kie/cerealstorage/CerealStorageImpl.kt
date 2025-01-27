package ru.webrelab.kie.cerealstorage

class CerealStorageImpl(
    override val containerCapacity: Float,
    override val storageCapacity: Float
) : CerealStorage {

    /**
     * Блок инициализации класса.
     * Выполняется сразу при создании объекта
     */
    init {
        require(containerCapacity >= 0) {
            "Ёмкость контейнера не может быть отрицательной"
        }
        require(storageCapacity >= containerCapacity) {
            "Ёмкость хранилища не должна быть меньше ёмкости одного контейнера"
        }
    }

    private val storage = mutableMapOf<Cereal, Float>()
    override fun addCereal(cereal: Cereal, amount: Float): Float {
        checkAmountIsNegative(amount)

        val containerBalance = amount - getSpace(cereal)

        val availableAmountContainers = (storageCapacity / containerCapacity).toInt()

        if (storage.size >= availableAmountContainers) {
            throw IllegalStateException("Хранилище не позволяет разместить ещё один контейнер для новой крупы")
        }
        if (containerBalance <= 0f) {
            storage.put(cereal, getAmount(cereal) + amount)
            return 0f
        } else {
            storage.put(cereal, containerCapacity)
            return containerBalance
        }


    }

    override fun getCereal(cereal: Cereal, amount: Float): Float {
        checkAmountIsNegative(amount)
        val amountInContainer = getAmount(cereal)

        if (amountInContainer == 0f) {
            return 0f
        } else if (amountInContainer < amount) {
            storage.put(cereal, 0f)
            removeContainer(cereal)
            return amountInContainer
        } else {
            storage.put(cereal, amountInContainer - amount)
            return amount
        }
    }

    override fun removeContainer(cereal: Cereal): Boolean {
        if (storage.getOrDefault(cereal, 0f) > 0f) {
            return false
        } else {
            storage.remove(cereal)
            return true
        }

    }

    override fun getAmount(cereal: Cereal): Float {
        return storage.getOrDefault(cereal, 0f)
    }

    override fun getSpace(cereal: Cereal): Float {
        val result = containerCapacity - getAmount(cereal)
        return result
    }

    override fun toString(): String {
        return "sum of cereal in a storage " + storage.values.sum()
    }

    private fun checkAmountIsNegative(amount: Float) {
        if (amount < 0f) throw IllegalArgumentException("Передано некорректное количество крупы")
    }


}
