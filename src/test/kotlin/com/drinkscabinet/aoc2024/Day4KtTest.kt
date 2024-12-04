package com.drinkscabinet.aoc2024

import Direction8
import GridString
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day4KtTest {

    private val testData = """MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(18, this.part1(testData))
        assertEquals(2642, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(9, this.part2(testData))
        assertEquals(1974, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val grid = GridString.parse(data)
        // Find all the Xs and search from there
        val starts = grid.getAll('X')
        var found = 0
        for (s in starts) {
            for (d in Direction8.entries) {
                var word = ""
                for (i in 0L..3L) {
                    word += grid[s.move(d, i)]
                }
                if (word == "XMAS") {
                    found++
                }
            }
        }
        return found
    }

    private fun part2(data: String): Int {
        val grid = GridString.parse(data)
        // Find all the As and search from there
        val starts = grid.getAll('A')
        var found = 0
        // Get the 4 diagonals, then we expect it to have
        // 2 M, 2S, and either 2 M or S consecutive
        val diags = listOf(Direction8.NE, Direction8.SE, Direction8.SW, Direction8.NW)
        for (s in starts) {
            var word = ""
            for (d in diags) {
                word += grid[s.move(d)]
            }
            if(matches(word)) found++
        }
        return found
    }

    private fun matches(word: String) : Boolean {
        if(word.count { it == 'M' } == 2) {
            if(word.count{it == 'S'} == 2) {
                if(word.contains("MM") || word.contains("SS")) {
                    return true
                }
            }
        }
        return false
    }
}