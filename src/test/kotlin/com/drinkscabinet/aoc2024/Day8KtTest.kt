package com.drinkscabinet.aoc2024

import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day8KtTest {

    private val testData = """............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(14, this.solve(testData))
        assertEquals(220, this.solve(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(34, this.solve(testData, true))
        assertEquals(1974, this.solve(realData, true))
    }

    private fun solve(data: String, part2: Boolean = false): Int {
        val grid = GridString.parse(data)
        val resultGrid = grid.copyOf()
        // Find all the antenna groups
        val antennaGroups = grid.getAll().entries.map{it.value to it.key}.filterNot{it.first=='.'}.groupBy({it.first}, {it.second})
        // Now for each set of matched antennas, calculate the antinodes
        for(e in antennaGroups.entries) {
            println("Antenna ${e.key} has ${e.value.size}")
            for(i in e.value.indices) {
                for(j in i+1..e.value.lastIndex) {
                    if(!part2) {
                        for (n in calcAntinodes(e.value[i], e.value[j])) {
                            if (resultGrid.contains(n)) {
                                resultGrid[n] = '#'
                            }
                        }
                    }
                    else {
                        for (n in calcAntinodes2(e.value[i], e.value[j], grid.getXRange(), grid.getYRange())) {
                            if (resultGrid.contains(n)) {
                                resultGrid[n] = '#'
                            }
                        }
                    }
                }
            }
        }
        println(resultGrid)
        return resultGrid.getAll('#').size
    }

    private fun calcAntinodes(a: Coord, b: Coord) = sequence {
        val diff = a.diff(b)
        yield(a.move(-diff))
        yield(a.move(diff, 2))
    }

    private fun calcAntinodes2(a: Coord, b: Coord, xBounds: LongRange, yBounds: LongRange) = sequence {
        val diff = a.diff(b)
        var pos = a
        // a away from b
        while(pos.x in xBounds && pos.y in yBounds) {
            yield(pos)
            pos = pos + -diff
        }
        // now b away from a
        pos = b
        while(pos.x in xBounds && pos.y in yBounds) {
            yield(pos)
            pos = pos + diff
        }
    }
}