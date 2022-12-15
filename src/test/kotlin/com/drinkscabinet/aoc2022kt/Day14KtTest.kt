package com.drinkscabinet.aoc2022kt

import GridString
import UpDown
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day14KtTest {

    private val testData = """498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(24, this.part1(testData))
        assertEquals(828, this.part1(realData))

    }

    @Test
    fun testPart2() {
        assertEquals(93, this.part2(testData))
        assertEquals(25500, this.part2(realData))
    }

    private fun parseGrid(data: String): GridString {
        val result = GridString()
        for (line in data.lines()) {
            val coords = mutableListOf<Coord>()
            val ints = Utils.extractInts(line)
            for (i in 0..ints.lastIndex step 2) {
                coords.add(Coord(ints[i], ints[i + 1]))
            }
            println("Coords are $coords")
            var pos = coords.removeFirst()
            result.add(pos, '#')
            while (coords.isNotEmpty()) {
                val target = coords.removeFirst()
                // Calculate delta from pos to target
                val delta = if (target.x == pos.x) {
                    if (target.y < pos.y) {
                        Coord(0, -1)
                    } else {
                        Coord(0, 1)
                    }
                } else {
                    if (target.x < pos.x) {
                        Coord(-1, 0)
                    } else {
                        Coord(1, 0)
                    }
                }
                // draw from pos to target
                while (pos != target) {
                    // increment pos
                    pos = pos.move(delta)
                    result.add(pos, '#')
                }
            }
        }
        return result
    }

    private fun dropSand(grid: GridString, sandStart: Coord, yMax: Long): Boolean {
        // return true if sand came to rest, false if blocked or fall into void
        var pos = sandStart
        // End when the sand reaches bottom row
        while (pos.y <= yMax) {
            // Determine next move
            // straight down
            if (grid[pos.move(UpDown.D)] == '.') {
                pos = pos.move(UpDown.D)
            } else if (grid[pos.move(UpDown.D).move(UpDown.L)] == '.') {
                pos = pos.move(UpDown.D).move(UpDown.L)
            } else if (grid[pos.move(UpDown.D).move(UpDown.R)] == '.') {
                pos = pos.move(UpDown.D).move(UpDown.R)
            } else {
                // All three moves aren't possible, so stick here
                grid[pos] = 'o'
                return pos != sandStart
            }
        }
        // Fell out the bottom
        return false
    }

    private fun part1(data: String): Int {
        val grid = parseGrid(data)
        val origin = Coord(500, 0)
        grid.add(origin, '+')
        println(grid.toString(true))
        val yMax = grid.getYMax()
        var canDrop = true
        while (canDrop) {
            canDrop = dropSand(grid, origin, yMax)
        }
        println(grid.toString(true))
        return grid.getAll('o').size
    }

    private fun part2(data: String): Int {
        val grid = parseGrid(data)
        val origin = Coord(500, 0)
        grid.add(origin, '+')
        println(grid.toString(true))
        // add floor
        val floorLevel = grid.getYMax() + 2
        for (x in grid.getXMin() - 1000..grid.getXMax() + 1000) {
            grid[Coord(x, floorLevel)] = '#'
        }
        var canDrop = true
        var dropped = 0
        val yMax = grid.getYMax()
        while (canDrop) {
            canDrop = dropSand(grid, origin, yMax)
            dropped++
            if (dropped % 1000 == 0) {
                println("Dropped $dropped")
            }
        }
//        println(grid.toString(true))
        return grid.getAll('o').size
    }

}