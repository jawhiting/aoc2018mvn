package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day10KtTest {

    private val testData = """"""


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
        return 0
    }

    private fun part2(data: String): Int {
        return 0
    }
}