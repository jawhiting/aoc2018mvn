package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day6KtTest {

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(7, this.part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, this.part1("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, this.part1("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, this.part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, this.part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
        assertEquals(1034, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(19, this.part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, this.part2("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, this.part2("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, this.part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, this.part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
        assertEquals(2472, this.part2(realData))
    }

    private fun part1(data: String): Int {
        return IntRange(0, data.length).first { data.toCharArray(it, it+4).toSet().size == 4 } + 4
    }

    private fun part2(data: String): Int {
        return IntRange(0, data.length).first { data.toCharArray(it, it+14).toSet().size == 14 } + 14
    }
}