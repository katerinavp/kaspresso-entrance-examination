package ru.webrelab.kie.cerealstorage

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CerealStorageImplTest {

    private val storage = CerealStorageImpl(10f, 20f)

    @Test
    fun `should throw if containerCapacity is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(-4f, 10f)
        }
    }

    @Test
    fun `should throw if storageCapacity less than storageCapacity `() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(20f, 10f)
        }
    }

    @Test
    fun `should throw if amount of cereal is negative `() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.addCereal(Cereal.BUCKWHEAT, -1f)
        }
    }

    @Test
    fun `should throw if storage can not accept a new container `() {
        storage.addCereal(Cereal.BUCKWHEAT, 5f)
        storage.addCereal(Cereal.PEAS, 5f)

        assertThrows(IllegalStateException::class.java) {
            storage.addCereal(Cereal.BULGUR, 5f)

        }
    }

    @Test
    fun `getCereal should throw if amount is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.getCereal(Cereal.BULGUR, -11f)

        }
    }

    @Test
    fun `getCereal return 0f`() {
        storage.addCereal(Cereal.BULGUR, 0f)
        assertEquals(0f, storage.getCereal(Cereal.BULGUR, 10f))


    }

    @Test
    fun `getCereal return cereal when amount less available container capacity`() {
        storage.addCereal(Cereal.BULGUR, 6f)

        assertEquals(6f, storage.getCereal(Cereal.BULGUR, 10f))


    }

    @Test
    fun `getCereal return cereal when amount more available cereal`() {
        storage.addCereal(Cereal.BULGUR, 12f)
        assertEquals(10f, storage.getCereal(Cereal.BULGUR, 10f))


    }

    @Test
    fun `remove container should return false, container is not empty`() {
        storage.addCereal(Cereal.BUCKWHEAT, 2f)
        assertEquals(false, storage.removeContainer(Cereal.BUCKWHEAT))

    }

    @Test
    fun `remove container should return true, container is empty`() {
        storage.addCereal(Cereal.BUCKWHEAT, 2f)
        assertEquals(true, storage.removeContainer(Cereal.BULGUR))

    }

    @Test
    fun `get amount should return amount of cereal from container`() {
        storage.addCereal(Cereal.BUCKWHEAT, 2f)
        assertEquals(2f, storage.getAmount(Cereal.BUCKWHEAT))

    }

    @Test
    fun `get space should return free space in container`() {
        storage.addCereal(Cereal.BUCKWHEAT, 2f)
        assertEquals(8f, storage.getSpace(Cereal.BUCKWHEAT))

    }

    @Test
    fun `toString should return string`() {
        storage.addCereal(Cereal.BUCKWHEAT, 2f)
        storage.addCereal(Cereal.BULGUR, 2f)

        assertEquals("sum of cereal in a storage 4.0", storage.toString())

    }
}