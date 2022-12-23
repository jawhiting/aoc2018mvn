package com.drinkscabinet.aoc2022kt

import Direction8
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day23KtTest {

    private val testData = """....#..
..###.#
#...#.#
.#...##
#.###..
##.#.##
.#..#.."""

    private val testSimple = """.....
..##.
..#..
.....
..##.
....."""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(110L, solve(testData, false))
    }

    @Test
    fun testPart1Real() {
        // 4495 too high
        //1536 too low
        assertEquals(3684, solve(realData, false))
    }

    private fun solve(data: String, part2: Boolean): Long {
        var grid = GridString.parse(data, default = '.', ignoreDefault = true)
        println(grid)

        val moveList = ArrayDeque<Direction8>(listOf(Direction8.N, Direction8.S, Direction8.W, Direction8.E))

        var i = 1

        while (true) {
            // Get all elves to consider
            val proposals: MutableMap<Coord, MutableSet<Coord>> = mutableMapOf()
            for (elf in grid.getAll('#')) {
                // If no neighbours
                if (grid.neighboursMatch(elf, Direction8.values().asIterable()) { it == '#' } == 0) {
                    // Don't move
                    proposals.getOrPut(elf) { mutableSetOf() }.add(elf)
                } else {
                    var foundMove = false
                    for (move in moveList) {
                        val movesToCheck = (-1..1).map { move.rotate(it) }
                        if (grid.neighboursMatch(elf, movesToCheck) { it == '#' } == 0) {
                            // Can make this move
                            proposals.getOrPut(elf.move(move)) { mutableSetOf() }.add(elf)
                            foundMove = true
                            break
                        }
                    }
                    if (!foundMove) {
                        // Don't move
                        proposals.getOrPut(elf) { mutableSetOf() }.add(elf)
                    }
                }
            }
            // Now all the proposals have been made, iterate and apply them
            val nextGrid = GridString(ignoreDefault = true)
            for ((target, origins) in proposals) {
                if (origins.size == 1) {
                    // can apply
                    nextGrid[target] = '#'
                } else {
                    // leave the elves in original position
                    origins.forEach { nextGrid[it] = '#' }
                }
            }
            // Now rotate the move list
            moveList.addLast(moveList.removeFirst())
            if (part2) {
                if (grid.getAll() == nextGrid.getAll())
                    return i.toLong()
            }
            grid = nextGrid
//            println("After round $i")
//            println(grid)
            if (!part2 && i == 10)
                break
            i++
        }

        return score(grid)
    }

    @Test
    fun testScore() {
        val data = """......#.....
..........#.
.#.#..#.....
.....#......
..#.....#..#
#......##...
....##......
.#........#.
...#.#..#...
............
...#..#..#.."""
        assertEquals(110L, score(GridString.parse(data, ignoreDefault = true)))
    }

    private fun score(grid: GridString): Long {
        // Bounding rect is just min/max XY
        val xRange = grid.getXRange()
        val yRange = grid.getYRange()
        // total size
        val total = (xRange.last - xRange.first + 1) * (yRange.last - yRange.first + 1)
        return total - grid.getAll('#').size
    }


    @Test
    fun testPart2() {
        assertEquals(20, solve(testData, true))
    }

    @Test
    fun testPart2real() {
        // 2697722344572 too low
        // 3451534022349 too high
        assertEquals(862, solve(realData, true))
    }

}