package com.drinkscabinet.aoc2024

import GridString
import PathFinder
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day18KtTest {

    private val testData = """5,4
4,2
4,5
3,0
2,1
6,3
2,4
1,5
0,6
3,3
2,6
5,1
1,2
5,5
2,5
6,5
1,4
0,4
6,4
1,1
6,1
1,0
0,5
1,6
2,0"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(22, this.part1(testData, 12, 0..6))
        assertEquals(260, this.part1(realData, 1024, 0..70))
    }

    @Test
    fun testPart2() {
        assertEquals("6,1", this.part2(testData, 0..6))
        assertEquals("24,48", this.part2(realData, 0..70))

    }

    private fun part1(data: String, count: Int, range: IntRange): Int {
        // Draw bounds around a grid
        val grid = GridString()
        // draw square bounds
        for (i in range.min() - 1..range.max() + 1) {
            grid[Coord(i, range.min() - 1)] = '#'
            grid[Coord(i, range.max() + 1)] = '#'
            grid[Coord(range.min() - 1, i)] = '#'
            grid[Coord(range.max() + 1, i)] = '#'
        }
        println(grid)
        corrupt(count, data, grid)
        println(grid)

        // Now pathfind
        val start = Coord(range.min(), range.min())
        val end = Coord(range.max(), range.max())
        val paths = PathFinder.dijkstra2(start, end) { moves(it, grid) }
        return paths.first[end]!!
    }

    private fun part2(data: String, range: IntRange) : String {
        // binary search on the number
        // Draw bounds around a grid
        val grid = GridString()
        // draw square bounds
        for (i in range.min() - 1..range.max() + 1) {
            grid[Coord(i, range.min() - 1)] = '#'
            grid[Coord(i, range.max() + 1)] = '#'
            grid[Coord(range.min() - 1, i)] = '#'
            grid[Coord(range.max() + 1, i)] = '#'
        }
        val start = Coord(range.min(), range.min())
        val end = Coord(range.max(), range.max())

        val c = Utils.binarySearch(0, data.lines().size.toLong()) { count ->
            val g = grid.copyOf()
            corrupt(count.toInt(), data, g)
            canExit(start, end, g)
        }
        println(c)
        // second item in the pair is what we want
        return data.lines()[c.second.toInt()-1].toString()
    }


    private fun canExit(start: Coord, end: Coord, grid: GridString): Boolean {
        val paths = PathFinder.dijkstra2(start, end) { moves(it, grid) }
        return paths.first.contains(end)
    }

    private fun moves(c: Coord, grid: GridString): List<Pair<Coord, Int>> {
        return grid.neighbours4(c).filter { it.second == '.' }.map { it.first to 1 }.toList()
    }

    private fun corrupt(count: Int, data: String, grid: GridString) {
        val bytes = data.lines().map { Utils.extractInts(it) }.map { Coord(it[0], it[1]) }.take(count)
        bytes.forEach { grid[it] = '#' }
    }

    private fun part2(data: String): Int {
        return 0
    }
}