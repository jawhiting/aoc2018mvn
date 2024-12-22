package com.drinkscabinet.aoc2024

import UpDown
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day21KtTest {

    private val testData = """"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(36, this.part1(testData))
        assertEquals(638, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(81, this.part2(testData))
        assertEquals(1289, this.part2(realData))
    }

    private fun part1(data: String): Int {
        return 0
    }

    private fun part2(data: String): Int {
        return 0
    }


    @Test
    fun testNumberPad() {
        val pad = Pad(numPadLayout, listOf(UpDown.U, UpDown.R, UpDown.D, UpDown.L))
        val dPad = Pad(dPadLayout, listOf(UpDown.R, UpDown.D, UpDown.L, UpDown.U))
        val moves = pad.enter("029A").joinToString("")
        println(moves)
        val moves1 = dPad.enter(moves).joinToString("")
        println(moves1)
        assertEquals(12, moves.length)
        val k1 = "v<<A>>^A<A>AvA<^AA>A<vAAA>^A"
        println(dPad.move('A', '^').toList().joinToString(""))

        println(k1)
        assertEquals(k1.length, moves1.length)

        /**
         *    <   A ^ A ^  >  ^ A  vvv  A
         * v<<A>^>A<A>A<A>vA<^A>Av<AAA>^A
         * v<<A>>^A<A>AvA<^AA>A<vAAA>^A
         */


        val k2 = "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A"
        val moves2 = dPad.enter(moves1).joinToString("")
        assertEquals(k2.length, moves2.length)
    }

    private val numPadLayout = """789
            |456
            |123
            |#0A
        """.trimMargin()

    private val dPadLayout = """#^A
            |<v>
        """.trimMargin()

    private class Pad(layout: String, val moveSeq: List<UpDown>) {
        private val keyPositions = mutableMapOf<Char, Coord>()

        init {
            layout.lines().forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if (c != '#') {
                        keyPositions[c] = Coord(x, y)
                    }
                }
            }
        }

        fun enter(number: String) = sequence {
            var pos = 'A'
            for (c in number) {
                yieldAll(move(pos, c).map { it.c })
                // Now press the key
                yield('A')
                pos = c
            }
        }

        fun move(from: Char, to: Char) = sequence {
            // Iterate through the move sequence and pick the first valid one
            val target = keyPositions[to]!!
            var pos = keyPositions[from]!!
            while (pos != target) {
                for(m in moveSeq) {
                    if((pos+m).distance(target) < pos.distance(target)) {
                        // If this move gets us closer, do it
                        yield(m)
                        pos += m
                    }
                }
            }
        }
    }
}