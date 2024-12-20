package com.drinkscabinet.aoc2024

import GridString
import PathFinder
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day20KtTest {

    private val testData = """###############
#...#...#.....#
#.#.#.#.#.###.#
#S#...#.#.#...#
#######.#.#.###
#######.#.#...#
#######.#.###.#
###..E#...#...#
###.#######.###
#...###...#...#
#.#####.#.###.#
#.#...#.#.#...#
#.#.#.#.#.#.###
#...#...#...###
###############"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(5, this.solve(testData, 2,20))
        assertEquals(1332, this.solve(realData, 2,100))
    }

    @Test
    fun testPart2() {
        assertEquals(285, this.solve(testData, 20, 50))
        assertEquals(987695, this.solve(realData, 20, 100))
    }

    private fun parse(data: String): GridString {
        return GridString.parse(data)

    }

    fun getStartEnd(grid: GridString): Pair<Coord, Coord> {
        val start = grid.getAll('S').first()
        val end = grid.getAll('E').first()
        grid[start] = '.'
        grid[end] = '.'
        return start to end
    }

    private fun solve(data: String, cheatDistance: Int, atLeast: Int): Int {
        val grid = parse(data)
        val (start, end) = getStartEnd(grid)
        // Get shortest path
        val (dist, predecessor) = PathFinder.dijkstra2(start, end) { moves(it, grid) }
        // Now for each step in the path, find all the other path entries steps up to 20 away
        // and figure out how much you skipped
        val path = dist.keys
        val savings = mutableMapOf<Long, Int>()
        for (p in dist.keys) {
            // Get all the steps up to 20 away
            val cheats = path.filter { it.distance(p) <= cheatDistance }.map{ it.distance(p) to dist[it]!! }
            // This now has manhattan distance to the cheat distance

            // How far is this coord from the start
            val currentDist = dist[p]!!

            // now find how many of the cheats are further than md away from the current distance+md
            for (c in cheats) {
                val saved = c.second - currentDist - c.first
                savings[saved] = savings.getOrDefault(saved, 0) + 1
            }
        }
        savings.toSortedMap().forEach { (k, v) -> println("$k: $v") }
        return savings.filter { it.key >= atLeast }.map { it.value }.sum()
    }

    private fun moves(c: Coord, grid: GridString): List<Pair<Coord, Int>> {
        return grid.neighbours4(c).filter { it.second != '#' }.map { it.first to 1 }.toList()
    }
}