package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertTrue


class Day3KtTest {

    private val testData = """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"""
    private val testData2 = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""

    // Get the year and day from the class
    private val realData = Utils.input(this)



    @Test
    fun testPart1() {
        assertEquals(161, this.part1(testData))
        assertEquals(181345830, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(48, this.part2(testData2))
        assertEquals(566, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val regex = """mul\(\d+,\d+\)""".toRegex()
        return regex.findAll(data).map{ Utils.extractInts(it.value)}.map { it[0] * it[1] }.sum()
    }

    private fun part2(data: String): Int {
        val mul = """mul\(\d+,\d+\)""".toRegex()
        val doo = """do\(\)""".toRegex()
        val dont = """don't\(\)""".toRegex()

        val muls = mul.findAll(data).map{ it.range.first to it.value}.toList()
        val doos = doo.findAll(data).map{it.range.first to it.value}.toList()
        val donts = dont.findAll(data).map{it.range.first to it.value}.toList()

        val all = muls + doos + donts
        val sorted = all.sortedBy() { it.first }
        var indo = true
        var sum = 0
        for( s in sorted) {
            if(s.second.startsWith("don")) {
                indo = false
            }
            else if(s.second.startsWith("do")) {
                indo = true
            }
            if(s.second.startsWith("mul") && indo) {
                val nums = Utils.extractInts(s.second)
                sum += nums[0] * nums[1]
            }
        }
        return sum
    }
}