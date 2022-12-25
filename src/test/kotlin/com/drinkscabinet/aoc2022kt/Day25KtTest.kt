package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day25KtTest {

    private val testData = """1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals("2=-1=0", solve(testData))
    }

    @Test
    fun testPart1Real() {
        // 4495 too high
        //1536 too low
        assertEquals("2--1=0=-210-1=00=-=1", solve(realData))
    }

    private val digits = mapOf('2' to 2, '1' to 1, '0' to 0, '-' to -1, '=' to -2)

    private fun solve(data: String): String {
        val total = data.lines().map { fromSnafu(it) }.sum()
        println(total)
        println(toSnafu(total))
        return toSnafu(total)
    }

    @Test
    fun testFromSnafu() {
        assertEquals(1747, fromSnafu("1=-0-2"))
    }

    private fun fromSnafu(value: String): Long {
        var result = 0L
        for (c in value.toCharArray()) {
            result = result * 5 + digits[c]!!
        }
        return result
    }

    private val snafu = mapOf<Long, Char>(0L to '0', 1L to '1', 2L to '2', 3L to '=', 4L to '-')

    @Test
    fun testToSnafu() {
        assertEquals("20", toSnafu(10))
        assertEquals("1=0", toSnafu(15))
        assertEquals("1121-1110-1=0", toSnafu(314159265))
    }

    private fun toSnafu(value: Long): String {
        var current = value
        var result = ""
        do {
            val rem = current % 5
            val digit = snafu[rem]!!
            result = digit + result
            current -= digits[digit]!!
            current /= 5
        } while (current != 0L)
        return result
    }

}

