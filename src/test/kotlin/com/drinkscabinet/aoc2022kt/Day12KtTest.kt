package com.drinkscabinet.aoc2022kt

import GridString
import PathFinder
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day12KtTest {

    private val testData = """Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(31, this.part1(testData))
        assertEquals(437, this.part1(realData))

    }

    @Test
    fun testPart2() {
        assertEquals(29, this.part2(testData))
        assertEquals(430, this.part2(realData))
    }


    private fun part1(data: String): Int {
        val grid = GridString.parse(data)
        // Find start and end
        val start = grid.getAll('S').first()
        val end = grid.getAll('E').first()
        // Set actual heights for start and end
        grid.add(start, 'a')
        grid.add(end, 'z')
        println("Start from $start target $end")

        val path = PathFinder.dijkstra2(start, end) { cur -> grid.neighbours4(cur).filter { n -> validMove(grid, cur, n.first) }.map { it.first to 1 }}
        return path.first[end]!!
    }

    private fun part2(data: String): Int {
        val grid = GridString.parse(data)
        // Find start and end
        val start = grid.getAll('S').first()
        val end = grid.getAll('E').first()
        // Set actual heights for start and end
        grid.add(start, 'a')
        grid.add(end, 'z')
        println("Start from $start target $end")

        // Find the shortest path starting with 'a'
        val aas = grid.getAll('a')
        return aas.parallelStream().map { shortestPath(grid, it, end) }.min(Int::compareTo).orElse(9999)
    }

    private fun shortestPath(grid: GridString, start: Coord, end: Coord): Int {
        val path = PathFinder.dijkstra2(start, end) { cur -> grid.neighbours4(cur).filter { n -> validMove(grid, cur, n.first) }.map { it.first to 1 }}
        // Should probably cache...
        return path.first[end] ?: 9999
    }

    private fun validMove(grid: GridString, current: Coord, neighbour: Coord): Boolean {
        val curVal = grid.get(current)
        val nVal = grid.get(neighbour)
        return if( nVal == '.') {
            false
        } else {
            nVal.code <= curVal.code+1
        }
    }
}