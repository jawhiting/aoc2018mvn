package com.drinkscabinet.aoc2025

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day1KtTest {

    private val testData = """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82"""

    private val testData2 = """R1000"""

    private val testData3 = """L50
R50
L50
R50"""



    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(3, this.part1(testData))
        assertEquals(1135, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(6, this.part2(testData))
        assertEquals(10, this.part2(testData2))
        assertEquals(2, this.part2(testData3))
        //6873 too high
        assertEquals(6558, this.part2(realData))
    }

    private fun part1(data: String): Int {
        var current = 50
        var result = 0
        for(line in data.lines()) {
            val dir = line[0]
            val dist = line.substring(1).toInt()
            current += if (dir == 'R') dist else -dist
            current %= 100
            if(current < 0) current += 100
            if(current == 0) result++
        }
        return result
    }

    private fun part2(data: String): Int {
        var current = 50
        var result = 0
        for(line in data.lines()) {
            val dir = line[0]
            var dist = line.substring(1).toInt()
            // Normalise to take account of eg R1000
            result += dist / 100
            dist %= 100
            if(dist != 0) {
                val prev = current
                current += if (dir == 'R') dist else -dist
//                println("$line Moved from $prev to $current")
                if(prev != 0) {
                    if (current <= 0 || current > 99) {
//                        println("Crossed or at 0")
                        result++
                    }
                }
                current %= 100
                if(current < 0) current += 100

//                println("Normalised to $current")
            }
        }
        return result
    }
}