package com.drinkscabinet

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UtilsKtTest {
    @Test
    fun testContains() {
        assertTrue(IntRange(2, 8).contains(IntRange(3, 7)))
        assertFalse(IntRange(3, 7).contains(IntRange(2, 8)))
    }

    @Test
    fun testOverlaps() {
        assertTrue(IntRange(5, 7).overlaps(IntRange(7, 9)))
        assertTrue(IntRange(2, 8).overlaps(IntRange(3, 7)))
        assertTrue(IntRange(3, 7).overlaps(IntRange(2, 8)))
    }

    @Test
    fun testLcm() {
        assertEquals(700, Utils.lcm(100, 35))
        assertEquals(700, Utils.lcm(35, 100))
        assertEquals(100, Utils.lcm(10, 100))
        assertEquals(100, Utils.lcm(100, 10))
        assertEquals(21, Utils.lcm(3, 7))
        assertEquals(21, Utils.lcm(7, 3))
    }
}