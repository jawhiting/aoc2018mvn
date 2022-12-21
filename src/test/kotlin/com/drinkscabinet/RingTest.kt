package com.drinkscabinet

import org.junit.jupiter.api.Test

class RingTest {

    @Test
    fun testCreate() {
        val ring = Ring<Int>()
        for (i in 0..10) {
            ring.addBefore(i)
        }
        println(ring)
        ring.removeAt(15)
        println(ring)
        println(ring[2])
    }
}