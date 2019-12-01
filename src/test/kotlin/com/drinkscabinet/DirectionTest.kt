package com.drinkscabinet

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DirectionTest {

    @Test
    fun testDirection() {
        assertEquals(Direction.E, Direction.N.rotate(1))
        assertEquals(Direction.S, Direction.N.rotate(2))
        assertEquals(Direction.W, Direction.N.rotate(3))
        assertEquals(Direction.N, Direction.N.rotate(4))
        assertEquals(Direction.N, Direction.N.rotate(8))

    }



}