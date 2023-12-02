package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import com.drinkscabinet.contains
import com.drinkscabinet.overlaps
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
        "five" to
                "5",
        "six" to
                "6",
        "seven" to
                "7",
        "eight" to
                "8",
        "nine" to
                "9"
    )
    private val numsR = nums.keys.map { it.reversed() }.toSet()

    fun greedyNumFirst(line: String): String {
        // find earliesst num
        var l = line

        val positions = l.findAnyOf(nums.keys)
        if (positions != null) {
            l = l.replaceFirst(positions.second, nums[positions.second!!]!!)
        }

//        println(line + " converted to " + l)
        return l
    }

    fun greedyNumLast(line: String): String {
        var l = line.reversed()
        val positions = l.findAnyOf(numsR)
        if (positions != null) {
            l = l.replaceFirst(positions.second, nums[positions.second!!.reversed()]!!)
        }

//        println(line + " converted to " + l)
        return l.reversed()
    }

    fun lineVal(line: String): Int {
        var l = line
        val lf = greedyNumFirst(l)
        val ll = greedyNumLast(l)
        val digitsf = lf.filter { it in '0'..'9' }
        val digitsl = ll.filter { it in '0'..'9' }
        val first = digitsf[0].digitToInt()
        val last = digitsl.reversed()[0].digitToInt()
        val r = first * 10 + last
        println(line + " : " + l + " : " + r)
        return r
    }

    @Test
    fun testPart1() {
        assertEquals(142, this.part1(testData))
        assertEquals(54708, this.part1(realData))
    }

    @Test
    fun testPart2() {
//        assertEquals(281, this.part1(testData2))
        // not 54100
        // 54060 too low
        assertEquals(770, this.part1(realData))
    }

    private fun part1(data: String): Int {
        return data.lines().map { lineVal(it) }.sum()
    }

    private fun part2(data: String): Int {
        return 4
    }

//    @Test
//    fun testGreedy() {
//        assertEquals("96lqhnvbpx178sxjfkz4vr", greedyNum("nine6lqhnvbpxoneseveneightsxjfkz4vr"))
//        assertEquals("8wo3", greedyNum("eightwothree"))
//    }
//
    @Test
    fun testLineVal() {
        assertEquals(11, lineVal("xx1xx"))
        assertEquals(94, lineVal("nine6lqhnvbpxoneseveneightsxjfkz4vr"))
        assertEquals(83, lineVal("eightwothree"))
    }

}