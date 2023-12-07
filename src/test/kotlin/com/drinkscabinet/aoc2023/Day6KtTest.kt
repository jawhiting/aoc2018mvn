package com.drinkscabinet.aoc2023

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day6KtTest {

    private val testData = mapOf(7L to 9L, 15L to 40L, 30L to 200L)
    private val realData = mapOf(46L to 208L, 85L to 1412L, 75L to 1257L, 82L to 1410L)


    fun distance(time: Long, pressed: Long): Long {
        return (time * pressed) - (pressed * pressed)
    }

    fun winCount(time: Long, minDistance: Long): Long {
        var counter = 0L
        for (p in 1..time) {
            if (distance(time, p) > minDistance) {
                counter++
            }
        }
        return counter
    }



    @Test
    fun testPart1() {
        assertEquals(288L, part1(testData))
        assertEquals(1108800L, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(71503L, part1(mapOf(71530L to 940200)))
        assertEquals(71503L, part1(mapOf(46857582L to 208141212571410L)))

    }
    fun part1(data: Map<Long, Long>): Long {
        return data.map{winCount(it.key, it.value)}.reduce(Long::times)
    }
}