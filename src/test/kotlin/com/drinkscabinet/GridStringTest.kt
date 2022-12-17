package com.drinkscabinet

import GridString
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun testAddShape() {
        val shapeTxt = """
            .#.
            ###
            .#.
        """.trimIndent()
        val startTxt = """
            #.#
            ...
            #.#
        """.trimIndent()
        val shape = GridString.parse(shapeTxt)
        val start = GridString.parse(startTxt)

        val expected = """
            ###
            ###
            ###
        """.trimIndent()
        println(shape.toString())
        println(start.toString())
//        start.add(Coord(0,0), shape)
//        println(start)
//        assertEquals(expected, start.add(Coord(0,0), shape).toString().trim())
        assertFalse(start.overlaps(Coord(0,0), shape))
        assertTrue(start.overlaps(Coord(1,0), shape))
        assertFalse(start.overlaps(Coord(2,0), shape))

    }



}