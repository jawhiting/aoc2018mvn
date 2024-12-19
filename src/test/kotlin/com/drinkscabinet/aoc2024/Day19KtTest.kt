package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day19KtTest {

    private val testData = """r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb"""


    // Get the year and day from the class
    private val realData = Utils.input(this)

    private fun parse(data: String) : Pair<List<String>, List<String>> {
        val s = data.split("\n\n")
        val towels = s[0].split(", ").map{it.trim()}

        val patterns = s[1].lines().map { it.trim() }
        return towels to patterns
    }

    @Test
    fun testPart1() {
        assertEquals(6, this.part1(testData))
        assertEquals(238, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(16, this.part2(testData))
        assertEquals(635018909726691, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val (towels, patterns) = parse(data)
        return patterns.count { canMake2(it, towels) > 0 }
    }

    private fun part2(data: String): Long {
        cache.clear()
        val (towels, patterns) = parse(data)
        return patterns.sumOf { canMake2(it, towels) }
    }

    private val cache = mutableMapOf<String, Long>()

    private fun canMake2(pattern: String, towels: Iterable<String>) : Long {
        if(pattern.isEmpty()) return 1

        // Otherwise we need to recurse
        val options = towels.filter { pattern.startsWith(it) }
        var count = 0L
        for(o in options) {
            val remainingPattern = pattern.substring(o.length)
            count += cache.getOrPut(remainingPattern) { canMake2(remainingPattern, towels) }
        }
        return count
    }


}