package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day1KtTest {

    private val testData = """3   4
4   3
2   5
1   3
3   9
3   3"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(11, this.part1(testData))
        assertEquals(3246517, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(31, this.part2(testData))
        assertEquals(29379307, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val l1 = mutableListOf<Int>()
        val l2 = mutableListOf<Int>()
        data.lines().map { Utils.extractInts(it) }.forEach { t ->
            l1.add(t[0])
            l2.add(t[1])
        }
        l1.sort()
        l2.sort()
        return l1.zip(l2).sumOf { abs(it.first - it.second) }
    }

    private fun part2(data: String): Int {
        val l1 = mutableListOf<Int>()
        val l2 = mutableListOf<Int>()
        data.lines().map { Utils.extractInts(it) }.forEach { t ->
            l1.add(t[0])
            l2.add(t[1])
        }
        var freq = l2.groupingBy { it }.eachCount()

        return l1.sumOf { it * freq.getOrDefault(it, 0) }

    }
}