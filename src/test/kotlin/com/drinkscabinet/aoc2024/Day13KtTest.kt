package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.floor
import kotlin.test.assertNull


class Day13KtTest {

    private val testData = """Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(480, this.part1(testData))
        assertEquals(25629, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(875318608908, this.part2(testData))
        assertEquals(107487112929999, this.part2(realData))
    }

    private fun part1(data: String): Long {
        return data.split("\n\n").mapNotNull { solve(it) }.sumOf{3*it.first + it.second}
    }

    private fun part2(data: String): Long {
        return data.split("\n\n").mapNotNull { solve(it, true) }.sumOf{3*it.first + it.second}
    }

    private fun solve(data: String, part2: Boolean=false): Pair<Long, Long>? {
        /*
        Button A: X+94, Y+34     p r
        Button B: X+22, Y+67     q s
        Prize: X=8400, Y=5400    m n
         */
        val nums = Utils.extractLongs(data)
        val p = nums[0]
        val r = nums[1]
        val q = nums[2]
        val s = nums[3]
        val m = nums[4] + if(part2) 10000000000000 else 0
        val n = nums[5] + if(part2) 10000000000000 else 0

        return solve(p, q, r, s, m, n)
    }

    private fun solve(p: Long, q: Long, r: Long, s: Long, m: Long, n: Long): Pair<Long, Long>? {
        val a = (m * s - n * q).toDouble() / (p * s - r * q).toDouble()
        val b = (m - p * a) / q

        if (floor(a) == a && floor(b) == b) {
            return a.toLong() to b.toLong()
        } else {
            return null
        }
    }

    @Test
    fun testSolve() {
        assertEquals(80L to 40L, solve(94, 22, 34, 67, 8400, 5400))

        val input = """Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400"""

        assertEquals(80L to 40L, solve(input))

        val inputFail = """Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176"""

        assertNull(solve(inputFail))
    }
}