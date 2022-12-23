package com.drinkscabinet

import Direction
import Direction8
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

    @Test
    fun testDirectionNegative() {
        assertEquals(Direction.W, Direction.N.rotate(-1))
        assertEquals(Direction.W, Direction.N.rotate(-5))
        assertEquals(Direction.S, Direction.N.rotate(-2))
        assertEquals(Direction.E, Direction.N.rotate(-3))
        assertEquals(Direction.N, Direction.N.rotate(-4))
        assertEquals(Direction.N, Direction.N.rotate(-8))
        assertEquals(Direction8.NW, Direction8.N.rotate(-1))

    }



}