package com.drinkscabinet.aoc2023

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13KtTest {

    private val testInput = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""

    private val testInput1 = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#."""

    private val testInput2 = """#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""

    private val realData = Utils.input(this)
    private fun findVerticalSymmetry(grid: GridString, diffs: Int = 0) : Long? {
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        for(x in xr.first until xr.last) {
            var colDiffs = 0
            for(y in yr) {
                for(delta in 0..(xr.last-xr.first)/2) {
                    if(x-delta in xr && x+1+delta in xr &&grid[Coord(x-delta,y)] != grid[Coord(x+1+delta, y)]) {
                        colDiffs++
                    }
                }
                if(colDiffs > diffs) {
                    break
                }
            }
            if(colDiffs == diffs) {
                return x
            }
        }
        return null
    }

    private fun findHorizontalSymmetry(grid: GridString, diffs: Int = 0) : Long? {
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        for(y in yr.first until yr.last) {
            var rowDiffs = 0
            for(x in xr) {
                for(delta in 0..(yr.last-yr.first)/2) {
                    if(y-delta in yr && y+1+delta in yr &&grid[Coord(x,y-delta)] != grid[Coord(x,y+1+delta)]) {
                        rowDiffs++
                    }
                }
                if(rowDiffs > diffs) {
                    break
                }
            }
            if(rowDiffs == diffs) {
                return y
            }
        }
        return null
    }

    @Test
    fun testVerticalSymmetry() {
        val grid1 = GridString.parse(testInput1, '.', true)
        val grid2 = GridString.parse(testInput2, '.', true)
        assertEquals(4, findVerticalSymmetry(grid1))
        assertEquals(null, findVerticalSymmetry(grid2))
    }

    @Test
    fun testHorizontalSymmetry() {
        val grid1 = GridString.parse(testInput1, '.', true)
        val grid2 = GridString.parse(testInput2, '.', true)
        assertEquals(null, findHorizontalSymmetry(grid1))
        assertEquals(3, findHorizontalSymmetry(grid2))
    }

    @Test
    fun testPart1() {
        assertEquals(405, part1(testInput))
        assertEquals(35538, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(400, part2(testInput))
        assertEquals(30442, part2(realData))
    }

    private fun score1(grid: GridString, diffs: Int = 0) : Long {
        println("Grid: ")
        println(grid)
        val vert = findVerticalSymmetry(grid, diffs)
        return if(vert != null ) {
            vert + 1
        } else {
            (findHorizontalSymmetry(grid, diffs)!!+1) * 100
        }
    }

    fun part1(input: String) : Long {
        val grids = input.split("\n\n").map {GridString.parse(it, '.', true)}

        return grids.map { score1(it) }.sum()
    }

    fun part2(input: String) : Long {
        val grids = input.split("\n\n").map {GridString.parse(it, '.', true)}

        return grids.map { score1(it, 1) }.sum()
    }
}