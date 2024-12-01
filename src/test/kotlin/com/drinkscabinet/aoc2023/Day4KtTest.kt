package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import com.google.common.math.IntMath.pow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day4KtTest {

    private val testData = """Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"""

    data class Card(val id: Int, val winners: Set<Int>, val numbers: Set<Int>) {
        fun score(): Int {
            // calculate 2 to the power of 4
            val c = winners.intersect(numbers).size
            if (c== 0) return 0
            return pow(2, c-1)
        }

        fun score2(): Int {
            return winners.intersect(numbers).size
        }

        companion object {
            fun parse(data: String) : Card {
                val parts = data.split(":","|")
                val id = Utils.extractInts(parts[0])[0]
                val winners = Utils.extractInts(parts[1]).toSet()
                val numbers = Utils.extractInts(parts[2]).toSet()
                val c =  Card(id, winners, numbers)
                cards[id] = c
                return c
            }

            private val cards = mutableMapOf<Int, Card>()
        }
    }

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(13, part1(testData))
        assertEquals(21485, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(30, part2(testData))
        assertEquals(11024379, part2(realData))
    }

    private fun part1(data: String) : Int {
        val lines = data.lines()
        return lines.map { Card.parse(it) }.map { it.score() }.sum()
    }

    private fun part2(data: String) : Int {
        val lines = data.lines()
        val cards = lines.map { Card.parse(it) }.toList()
        val counts = mutableMapOf<Int, Int>()
        cards.forEach { counts.merge(it.id, 1, Int::plus) }

        for (c in cards) {
            val s = c.score2()
            for (i in 1..s) {
                counts.merge(c.id + i, counts[c.id]!!, Int::plus)
            }
        }
        return counts.values.sum()
    }
}