package com.drinkscabinet.aoc2025

import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day7KtTest {

    private val testData = """.......S.......
...............
.......^.......
...............
......^.^......
...............
.....^.^.^.....
...............
....^.^...^....
...............
...^.^...^.^...
...............
..^...^.....^..
...............
.^.^.^.^.^...^.
..............."""



    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(21, this.part1(testData))
        assertEquals(1600, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(40, this.part2(testData))
        assertEquals(8632253783011, this.part2(realData))
    }

    private fun solve(data: String, part1: Boolean): Long {
        var result = 0L
        val grid = GridString.parse(data)
        // do it row by row
        for(row in grid.getYRange()) {
            val rowEntries = grid.getRowEntries(row)
            for(entry in rowEntries) {
                if(entry.second == 'S') {
                    // Put a beam below it
                    val below = entry.first.move(UpDown.D)
                    grid.add(below, '|')
                }
                if(entry.second == '|') {
                    // drop it down a level if empty
                    val below = entry.first.move(UpDown.D)
                    if(grid[below] == '.') {
                        grid[below] = '|'
                    }
                    else if(grid[below] == '^') {
                        result++
                        grid[below+UpDown.L] = '|'
                        grid[below+UpDown.R] = '|'
                    }
                }
            }
        }
        if(part1) return result
//        println(grid.toString(nums = true))

        val ways = mutableMapOf<Coord, Long>()
        for(row in grid.getYRange()) {
            val rowEntries = grid.getRowEntries(row)
            for(entry in rowEntries) {
                if(entry.second == '|') {
                    val left = entry.first.move(UpDown.L)
                    val right = entry.first.move(UpDown.R)
                    val above = entry.first.move(UpDown.U)
                    val aleft = above.move(UpDown.L)
                    val aright = above.move(UpDown.R)

                    if(grid[above] == 'S') {
                        ways[entry.first] = 1
                    }
                    else {
                        if(grid[above] == '|') {
                            ways[entry.first] = ways.getOrDefault(entry.first, 0) + ways.getValue(above)
                        }
                        if(grid[left] == '^' && grid[aleft] == '|') {
                            ways[entry.first] = ways.getOrDefault(entry.first, 0) + ways.getValue(aleft)
                        }
                        if(grid[right] == '^' && grid[aright] == '|') {
                            ways[entry.first] = ways.getOrDefault(entry.first, 0) + ways.getValue(aright)
                        }
                    }
//                    println("Ways to ${entry.first}: ${ways[entry.first]}")
                }
            }
        }
        // add together all the last row
       val timelines = ways.filter { it.key.y == grid.getYRange().last }.values.sum()
//        println(grid.toString(nums = true))
        println("Total timelines: $timelines")
        return timelines
    }

    private fun part1(data: String): Long {
        return solve(data, true)
    }
    private fun part2(data: String): Long {
        return solve(data, false)
    }
}