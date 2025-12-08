package com.drinkscabinet.aoc2025

import com.drinkscabinet.Coord3
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.PriorityQueue
import java.util.TreeSet
import kotlin.math.abs


class Day8KtTest {

    private val testData = """162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(40, this.part1(testData, 10))
        // 1452 too low
        assertEquals(90036, this.part1(realData, 1000))
    }

    @Test
    fun testPart2() {
        assertEquals(25272, this.part2(testData))
        assertEquals(6083499488, this.part2(realData))
    }

    private fun part1(data: String, connections: Int): Long {
        val boxes = data.lines().map { Coord3.of(it) }.toList()
        val allDistances = mutableMapOf<Set<Coord3>, Double>()
        for (i in boxes.indices) {
            for (j in i + 1..boxes.lastIndex) {
                allDistances[sortedSetOf(boxes[i], boxes[j])] = boxes[i].euclidianDistance(boxes[j])
            }
        }
        // Now sort by distance
        val sorted = allDistances.entries.sortedBy { it.value }
        // Now form circuits from the first n
        val circuits = mutableListOf<MutableSet<Coord3>>()
        // populate all the single circuits
        for (box in boxes) {
            val s = TreeSet<Coord3>()
            s.add(box)
            circuits.add(s)
        }
        var i = 0
        var connectionsMade = 0
        while (connectionsMade < connections) {
            val connection = sorted[i++]
            val aBox = connection.key.first()
            val bBox = connection.key.last()
            val acircuit = getCircuit(aBox, circuits)!!
            val bcircuit = getCircuit(bBox, circuits)!!

            if (acircuit == bcircuit) {
                // don't think this should happen, flag
//                    throw RuntimeException("Already connected ${connection.key} and ${connection.value}")
                connectionsMade++
            } else {
                // Need to join the circuits
                circuits.remove(acircuit)
                circuits.remove(bcircuit)
                val mergedSet = TreeSet<Coord3>()
                mergedSet.addAll(acircuit)
                mergedSet.addAll(bcircuit)
                circuits.add(mergedSet)
                connectionsMade++
            }
        }
        // Get 3 largest circuits and multiply them
        return circuits.map { it.size }.sortedDescending().take(3).toIntArray().reduce { acc, i -> acc * i }.toLong()
    }

    private fun getCircuit(box: Coord3, circuits: List<MutableSet<Coord3>>): MutableSet<Coord3>? {
        return circuits.find { box in it }
    }

    private fun part2(data: String): Long {
        val boxes = data.lines().map { Coord3.of(it) }.toList()
        val allDistances = mutableMapOf<Set<Coord3>, Double>()
        for (i in boxes.indices) {
            for (j in i + 1..boxes.lastIndex) {
                allDistances[sortedSetOf(boxes[i], boxes[j])] = boxes[i].euclidianDistance(boxes[j])
            }
        }
        // Now sort by distance
        val sorted = allDistances.entries.sortedBy { it.value }
        // Now form circuits from the first n
        val circuits = mutableListOf<MutableSet<Coord3>>()
        // populate all the single circuits
        for (box in boxes) {
            val s = TreeSet<Coord3>()
            s.add(box)
            circuits.add(s)
        }
        var i = 0
        var connectionsMade = 0
        var connection = sorted[0]
        while (circuits.size > 1) {
            connection = sorted[i++]
            val aBox = connection.key.first()
            val bBox = connection.key.last()
            val acircuit = getCircuit(aBox, circuits)!!
            val bcircuit = getCircuit(bBox, circuits)!!

            if (acircuit == bcircuit) {
                // don't think this should happen, flag
//                    throw RuntimeException("Already connected ${connection.key} and ${connection.value}")
                connectionsMade++
            } else {
                // Need to join the circuits
                circuits.remove(acircuit)
                circuits.remove(bcircuit)
                val mergedSet = TreeSet<Coord3>()
                mergedSet.addAll(acircuit)
                mergedSet.addAll(bcircuit)
                circuits.add(mergedSet)
                connectionsMade++
            }
        }
        println("Last connection is $connection circuits size is ${circuits.size}")
        return connection.key.first().x * connection.key.last().x
    }
}