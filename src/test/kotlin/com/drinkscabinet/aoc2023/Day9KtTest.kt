package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day9KtTest {
    private val testData = """0 3 6 9 12 15
    1 3 6 10 15 21
    10 13 16 21 30 45"""


    private val realData = Utils.input(this)

    private fun diffSeq(n: List<Long>) : List<Long> {
        return n.zipWithNext().map { it.second - it.first }
    }

    private fun nextInSeq(n: List<Long>) : Long {
        if(n.any { it != 0L }) {
            return n.last() + nextInSeq(diffSeq(n))
        }
        return 0
    }

    private fun prevInSeq(n: List<Long>) : Long {
        if(n.any { it != 0L }) {
            return n.first() - prevInSeq(diffSeq(n))
        }
        return 0
    }

    @Test
    fun testDiffSeq() {
        assertEquals(listOf<Long>(3, 3, 3, 3, 3), diffSeq(listOf(0, 3, 6, 9, 12, 15)))
    }

    @Test
    fun testNextInSeq() {
        assertEquals(18, nextInSeq(listOf(0, 3, 6, 9, 12, 15)))
        assertEquals(68, nextInSeq(Utils.extractLongs("10  13  16  21  30  45").toList()))
        assertEquals(5, prevInSeq(Utils.extractLongs("10  13  16  21  30  45").toList()))
    }

    @Test
    fun testPart1() {
        assertEquals(114, part1(testData))
        assertEquals(1992273652, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(2, part2(testData))
        assertEquals(1012, part2(realData))
    }

    fun part1(data: String): Long {
       return data.lines().map { Utils.extractLongs(it).toList() }.map { nextInSeq(it) }.sum()
    }

    fun part2(data: String): Long {
        return data.lines().map { Utils.extractLongs(it).toList() }.map { prevInSeq(it) }.sum()
    }
}