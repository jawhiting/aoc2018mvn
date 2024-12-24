package com.drinkscabinet.aoc2024

import UpDown
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day21KtTest {

    private val testData = """029A
980A
179A
456A
379A"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(126384, this.part1(testData))
        // mine v<<A>>^AvA^Av<<A>>^AAv<A<A>>^AAvAA<^A>Av<A>^AA<A>Av<A<A>>^AAAvA<^A>A
        // mine v<<A>>^AvA^Av<<A>>^AAv<A<A>>^AAvAA<^A>Av<A>^AA<A>Av<A<A>>^AAAvA<^A>A
        // them <v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
        assertEquals(638, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(81, this.part2(testData))
        assertEquals(1289, this.part2(realData))
    }

    fun part1(data: String) : Int {
        var result = 0
        for(d in data.lines()) {
            val length = enterPart1(d)
            val numeric = Utils.extractInts(d)[0]
            println("Code $d length $length numeric $numeric score ${length*numeric}")
            result += length * numeric
        }
        return result
    }

    @Test
    fun testEnterPart1() {
        val example = "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A"
        val expected = """<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
v<<A>>^A<A>AvA<^AA>A<vAAA>^A
<A^A>^^AvvvA
029A"""
        for(e in expected.lines()) {
            println("Expected length: ${e.length}")
        }
        assertEquals(example.length, enterPart1("029A"))
    }

    private fun enterPart1(code: String): Int {
        val pad = Pad(numPadLayout, listOf(UpDown.U, UpDown.R, UpDown.D, UpDown.L))
        val dPad = Pad(dPadLayout, listOf(UpDown.R, UpDown.D, UpDown.L, UpDown.U))

        val pads = listOf(pad, dPad, dPad)
        val code = enter(code, pads)
        println("Code: $code")
        return code.length
    }

    private fun part2(data: String): Int {
        return 0
    }

    private fun enter(number: String, pads: List<Pad>): String {
        // expand out from the base pad
        var code = number
        for((i,p) in pads.withIndex()) {
            val nextCode = p.enter(code).joinToString("")
            println("To enter code $code using pad $i: ${nextCode.length} $nextCode")
            code = nextCode
        }
        return code
    }


    @Test
    fun testNumberPad() {
        val pad = Pad(numPadLayout, listOf(UpDown.U, UpDown.R, UpDown.D, UpDown.L))
        val dPad = Pad(dPadLayout, listOf(UpDown.D, UpDown.R, UpDown.L, UpDown.U))
        val moves = pad.enter("029A").joinToString("")
        println("Moves: " + moves)
        val moves1 = dPad.enter(moves).joinToString("")
        println("Moves1: " + moves1)
        assertEquals(12, moves.length)
        val k1 = "v<<A>>^A<A>AvA<^AA>A<vAAA>^A"
//        println("Mine: " + dPad.move('A', '^').toList().joinToString(""))

        println("Them  : " + k1)
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
        private var dead : Coord = Coord(0,0)

        init {
            layout.lines().forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if (c != '#') {
                        keyPositions[c] = Coord(x, y)
                    }
                    else {
                        dead = Coord(x, y)
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
                        assert(pos != dead, { "Dead at $pos" })
                        break
                    }
                }
            }
        }
    }
}