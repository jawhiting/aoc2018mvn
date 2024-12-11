package com.drinkscabinet.aoc2024

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day11KtTest {

    private val testData = """125 17"""


    // Get the year and day from the class
    private val realData = """3279 998884 1832781 517 8 18864 28 0"""


    @Test
    fun testPart1() {
        assertEquals(22, this.part1(testData, 6))
        assertEquals(55312, this.part1(testData, 25))
        assertEquals(218956, this.part1(realData, 25))
    }

    @Test
    fun testPart2() {
        assertEquals(259593838049805, this.part1(realData, 75))

    }

    private val cache = mutableMapOf<Pair<Long, Int>, Long>()

    private fun blink(stone: Long, blinks: Int): Long {
        val sb = stone to blinks
        val cached = cache[sb]
        if(cached != null) return cached

        // Returns total number of stones
        if (blinks == 0) return cache.computeIfAbsent(sb) { 1L }
        // Split the stones then count
        var result = 0L
        if (stone == 0L) {
            result = blink(1, blinks - 1)
        }
        else {
            val stoneStr = stone.toString()
            result = if (stoneStr.length % 2 == 0) {
                blink(stoneStr.substring(0, stoneStr.length / 2).toLong(), blinks - 1) + blink(
                    stoneStr.substring(
                        stoneStr.length / 2
                    ).toLong(), blinks - 1
                )
            } else {
                blink(stone*2024L, blinks-1)
            }
        }
        cache[sb] = result
        return result
    }

    private fun part1(data: String, blinks: Int): Long {
        val stones = Utils.extractLongs(data)
        return stones.sumOf{blink(it, blinks)}
    }

    private fun part2(data: String): Int {
        val grid = GridString.parse(data)
        var count = 0
        for (start in grid.getAll('0')) {
            count += count(start, grid)
        }
        return count
    }

    private fun count(pos: Coord, grid: GridString): Int {
        if (grid[pos] == '9') return 1
        val nextNeeded = grid[pos] + 1
        var result = 0
        for (n in grid.neighbours4(pos)) {
            if (n.second == nextNeeded) {
                result += count(n.first, grid)
            }
        }
        return result
    }

    private fun count1(pos: Coord, grid: GridString): Set<Coord> {
        if (grid[pos] == '9') return setOf(pos)
        val nextNeeded = grid[pos] + 1
        var result = mutableSetOf<Coord>()
        for (n in grid.neighbours4(pos)) {
            if (n.second == nextNeeded) {
                result.addAll(count1(n.first, grid))
            }
        }
        return result
    }

}