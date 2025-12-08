package com.drinkscabinet.aoc2025

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day5KtTest {

    private val testData = """3-5
10-14
16-20
12-18

1
5
8
11
17
32"""



    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(3, this.part1(testData))
        assertEquals(525, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(14, this.part2(testData))
        assertEquals(333892124923577, this.part2(realData))
    }

    private fun part1(data: String): Int {
        var result = 0
        val ranges = mutableListOf<LongRange>()
        for(line in data.lines()) {
            val nums = Utils.extractULongs(line)
            if(nums.size == 2)
                ranges.add(LongRange(nums[0], nums[1]))
            else if (nums.size == 1)
                // ingredient
                result += if(isFresh(nums[0], ranges)) 1 else 0
        }
        return result
    }

    private fun isFresh(id: Long, ranges: List<LongRange>): Boolean {
        return ranges.any { r -> id in r }
    }

    private fun part2(data: String): Long {
        var ranges = mutableListOf<LongRange>()
        for(line in data.lines()) {
            val nums = Utils.extractULongs(line)
            if(nums.size == 2)
                // normalise range first
                ranges.add(LongRange(nums[0], nums[1]))
        }
        ranges.sortWith(Comparator { r1, r2 -> r1.first.compareTo(r2.first) })
        val nr = normalise(ranges)
        return nr.sumOf { abs(it.last - it.first) + 1 }
    }

    private fun normalise(ranges: List<LongRange>): List<LongRange> {
        val result = mutableListOf<LongRange>()
        var current = ranges.first()
        for(r in ranges.drop(1)) {
            if(r.first <= current.last + 1) {
                current = LongRange(current.first, maxOf(current.last, r.last))
            } else {
                result.add(current)
                current = r
            }
        }
        result.add(current)
        return result
    }
}