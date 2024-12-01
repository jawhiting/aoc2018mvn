package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day15KtTest {

    private val testData = """Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(26, this.part1(testData, 10))
        assertEquals(5073496, this.part1(realData, 2000000))

    }

    @Test
    fun testPart2() {
        assertEquals(56000011, this.part2(testData, 20))
        assertEquals(13081194638237, this.part2(realData, 4000000))
    }


    private fun part1(data: String, row: Long): Long {
        val sensors = parse(data)
        val beacons = sensors.map { it.second }.toSet()
        val sensorRanges = sensors.associate { it.first to it.first.distance(it.second) }
        // For a given cell, it cannot contain a beacon if it is inside one or more sensor range
        // Check range should be minX - maxRange to maxX + maxRange
        val maxRange = sensorRanges.values.max()
        val minX = sensorRanges.keys.minBy { it.x }.x
        val maxX = sensorRanges.keys.maxByOrNull { it.x }!!.x

        val xRange = minX - maxRange..maxX + maxRange

        return xRange.toList().parallelStream()
            .filter { !canContainBeacon(it, row, sensorRanges) && !beacons.contains(Coord(it, row)) }.count()
    }

    private fun part2(data: String, maxX: Long): Long {
        val sensors = parse(data)
        val sensorRanges = sensors.associate { it.first to it.first.distance(it.second) }

        return freq(scan(maxX, sensorRanges))
    }

    private fun scan(maxX: Long, sensorRanges: Map<Coord, Long>): Coord {
        for (y in 0..maxX) {
            if (y % 1000000L == 0L) {
                println("Y $y")
            }
            var x = 0L
            while (x <= maxX) {
                // Check against beacons
                var skipped = false
                for ((c, r) in sensorRanges) {
                    var distance = c.distance(x, y)
                    if (distance <= r) {
                        // we are inside the range - skip to the other side
                        if (c.x > x) {
                            // before the center of scan
                            // jump to center, then adjust distance
                            distance -= (c.x - x)
                            x = c.x
                        }
                        if (c.x <= x) {
                            // Add on however much remains of the range then an extra one to get outside
                            x += (r - distance) + 1
                        }
//                        println("Skipped to $x using dist $distance")
                        skipped = true
                        break
                    }
                }
                if (!skipped) {
                    // Must be the result
                    return Coord(x, y)
                }
            }
        }
        throw RuntimeException()
    }

    private fun freq(pos: Coord): Long {
        return pos.x * 4000000 + pos.y
    }

    private fun canContainBeacon(x: Long, y: Long, sensorDistances: Map<Coord, Long>): Boolean {
        // For a given cell, it cannot contain a beacon if it is inside one or more sensor range
//        return sensorDistances.entries.parallelStream().allMatch { it.key.distance(x, y) > it.value }
        for (e in sensorDistances) {
            if (e.key.distance(x, y) <= e.value) {
                return false
            }
        }
        return true
    }

    private fun parse(data: String): List<Pair<Coord, Coord>> {
        return data.lines().map(Utils::extractLongs).map { Coord(it[0], it[1]) to Coord(it[2], it[3]) }.toList()
    }
}