package com.drinkscabinet.aoc2024

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day10KtTest {

    private val testData = """89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(36, this.part1(testData))
        assertEquals(638, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(81, this.part2(testData))
        assertEquals(1289, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val grid = GridString.parse(data)
        var count = 0
        for(start in grid.getAll('0')) {
            count += count1(start, grid).size
        }
        return count
    }

    private fun part2(data: String): Int {
        val grid = GridString.parse(data)
        var count = 0
        for(start in grid.getAll('0')) {
            count += count(start, grid)
        }
        return count
    }

    private fun count(pos: Coord, grid: GridString) : Int {
        if(grid[pos] == '9') return 1
        val nextNeeded = grid[pos] + 1
        var result = 0
        for(n in grid.neighbours4(pos)) {
            if(n.second == nextNeeded ) {
                result += count(n.first, grid)
            }
        }
        return result
    }

    private fun count1(pos: Coord, grid: GridString) : Set<Coord> {
        if(grid[pos] == '9') return setOf(pos)
        val nextNeeded = grid[pos] + 1
        var result = mutableSetOf<Coord>()
        for(n in grid.neighbours4(pos)) {
            if(n.second == nextNeeded ) {
                result.addAll(count1(n.first, grid))
            }
        }
        return result
    }

}