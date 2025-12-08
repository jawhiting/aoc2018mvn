package com.drinkscabinet.aoc2025

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day4KtTest {

    private val testData = """..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@."""



    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(13, this.part1(testData))
        assertEquals(1460, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(43, this.part2(testData))

        //6873 too high
        assertEquals(9243, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val grid = GridString.parse(data)
        return grid.getAll('@').count { grid.neighboursMatch(it, Direction8.entries, { c -> c == '@' }) < 4 }
    }

    private fun part2(data: String): Int {
        val grid = GridString.parse(data)
        var count = 0
        while(true) {
            val removed = removeAccessible(grid)
            if(removed == 0) {
                break
            }
            count += removed
        }
        return count
    }

    private fun removeAccessible(g: GridString) : Int {
        val matched = g.getAll('@').filter { canRemove(g, it) }
        g.setAll(matched, 'x')
        return matched.size
    }

    private fun canRemove(g: GridString, c: Coord) : Boolean {
        return g.neighboursMatch(c, Direction8.entries, { c -> c == '@' }) < 4
    }
}