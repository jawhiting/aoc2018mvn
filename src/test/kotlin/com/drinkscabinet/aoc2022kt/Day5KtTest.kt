package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayDeque


class Day5KtTest {

    private val testData = """move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals("CMZ", this.part1(testData))
        assertEquals("VQZNJMWTR", this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals("MCD", this.part2(testData))
        assertEquals("NLCDCLVMQ", this.part2(realData))
    }

    private fun part1(data: String): String {
        val parsed = parse(data)
        val stacks = parsed.first
        val instructions = parsed.second

        for (instruction in instructions) {
            val ints = Utils.extractInts(instruction)

            assert(ints.size == 3)
            for (i in 1..ints[0]) {
                stacks[ints[2]]!!.addLast(stacks[ints[1]]!!.removeLast())
            }
        }
        return stacks.values.map { it.last() }.joinToString(separator = "")
    }

    private fun part2(data: String): String {
        val parsed = parse(data)
        val stacks = parsed.first
        val instructions = parsed.second

        for (instruction in instructions) {
            val ints = Utils.extractInts(instruction)

            assert(ints.size == 3)
            // Move blocks in order
            stacks[ints[2]]!!.addAll(stacks[ints[1]]!!.takeLast(ints[0]))
            // remove the ones we just moved
            for( i in 1..ints[0]) {
                stacks[ints[1]]!!.removeLast()
            }
        }
        return stacks.values.map { it.last() }.joinToString(separator = "")
    }

    private fun parse(data: String): Pair<SortedMap<Int, ArrayDeque<Char>>, List<String>> {

        return if (data == testData) {
            Pair(getTestStacks(), testData.lines())
        } else {
            Pair(getStacks(), realData.lines())
        }
    }

    private fun getTestStacks(): SortedMap<Int, ArrayDeque<Char>> {
        val stacks = TreeMap<Int, ArrayDeque<Char>>()

        stacks[1] = ArrayDeque(listOf('Z', 'N'))
        stacks[2] = ArrayDeque(listOf('M', 'C', 'D'))
        stacks[3] = ArrayDeque(listOf('P'))
        return stacks
    }

    private fun getStacks(): SortedMap<Int, ArrayDeque<Char>> {
        /*
            [L] [M]         [M]
        [D] [R] [Z]         [C] [L]
        [C] [S] [T] [G]     [V] [M]
[R]     [L] [Q] [B] [B]     [D] [F]
[H] [B] [G] [D] [Q] [Z]     [T] [J]
[M] [J] [H] [M] [P] [S] [V] [L] [N]
[P] [C] [N] [T] [S] [F] [R] [G] [Q]
[Z] [P] [S] [F] [F] [T] [N] [P] [W]
 1   2   3   4   5   6   7   8   9
         */
        val stacks = TreeMap<Int, ArrayDeque<Char>>()

        stacks[1] = ArrayDeque("ZPMHR".toCharArray().asList())
        stacks[2] = ArrayDeque("PCJB".toCharArray().asList())
        stacks[3] = ArrayDeque("SNHGLCD".toCharArray().asList())
        stacks[4] = ArrayDeque("FTMDQSRL".toCharArray().asList())
        stacks[5] = ArrayDeque("FSPQBTZM".toCharArray().asList())
        stacks[6] = ArrayDeque("TFSZBG".toCharArray().asList())
        stacks[7] = ArrayDeque("NRV".toCharArray().asList())
        stacks[8] = ArrayDeque("PGLTDVCM".toCharArray().asList())
        stacks[9] = ArrayDeque("WQNJFML".toCharArray().asList())
        return stacks
    }


}