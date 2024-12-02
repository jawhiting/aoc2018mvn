package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertTrue


class Day2KtTest {

    private val testData = """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testSafe() {
        assertTrue(safe(listOf(1,2,3,4,5)))
    }

    @Test
    fun testPart1() {
        assertEquals(2, this.part1(testData))
        assertEquals(526, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(4, this.part2(testData))
        assertEquals(566, this.part2(realData))
    }

    private fun safe(report: List<Int>) : Boolean {
        var total = 0
        for(i in 0..report.lastIndex-1) {
            if(report[i] < report[i+1]) {
                total += 1
            }
            else {
                total -= 1
            }
            val diff = abs(report[i]-report[i+1])
            if(diff !in 1..3) return false
        }
        return abs(total) == report.size-1
    }

    private fun safeWithRemoval(report: List<Int>): Boolean {
        var r = report.toMutableList()
        for(i in 0..report.lastIndex) {
            // Make a version without this index
            val tmp = r[i]
            r.removeAt(i)
            if( safe(r)) return true
            // Put it back
            r.add(i, tmp)
        }
        return false
    }

    private fun part1(data: String): Int {

        return data.lines().map { Utils.extractInts(it).toList() }.count(this::safe)
    }

    private fun part2(data: String): Int {
        return data.lines().map { Utils.extractInts(it).toList() }.count(this::safeWithRemoval)

    }
}