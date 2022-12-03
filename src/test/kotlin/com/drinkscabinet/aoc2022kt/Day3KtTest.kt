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

    private val realData = Utils.input(2022, 3)

    @Test
    fun testScore() {
        assertEquals(1, score('a'))
        assertEquals(27, score('A'))
    }

    @Test
    fun testPart1() {
        assertEquals(157, day3part1(testData))
        assertEquals(8038, day3part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(70, day3part1(testData))
        assertEquals(2510, day3part1(realData))
    }
}