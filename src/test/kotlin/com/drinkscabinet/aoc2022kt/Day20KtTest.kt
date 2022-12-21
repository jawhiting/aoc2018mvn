package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Ring
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day20KtTest {

    private val testData =
        """1
2
-3
3
-2
0
4"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    fun parse(data: String, multiplier: Long = 1): Ring<Long> {
        val ring = Ring<Long>()
        for (line in data.lines()) {

            ring.addBefore(line.toLong() * multiplier)
        }
        return ring
    }

    @Test
    fun testPart1() {
        assertEquals(3, part1(testData))
    }

    @Test
    fun testPart1Real() {
        // 4495 too high
        //1536 too low
        assertEquals(11123, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(1623178306, part2(testData))
    }

    @Test
    fun testPart2real() {
        // 2697722344572 too low
        assertEquals(1623178306, part2(realData))
    }


    fun part1(data: String): Long {
        val ring = parse(data)
        // Get all the entries in order
        val entries = ring.elements().toList()
        for (e in entries) {
            e.move(e.value)
//            println("After entry: $e Ring is $ring")
        }
        // Make 0 the root element
        while (ring.root!!.value != 0L) {
            ring.rotateForward()
        }
        val indices = listOf(1000, 2000, 3000)
        for (i in indices) {
            println(ring[i])
        }
        return indices.sumOf { ring[it].value }
    }


    fun part2(data: String): Long {
        val ring = parse(data, 811589153)
        // Get all the entries in order
        val entries = ring.elements().toList()
        for (i in 1..10) {
            for (e in entries) {
                e.move(e.value)
//            println("After entry: $e Ring is $ring")
            }
        }
        // Make 0 the root element
        while (ring.root!!.value != 0L) {
            ring.rotateForward()
        }
        val indices = listOf(1000, 2000, 3000)
        for (i in indices) {
            println(ring[i])
        }
        return indices.sumOf { ring[it].value }
    }
}