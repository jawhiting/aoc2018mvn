package com.drinkscabinet

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class CoordTest {

    @Test
    fun testTo() {
        // Horizontal going up
        assertEquals(listOf(Coord(1, 1), Coord(2, 1), Coord(3, 1)), Coord(1, 1).to(Coord(3, 1)).toList())
        // Going down
        assertEquals(listOf(Coord(3, 1), Coord(2, 1), Coord(1, 1)), Coord(3, 1).to(Coord(1, 1)).toList())

        // Vertical going up
        assertEquals(listOf(Coord(1, 1), Coord(1, 2), Coord(1, 3)), Coord(1, 1).to(Coord(1, 3)).toList())
        // Going down
        assertEquals(listOf(Coord(1, 3), Coord(1, 2), Coord(1, 1)), Coord(1, 3).to(Coord(1, 1)).toList())

        // Diagonal
        assertThrows<IllegalArgumentException> {
            Coord(1, 1).to(Coord(2, 2)).toList()
        }
    }

    @Test
    fun testDiff() {
        assertEquals(Coord(2,3), Coord(1,1).diff(Coord(3,4)))
    }
}