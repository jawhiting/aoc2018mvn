package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import com.drinkscabinet.contains
import com.drinkscabinet.overlaps
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day4KtTest {

    private val testData = """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(2, this.part1(testData))
        assertEquals(464, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(4, this.part2(testData))
        assertEquals(770, this.part2(realData))
    }

    private fun part1(data: String): Int {
        return data.lines().map(this::parse).count { it.first.contains(it.second) or it.second.contains(it.first)}
    }

    private fun part2(data: String): Int {
        return data.lines().map(this::parse).count { it.first.overlaps(it.second) }
    }

    fun parse(line: String): Pair<IntRange, IntRange> {
        val nums = Utils.extractUInts(line)
        assert(nums.size == 4)
        return Pair(IntRange(nums[0], nums[1]), IntRange(nums[2], nums[3]))
    }
}