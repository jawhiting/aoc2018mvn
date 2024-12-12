package com.drinkscabinet.aoc2024

import Direction
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day12KtTest {

    private val testData1 = """AAAA
BBCD
BBCC
EEEC"""

    private val testData2 = """OOOOO
OXOXO
OOOOO
OXOXO
OOOOO"""

    private val testData3 = """RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE"""

    private val testData4 = """EEEEE
EXXXX
EEEEE
EXXXX
EEEEE"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(140, this.part1(testData1))
        assertEquals(772, this.part1(testData2))
        assertEquals(1930, this.part1(testData3))
        assertEquals(55312, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(259593838049805, this.part1(realData))
    }


    private fun part1(data: String): Long {
        val grid = GridString.parse(data)
        val regions = extractRegions(grid)
        return regions.sumOf { it.score(grid) }
    }

    private data class Region(
        val code: Char,
        val coords: MutableSet<Coord> = mutableSetOf<Coord>(),
        val edges: MutableSet<Coord>
    ) {
        fun score(grid: GridString): Long {
            val area = coords.size
            // get the perimeter by walking the edges
            val perimeter = edges.sumOf { 4L - grid.neighboursMatch(it, Direction.entries) { it == code } }
            return area * perimeter
        }

        fun sides() : Int {
            // Pick an edge and walk through its neighbours
            return 0
        }
    }

    private fun extractRegions(grid: GridString): List<Region> {
        val regions = mutableListOf<Region>()
        val visited = mutableSetOf<Coord>()
        for (c in grid.getAll().keys) {
            if (c !in visited) {
                val r = getConnected(c, grid)
                regions.add(r)
                visited.addAll(r.coords)
            }
        }
        // Should have all the regions now
        return regions
    }

    private fun getConnected(coord: Coord, grid: GridString): Region {
        val c = grid[coord]
        val toVisit = mutableSetOf<Coord>(coord)
        val members = mutableSetOf<Coord>()
        val edges = mutableSetOf<Coord>()

        while (toVisit.isNotEmpty()) {
            val cur = toVisit.first()
            toVisit.remove(cur)
            members.add(cur)
            // Get matching neighbours
            val matching = grid.neighbours4(cur).filter { it.second == c }
            // Is it an edge?
            if (matching.size != 4) {
                edges.add(cur)
            }
            toVisit.addAll(matching.map { it.first }.filter{it !in members})
        }
        return Region(c, members, edges)
    }

    private fun part2(data: String): Long {
        return 0L
    }
}