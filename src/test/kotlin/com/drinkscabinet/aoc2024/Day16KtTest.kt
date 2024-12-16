package com.drinkscabinet.aoc2024

import Direction
import GridString
import PathFinder
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day16KtTest {

    private val testData = """###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#.....#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############"""

    private val testData2 = """#################
#...#...#...#..E#
#.#.#.#.#.#.#.#.#
#.#.#.#...#...#.#
#.#.#.#.###.#.#.#
#...#.#.#.....#.#
#.#.#.#.#.#####.#
#.#...#.#.#.....#
#.#.#####.#.###.#
#.#.#.......#...#
#.#.###.#####.###
#.#.#...#.....#.#
#.#.#.#####.###.#
#.#.#.........#.#
#.#.#.#########.#
#S#.............#
#################"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(7036, this.part1(testData))
        assertEquals(11048, this.part1(testData2))
        assertEquals(104516, this.part1(realData))
    }

    @Test
    fun testPart2() {
//        assertEquals(45, this.part2b(testData))
//        assertEquals(64, this.part2b(testData2))
        assertEquals(545, this.part2b(realData))
    }


    data class Reindeer(val pos: Coord, val dir: Direction)

    private fun part1(data: String): Long {
        val grid = GridString.parse(data)
        val startPos = grid.getAll('S').first()
        val start = Reindeer(startPos, Direction.E)
        val endPos = grid.getAll('E').first()

        var shortest = Int.MAX_VALUE
        // Check all target dirs for shortest
        for (d in Direction.entries) {
            val target = Reindeer(endPos, d)
            val result = PathFinder.dijkstra2(start, target) { r -> nextSteps(r, grid).toList() }
            println(result.first[target])
            if (result.first[target] != null)
                shortest = shortest.coerceAtMost(result.first[target]!!)
        }
        return shortest.toLong()
    }

    private fun nextSteps(r: Reindeer, grid: GridString) = sequence<Pair<Reindeer, Int>> {
        // forward, rotate left or rotate right
        val forwardPos = r.pos + r.dir
        if(grid[forwardPos] != '#') {
            yield(Reindeer(forwardPos, r.dir) to 1)
        }
        // turn right
        yield(Reindeer(r.pos, r.dir.rotate(1)) to 1000)
        // turn left
        yield(Reindeer(r.pos, r.dir.rotate(-1)) to 1000)
    }

    private fun part2b(data: String): Int {
        val targetScore = part1(data).toInt()
        // Now do a DFS with early break out
        val grid = GridString.parse(data)
        val startPos = grid.getAll('S').first()
        val start = Reindeer(startPos, Direction.E)
        val endPos = grid.getAll('E').first()

        // Now, do a dijkstra from start to target, and target to start
        // then for each coord in the grid

        val coords = mutableSetOf<Coord>()
        for (ed in Direction.entries) {
            val target = Reindeer(endPos, ed)
            var start2 = Reindeer(startPos, start.dir.opposite())
            var target2 = Reindeer(endPos, ed.opposite())
            val startToTarget = PathFinder.dijkstra2(start, target) { r -> nextSteps(r, grid).toList() }
            val targetToStart = PathFinder.dijkstra2(target2, start2) { r -> nextSteps(r, grid).toList() }
            // Now, since we know the target score, find all coords where the sum of each distance adds up
            for (x in grid.getXRange()) {
                for (y in grid.getYRange()) {
                    if(grid[Coord(x,y)] != '#') {
                        for (cd in Direction.entries) {
                            val r = Reindeer(Coord(x, y), cd)
                            val r2 = Reindeer(Coord(x, y), cd.opposite())
                            // look this up in both
                            val st = startToTarget.first.getOrDefault(r, 0)
                            val ts = targetToStart.first.getOrDefault(r2, 0)
                            if (st + ts == targetScore
                            ) {
                                coords.add(Coord(x, y))
                            }
                        }
                    }
                }
            }
        }
//        val grid2 = grid.copyOf()
//        coords.forEach { grid2[it] = 'O' }
//        println(grid2.toString(nums = true))
        return coords.size
    }

}