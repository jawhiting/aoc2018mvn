package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.Math.pow
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10


class Day7KtTest {

    private val testData = """190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(3749, this.part1(testData))
        assertEquals(7579994664753, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(11387, this.part2(testData))
        assertEquals(438027111276610, this.part2(realData))
    }

    private fun part1(data: String): Long {
        return data.lines().map{Utils.extractLongs(it).toList()}.filter { matches(it[0], 0L, it.subList(1, it.size)) }.sumOf { it[0] }
    }

    private fun part2(data: String): Long {
        return data.lines().map{Utils.extractLongs(it).toList()}.filter { matches2(it[0], 0L, it.subList(1, it.size)) }.sumOf { it[0] }
    }

    fun matches(target: Long, runningTotal: Long, numbers: List<Long>) : Boolean {
        if(numbers.isEmpty()) {
            return target == runningTotal
        }
        val child = numbers.subList(1, numbers.size)
        return matches(target, runningTotal * numbers[0], child) ||
                matches(target, runningTotal + numbers[0], child)
    }

    fun matches2(target: Long, runningTotal: Long, numbers: List<Long>) : Boolean {
        if(numbers.isEmpty()) {
            return target == runningTotal
        }
        val child = numbers.subList(1, numbers.size)
        return matches2(target, runningTotal * numbers[0], child) ||
            matches2(target, runningTotal + numbers[0], child) ||
            matches2(target, concat2(runningTotal,numbers[0]), child)
    }



    fun concat(a: Long, b: Long): Long {
        // concatenate two numbers together
        val result = (a.toString() + b.toString()).toLong()
        val result2 = concat2(a,b)
        if(result2 != result) {
            println("a=$a b=$b result=$result result2=$result2")
        }
        return result
    }

    fun concat2(a: Long, b: Long): Long {
        val p = floor(log10(b.toDouble())).toInt()+1
        val mult = pow(10.0, p.toDouble()).toLong()
        return (a * mult + b)
    }

    @Test
    fun testConcat() {
        assertEquals(1234L, concat(1L, 234L))
        assertEquals(1234L, concat2(1L, 234L))
        assertEquals(1231L, concat2(123L, 1L))
    }
}