package com.drinkscabinet

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GridStringTest {

    @Test
    fun testNextInDirection() {
        val input = """
            .............
            .L.L.#.#.#.#.
            .............
        """.trimIndent()

        val grid = GridString.parse(input)

        assertEquals(Coord(3, 1) to 'L', grid.nextInDirection(Coord(1,1), Direction.E))
        assertEquals(Coord(5, 1) to '#', grid.nextInDirection(Coord(3,1), Direction.E))
        assertEquals(Coord(1, 1) to 'L', grid.nextInDirection(Coord(3,1), Direction.W))
        assertEquals(Coord(3, -1) to '.', grid.nextInDirection(Coord(3,1), Direction.N))
    }



}