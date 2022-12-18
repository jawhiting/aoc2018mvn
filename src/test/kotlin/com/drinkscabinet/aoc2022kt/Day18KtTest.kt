package com.drinkscabinet.aoc2022kt

import PathFinder
import com.drinkscabinet.Coord3
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class Day18KtTest {

    private val testData = """2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(64, part1(testData))
        assertEquals(4364, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(58, part2(testData))
    }

    @Test
    fun testPart2real() {
        // 2508 too low
        assertEquals(2508, part2(realData))
    }

    @Test
    fun testCanReach() {
        val points = parse(testData)
        val bounds = getBounds(points)
        assertTrue(canReachOrigin(Coord3(bounds.last, bounds.last, bounds.last), points, bounds))
        assertFalse(canReachOrigin(Coord3(2, 2, 5), points, bounds))
    }

    fun part1(data: String): Long {
        val points = parse(data)
        // For each point, find out how many neighbours are in points
        var faces = 0L
        for (p in points) {
            val n6 = p.neighbours6()
            val populated = n6.intersect(points)

            faces += 6 - populated.size
        }
        return faces
    }

    fun part2(data: String): Long {
        val points = parse(data)
        // For each point, find out how many neighbours are in points
        var faces = 0L
        val bounds = getBounds(points)
        for (p in points) {
            val n6 = p.neighbours6()
            val exposed = n6.subtract(points)

            val external = exposed.filter { canReachOrigin(it, points, bounds) }
            faces += external.size
        }
        return faces
    }

    private fun parse(data: String): Set<Coord3> {
        val result = data.lines().map { Utils.extractInts(it) }.map { Coord3(it[0], it[1], it[2]) }.toSet()
        println("x=${result.minOf { it.x }} to ${result.maxOf { it.x }}")
        println("y=${result.minOf { it.y }} to ${result.maxOf { it.y }}")
        println("z=${result.minOf { it.z }} to ${result.maxOf { it.z }}")
        return result
    }

    private val reachCache = mutableSetOf<Coord3>()
    private val notReachCache = mutableSetOf<Coord3>()

    private fun getBounds(points: Set<Coord3>): LongRange {
        return points.flatMap { it.elements() }.min() - 1..points.flatMap { it.elements() }.max() + 1
    }

    private fun canReachOrigin(start: Coord3, points: Set<Coord3>, bounds: LongRange): Boolean {
        if (start in reachCache) return true
        if (start in notReachCache) return false
        val origin = Coord3(0, 0, 0)
        val result = PathFinder.dijkstra2(start, origin) { pos ->
            pos.neighbours6().subtract(points).filter { it.x in bounds && it.y in bounds && it.z in bounds }
                .map { it to 1 }
        }
        if (result.first.containsKey(origin)) {
            reachCache.addAll(result.first.keys)
            return true
        } else {
            notReachCache.addAll(result.first.keys)
            return false
        }
    }
}