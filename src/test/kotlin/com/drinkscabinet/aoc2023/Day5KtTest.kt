package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day5KtTest {

    data class RangeMap(val destination: Int, val start: Int, val length: Int) {
        fun map( value: Int): Int {
            if(value in start..<start+length) {
                return value + (destination-start)
            }
            return destination
        }
    }

    private val testData = """seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(100, part1(testData))
        assertEquals(100, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(100, part2(testData))
        assertEquals(100, part2(realData))
    }

    private fun part1(data: String) : Int {
        return 100
    }

    private fun part2(data: String) : Int {
        return 100
    }
}