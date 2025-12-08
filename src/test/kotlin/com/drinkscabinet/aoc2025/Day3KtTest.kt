package com.drinkscabinet.aoc2025

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day3KtTest {

    private val testData = """987654321111111
811111111111119
234234234234278
818181911112111"""



    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(89, this.joltage("811111111111119"))
        assertEquals(357, this.part1(testData))
        assertEquals(17166, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(434234234278, this.part2("234234234234278"))
        assertEquals(888911112111, this.part2("818181911112111"))
        assertEquals(3121910778619, this.part2(testData))
        // 169057317650774 too low
        // 169077317650774
        assertEquals(169077317650774, this.part2(realData))
    }

    private fun part1(data: String): Int {
        return data.lines().sumOf { joltage(it) }
    }

    private fun part2(data: String): Long {
        return data.lines().sumOf { joltage4(it) }
    }

    private fun joltage(data: String): Int {
        val m1 = firstMax(data.take(data.lastIndex))
        val m2 = firstMax(data.substring(m1+1)) + m1 + 1

        return data[m1].digitToInt()*10+data[m2].digitToInt()
    }

    private fun joltage4(data: String): Long {
        var d = data
        var result = 0L
        for(i in 11 downTo 0) {
            val p = firstMaxLeaving(d, i)
            val c = d[p].digitToInt()
            result = result * 10 + c
            d = d.removeRange(0..p)
        }
        return result
    }


    private fun firstMax(data: String): Int {
        for(n in 9 downTo 0) {
            val p = data.indexOfFirst { it == n.digitToChar() }
            if(p != -1) {
                return p
            }
        }
        throw RuntimeException("No max")
    }

    // Return index of first max, but ensuring leaving chars are left
    private fun firstMaxLeaving(s: String, leaving: Int) : Int {
        val pickFrom = s.dropLast(leaving)
        return firstMax(pickFrom)
    }

    private fun removeFirstLowest(data: String): String {
        for(n in 0..9) {
            val p = data.indexOfFirst { it == n.digitToChar() }
            if(p != -1) {
                return data.removeRange(p..p)
            }
        }
        throw RuntimeException("No lowest")
    }

}