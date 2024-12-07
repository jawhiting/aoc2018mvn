package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import com.google.common.math.LongMath.pow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.ceil
import kotlin.math.log10


class Day7KtTest {

    private val testData = """190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(3749, this.part1(testData))
        assertEquals(7579994664753, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(11387, this.part2(testData))
        assertEquals(1974, this.part2(realData))
        // 43865066662611 too low
    }

    private fun part1(data: String): Long {
        return data.lines().map { Utils.extractLongs(it).toList() }
            .filter { canMatch(it[0], 0L, it.subList(1, it.size), false) }
            .sumOf { it[0] }
    }

    private fun part2(data: String): Long {
        return data.lines().map { Utils.extractLongs(it).toList() }
            .filter { canMatch(it[0], 0L, it.subList(1, it.size), true) }
            .sumOf { it[0] }
    }

    private fun canMatch(target: Long, runningTotal: Long, numbers: List<Long>, part2: Boolean): Boolean {
        if (numbers.isEmpty()) {
            return target == runningTotal
        }
        if (runningTotal > target) return false

        // Now try both options
        val plusResult = canMatch(target, runningTotal * numbers.first(), numbers.subList(1, numbers.size), part2)
        if (plusResult) {
            print("*")
            return true
        } else {
            val multResult = canMatch(target, runningTotal + numbers.first(), numbers.subList(1, numbers.size), part2)
            if (multResult) {
                print("+")
                return true
            } else {
                if(part2) {
                    val concatResult =
                        canMatch(target, concat(runningTotal, numbers.first()), numbers.subList(1, numbers.size), part2)
                    if (concatResult) {
                        print("||")
                    }
                    return concatResult
                }
                else {
                    return false
                }
            }
        }
    }

    @Test
    fun testCanMatch() {
        val d = "7290: 6 8 6 15"
        val nums = Utils.extractLongs(d).toList()
        assertEquals(true, canMatch(nums[0], 0, nums.subList(1, nums.size), true))
    }

    private fun concat(a: Long, b: Long): Long {
        val p = ceil(log10(b.toDouble())).toInt()
        val mult = pow(10L, p)
        return a * mult + b
    }

    @Test
    fun testConcat() {
        assertEquals(12345, concat(123, 45))
        assertEquals(99999, concat(99, 999))
    }
}