package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day3KtTest {

    private val testData = """1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet"""


    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(100, this.part1(testData))
        assertEquals(100, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(100, this.part2(testData))
        assertEquals(100, this.part2(realData))
    }

    private fun part1(data: String): Int {
        return 100
    }

    private fun part2(data: String): Int {
        return 100
    }
}