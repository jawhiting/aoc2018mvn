package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day1KtTest {

    private val testData = """1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet"""

    private val testData2 = """two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen""".trimIndent()

    // Get the year and day from the class
    private val realData = Utils.input(this)

    private val nums = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )
    private val numsR = nums.keys.map { it.reversed() }.toSet()

    private fun greedyNumFirst(line: String): String {
        // find earliest num
        var l = line
        val positions = l.findAnyOf(nums.keys)
        if (positions != null) {
            l = l.replaceFirst(positions.second, nums[positions.second]!!)
        }
        return l
    }

    private fun greedyNumLast(line: String): String {
        var l = line.reversed()
        val positions = l.findAnyOf(numsR)
        if (positions != null) {
            l = l.replaceFirst(positions.second, nums[positions.second.reversed()]!!)
        }
        return l.reversed()
    }

    private fun lineValPart2(line: String): Int {
        val first = greedyNumFirst(line).filter { it in '0'..'9' }[0].digitToInt()
        val last = greedyNumLast(line).reversed().filter { it in '0'..'9' }[0].digitToInt()
        return first * 10 + last
    }

    private fun lineValPart1(line: String): Int {
        val first = line.filter { it in '0'..'9' }[0].digitToInt()
        val last = line.reversed().filter { it in '0'..'9' }[0].digitToInt()
        return first * 10 + last
    }

    @Test
    fun testPart1() {
        assertEquals(142, this.part1(testData))
        assertEquals(54708, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(281, this.part2(testData2))
        assertEquals(54087, this.part2(realData))
    }

    private fun part1(data: String): Int {
        return data.lines().sumOf { lineValPart1(it) }
    }

    private fun part2(data: String): Int {
        return data.lines().sumOf { lineValPart2(it) }
    }

    @Test
    fun testLineVal() {
        assertEquals(11, lineValPart2("xx1xx"))
        assertEquals(94, lineValPart2("nine6lqhnvbpxoneseveneightsxjfkz4vr"))
        assertEquals(83, lineValPart2("eightwothree"))
    }

}