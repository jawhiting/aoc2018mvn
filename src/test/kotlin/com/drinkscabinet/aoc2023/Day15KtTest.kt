package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15KtTest {

    private val testInput = """rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"""

    private val realData = Utils.Companion.input(this)

    private data class Lens(val label: String, var length: Int)

    private data class Box(val id: Long, val lenses: MutableList<Lens> = mutableListOf()) {
        fun remove(label: String) {
            lenses.removeIf { it.label == label }
        }

        fun addOrReplace(lens: Lens) {
            val match = lenses.firstOrNull { it.label == lens.label }

            if (match != null) {
                match.length = lens.length
            } else {
                lenses += lens
            }
        }

        fun power(): Long {
            return lenses.mapIndexed { index, lens -> (id + 1) * (index + 1) * lens.length }.sum()
        }
    }

    private fun hash(s: String): Int {
        var current = 0

        for (c in s.chars()) {
            current += c
            current *= 17
            current %= 256

        }
        return current

    }

    @Test
    fun testHash() {
        assertEquals(52, hash("HASH"))
    }

    @Test
    fun testPart1() {
        assertEquals(1320, part1(testInput))
        assertEquals(1, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(145, part2(testInput))
        assertEquals(145, part2(realData))
    }

    fun part1(data: String): Int {
        return data.split(",").map { hash(it) }.sum()
    }

    fun part2(data: String): Long {
        val instructions = data.split(",")


        val boxes = mutableMapOf<Int, Box>()

        for (i in instructions) {
            if (i.isBlank()) continue
            val parts = i.split("=", "-")
            val label = parts[0]!!
            val hash = hash(label)
            val box = boxes.computeIfAbsent(hash) { Box(hash.toLong()) }
            if (i.contains('=')) {
                box.addOrReplace(
                    Lens(
                        label, parts[1]!!.toInt()
                    )
                )
            } else {
                box.remove(label)
            }
        }

        return boxes.values.map { it.power() }.sum()

    }
}