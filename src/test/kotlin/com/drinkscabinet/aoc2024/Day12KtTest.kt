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

    private val testData5 = """AAAAAA
AAABBA
AAABBA
ABBAAA
ABBAAA
AAAAAA"""

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
        assertEquals(80, this.part2(testData1))
        assertEquals(436, this.part2(testData2))
        assertEquals(236, this.part2(testData4))
        assertEquals(368, this.part2(testData5))
        assertEquals(1206, this.part2(testData3))
        assertEquals(815788, this.part2(realData))
    }


    private fun part1(data: String): Long {
        val grid = GridString.parse(data)
        val regions = extractRegions(grid)
        return regions.sumOf { it.score(grid) }
    }

    private fun part2(data: String): Long {
        val grid = GridString.parse(data)
        val regions = extractRegions(grid)
        return regions.sumOf { it.score2() }
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

        fun score2(): Long {
            val area = coords.size
            val corners = countCorners(scale())
            return area * corners.toLong()
        }

        fun scale(s: Int = 3): GridString {
            val result = GridString()
            for (c in coords) {
                for (x in 0..s - 1) {
                    for (y in 0..s - 1) {
                        result[Coord(c.x * s + x, c.y * s + y)] = 'X'
                    }
                }
            }
            return result.normalise()
        }

        val corner = GridString.parse(
            """..
X."""
        )
        val innerCorner = GridString.parse(
            """XX
X."""
        )

        val xCorner = GridString.parse(
            """X.
.X"""
        )

        val corners = (0..3).map { corner.copyOf().rotate90(it).normalise() }
        val innerCorners = (0..3).map { innerCorner.copyOf().rotate90(it).normalise() }
        val xCorners = (0..3).map { xCorner.copyOf().rotate90(it).normalise() }

        fun countCorners(grid: GridString): Int {
            // count the number of inner and outer corners
            var cornerCount = 0
            for (rotate in 0..3) {
                val c = corners[rotate]
                val ic = innerCorners[rotate]
                val xc = xCorners[rotate]

                for (x in grid.getXMin() - 1..grid.getXMax() + 1) {
                    for (y in grid.getYMin() - 1..grid.getYMax() + 1) {
                        if (grid.matches(Coord(x, y), c)) {
//                            println("Found corner at $x $y")
                            cornerCount++
                        } else if (grid.matches(Coord(x, y), ic)) {
//                            println("Found innercorner at $x $y")
                            cornerCount++
                        } else if (grid.matches(Coord(x, y), xc)) {
//                            println("Found xcorner at $x $y")
                            cornerCount++
                        }
                    }
                }
            }
            return cornerCount
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
            toVisit.addAll(matching.map { it.first }.filter { it !in members })
        }
        return Region(c, members, edges)
    }


}