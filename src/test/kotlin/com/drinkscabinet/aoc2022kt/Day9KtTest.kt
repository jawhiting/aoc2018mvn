package com.drinkscabinet.aoc2022kt

import UpDown
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day9KtTest {

    private val testData = """R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2"""
    private val testData2 = """R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(13, this.part1(testData))
        assertEquals(6212, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(1, this.part2(testData))
        assertEquals(36, this.part2(testData2))
        assertEquals(2522, this.part2(realData))
    }

    private fun part1(data: String): Int {
        var head = Coord(0,0)
        var tail = Coord(0,0)
        val seen = mutableSetOf<Coord>()
        seen.add(tail)

        for(line in data.lines()) {
            val tokens = line.split(" ")
            val dir = UpDown.valueOf(tokens[0])
            val distance = tokens[1].toInt()

            for( i in 1..distance) {
                // move head
                head = head.move(dir)
                tail = move(head, tail)
                seen.add(tail)
            }
        }
        return seen.size
    }

    private fun move(head: Coord, tail: Coord): Coord {
        // Now move tail towards it if necessary
        if (abs(head.x - tail.x) > 1 && head.y == tail.y) {
            // same row
            return Coord(tail.x + delta(head.x, tail.x), tail.y)
        }
        else if( abs(head.y - tail.y) > 1 && head.x == tail.x) {
            // same col
            return Coord(tail.x, tail.y + delta(head.y, tail.y))
        }
        else if( abs(head.x - tail.x) > 1 || abs(head.y-tail.y) > 1) {
            // diagonal
            return Coord(tail.x + delta(head.x, tail.x), tail.y + delta(head.y, tail.y))
        }
        return tail
    }

    private fun delta(headVal: Long, tailVal: Long): Int {
        return if( headVal > tailVal) {
            1
        } else {
            -1
        }
    }

    private fun part2(data: String): Int {
        val knots = arrayListOf<Coord>()
        for( i in 1..10) {
            knots.add(Coord(0,0))
        }
        val seen = mutableSetOf<Coord>()
        seen.add(knots[0])

        for(line in data.lines()) {
            val tokens = line.split(" ")
            val dir = UpDown.valueOf(tokens[0])
            val distance = tokens[1].toInt()

            for( d in 1..distance) {
                // move head
                knots[0] = knots[0].move(dir)
                for( i in 1..9) {
                    knots[i] = move(knots[i-1], knots[i])
                }
                seen.add(knots[9])
            }
        }
        return seen.size
    }

}