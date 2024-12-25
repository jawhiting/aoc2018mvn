package com.drinkscabinet.aoc2024

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day25KtTest {

    private val testData = """#####
.####
.####
.####
.#.#.
.#...
.....

#####
##.##
.#.##
...##
...#.
...#.
.....

.....
#....
#....
#...#
#.#.#
#.###
#####

.....
.....
#.#..
###..
###.#
###.#
#####

.....
.....
.....
#....
#.#..
#.#.#
#####"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(3, this.part1(testData))
        // 3933230 too high
        assertEquals(638, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(81, this.part2(testData))
        assertEquals(1289, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val (locks, keys) = parse(data)
        var count = 0
        for (key in keys) {
            for (lock in locks) {
                if (fits(key, lock)) {
                    count++
                }
            }
        }
        return count
    }

    private fun fits(key: List<Int>, lock: List<Int>): Boolean {
        for (i in 0..4) {
            if (key[i] + lock[i] > 5) return false

        }
        return true
    }

    private fun part2(data: String): Int {
        return 0
    }

    private fun parse(data: String): Pair<List<List<Int>>, List<List<Int>>> {
        val locks = mutableListOf<List<Int>>()
        val keys = mutableListOf<List<Int>>()
        val delim = if(data.contains("\r\n")) "\r\n\r\n" else "\n\n"
        for (diagram in data.split(delim)) {
            val grid = GridString.parse(diagram)
            val heights = mutableListOf<Int>()
            for (x in 0L..4) {
                var count = 0
                for (y in grid.getYRange()) {
                    if (grid[Coord(x, y)] == '#') {
                        count++
                    }
                }
                heights.add(count - 1)    // top or bottom doesn't count
            }
            if (grid[Coord(0, 0)] == '#') {
                locks.add(heights)
            } else {
                keys.add(heights)
            }
        }

        return locks to keys
    }
}