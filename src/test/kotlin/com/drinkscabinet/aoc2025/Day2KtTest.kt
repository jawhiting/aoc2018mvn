package com.drinkscabinet.aoc2025

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt


class Day2KtTest {

    private val testData = """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""




    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(11, this.part1("3-16"))
        assertEquals(1227775554, this.part1(testData))
        // 30323879635 too low
        assertEquals(30323879646, this.part1(realData))
    }


    @Test
    fun testPart2() {
        assertEquals(4174379265, this.part2(testData))
        //6873 too high
        assertEquals(43872163557, this.part2(realData))
    }

    @Test
    fun testRoot() {
        assertEquals(0, getRoot(3))
        assertEquals(1, getRoot(11))
        assertEquals(1, getRoot(111))
        assertEquals(11, getRoot(1111))
    }

    @Test
    fun testRepeat() {
        assertEquals(11, repeat(1))
        assertEquals(1010, repeat(10))
        assertEquals(1212, repeat(12))
        assertEquals(123123, repeat(123))
    }

    @Test
    fun testIsRepeat() {
        assertEquals(false, isRepeat(1))
        assertEquals(false, isRepeat(12))
        assertEquals(false, isRepeat(121))
        assertEquals(true, isRepeat(11))
        assertEquals(true, isRepeat(1212))
        assertEquals(true, isRepeat(1111111111))
        assertEquals(true, isRepeat(11111111111))
        assertEquals(false, isRepeat(1111111112))
    }

    private fun part1(data: String): Long {
        var total = 0L
        val ranges = data.split(",")
        for(r in ranges) {
            val nums = Utils.extractULongs(r)
            val start = nums[0]
            val end = nums[1]

            var root = getRoot(start)
            var repeated = repeat(root)
            while(repeated <= end) {
                if(repeated >= start) {
                    total += repeated
                }
                root++
                repeated = repeat(root)
            }
        }
        return total
    }

    private fun getRoot(n: Long): Long {
        val s = n.toString()
        val rootStr = s.take(floor(s.length/2.0).roundToInt())
        return if(rootStr.isNotEmpty()) rootStr.toLong() else 0
    }

    private fun repeat(n: Long): Long {
//        if(n < 10) return n*10+n
        val mag = floor(log10(n.toDouble())).roundToInt()+1
        return n * 10.0.pow(mag.toDouble()).roundToInt() + n
    }

    private fun isRepeat(n: Long): Boolean {
        val s = n.toString()
        val maxLen = max(1, s.length/2)
        return IntRange(1, maxLen).any{ isRepeat(s, it)}
    }

    private fun isRepeat(s: String, n: Int): Boolean {
        val chunks = s.chunked(n)
        return chunks.size > 1 &&  chunks.all{ it == chunks[0] }
    }

    private fun part2(data: String): Long {
        var total = 0L
        val ranges = data.split(",")
        for(r in ranges) {
            val nums = Utils.extractULongs(r)
            val start = nums[0]
            val end = nums[1]

            LongRange(start, end).filter{ isRepeat(it) }.forEach{ total += it }
        }
        return total
    }
}