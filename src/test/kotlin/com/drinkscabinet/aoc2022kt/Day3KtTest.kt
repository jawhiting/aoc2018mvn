package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day3KtTest {

    private val testData = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testScore() {
        assertEquals(1, score('a'))
        assertEquals(27, score('A'))
    }

    @Test
    fun testPart1() {
        assertEquals(157, day3part1(testData))
        assertEquals(8039, day3part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(70, day3part2(testData))
        assertEquals(2510, day3part2(realData))
    }
}