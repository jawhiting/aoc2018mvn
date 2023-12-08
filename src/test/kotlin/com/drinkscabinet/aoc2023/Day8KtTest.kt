package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import com.drinkscabinet.Utils.Companion.lcm
import com.drinkscabinet.Utils.Companion.lcmList
import com.github.jsonldjava.shaded.com.google.common.math.LongMath.gcd
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day8KtTest {
    private val testData = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)"""

    private val testData2 = """LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)"""

    private val testData3 = """LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)"""
    private val realData = Utils.input(this)

    private enum class Direction { L, R }
    private data class Node(val id: String, val left: String, val right: String) {


        companion object {

            fun parse(data: String): Node {
                val parts = data.split(" = (", ", ", ")")
                return Node(parts[0], parts[1], parts[2])
            }
        }
    }

    private data class Nodes(val directions: List<Direction>, val nodes: Map<String, Node>) {
        fun next(n: Node, d: Direction): Node {
            return when (d) {
                Direction.L -> nodes[n.left]!!
                Direction.R -> nodes[n.right]!!
            }
        }

        fun part2() : Long {
            // Find all the start nodes
            val startNodes = nodes.values.filter { it.id.endsWith("A") }
            val multiples = startNodes.map { part2Node(it) }
            // Now find the lowest common multiple of multiples
            val lcm = lcmList(multiples)
            println("LCM is $lcm")
            return lcm
        }

        fun part2Node(n: Node) : Long {
            var current = n
            var i = 0L
            do {
                val d = directions[(i % directions.size).toInt()]
                current = next(current, d)
                i++
            } while(!current.id.endsWith("Z"))
            println("Path from $n takes $i steps")
            return i
        }
        fun part1(): Long {
            var current = nodes["AAA"]!!
            var i = 0L
            do {
                val d = directions[(i % directions.size).toInt()]
                current = next(current, d)
                i++
            } while(current.id != "ZZZ")
            return i
        }

        companion object {
            fun parse(data: String): Nodes {
                val directions = data.lines()[0].toCharArray().filter{it.isLetter()}.map { Direction.valueOf(it.toString()) }.toList()
                val nodeMap = data.lines().filter{it.contains("=")}.map { Node.parse(it) }.associateBy { it.id }
                return Nodes(directions, nodeMap)
            }
        }
    }

    @Test
    fun testParse() {
        assertEquals("AAA", Node.parse("AAA = (BBB, CCC)").id)
        assertEquals("BBB", Node.parse("AAA = (BBB, CCC)").left)
        assertEquals("CCC", Node.parse("AAA = (BBB, CCC)").right)
    }

    @Test
    fun testParseNodes() {
        val n = Nodes.parse(testData)
        assertEquals(2, n.directions.size)
        assertEquals(Direction.R, n.directions[0])
        assertEquals(Direction.L, n.directions[1])
        assertEquals(7, n.nodes.size)
        assertEquals("AAA", n.nodes["AAA"]!!.id)
        assertEquals("BBB", n.nodes["AAA"]!!.left)
        assertEquals("CCC", n.nodes["AAA"]!!.right)
    }

    @Test
    fun testPart1() {
        assertEquals(2, part1(testData))
        assertEquals(6, part1(testData2))
        assertEquals(11911, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(6, part2(testData3))
        assertEquals(10151663816849, part2(realData))
    }

    fun part1(data: String): Long {
        val n = Nodes.parse(data)
        return n.part1()
    }

    fun part2(data: String): Long {
        val n = Nodes.parse(data)
        return n.part2()
    }
}