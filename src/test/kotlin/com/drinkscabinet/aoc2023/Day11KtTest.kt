package com.drinkscabinet.aoc2023

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertEquals

class Day11KtTest {
    private val testData = """...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#....."""

    private val target = """....#........
.........#...
#............
.............
.............
........#....
.#...........
............#
.............
.............
.........#...
#....#......."""

    private val realData = Utils.input(this)

    @Test
    fun testEmpty() {
        val grid = GridString.parse(testData, '.', true)
        assertFalse(grid.isColumnEmpty(0))
        assertTrue(grid.isColumnEmpty(2))
        assertFalse(grid.isRowEmpty(0))
        assertTrue(grid.isRowEmpty(3))
    }

    private fun stretchEmpty(grid: GridString, factor: Long): GridString {
        val grid2 = GridString('.', true)
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        val emptyColumns = xr.filter { grid.isColumnEmpty(it) }.toSortedSet()
        val emptyRows = yr.filter { grid.isRowEmpty(it) }.toSortedSet()

        // Now look at each galaxy and move it
        for (galaxy in grid.getAll().entries) {
            // How many empty columns before this
            val emptyColsBefore = emptyColumns.count { it < galaxy.key.x }
            // How many empty rows before this
            val emptyRowsBefore = emptyRows.count { it < galaxy.key.y }
            val newX = galaxy.key.x + (emptyColsBefore * factor) - emptyColsBefore
            val newY = galaxy.key.y + (emptyRowsBefore * factor) - emptyRowsBefore
            grid2.add(Coord(newX, newY), galaxy.value)
        }
        return grid2
    }

    private fun distance(a: Coord, b: Coord): Long {
        return abs(a.x - b.x) + abs(a.y - b.y)
    }

    private fun calcDistances(grid: GridString): Long {
        var result = 0L
        val clist = grid.getAll().keys.toTypedArray()
        for(i in clist.indices) {
            for(j in i + 1 until clist.size) {
                result += distance(clist[i], clist[j])
            }
        }
        return result
    }


    @Test
    fun testPart1() {
        assertEquals(374, part1(testData))
        assertEquals(9543156, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(1030, solve(testData, 10))
        assertEquals(8410, solve(testData, 100))
        assertEquals(625243292686, solve(realData, 1000000))
    }

    fun solve(data: String, factor: Long): Long {
        val grid = GridString.parse(data, '.', true)
        val grid2 = stretchEmpty(grid, factor)

        return calcDistances(grid2)
    }

    fun part1(data: String) : Long {
        return solve(data, 2)
    }
}