package com.drinkscabinet.aoc2025

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day0KtTest {

    private val testData = """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82"""



    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(3, this.part1(testData))
        assertEquals(1135, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(6, this.part2(testData))
        assertEquals(6558, this.part2(realData))
    }

    private fun part1(data: String): Int {
        var result = 0
        return result
    }

    private fun part2(data: String): Int {
        var result = 0
        return result
    }
}