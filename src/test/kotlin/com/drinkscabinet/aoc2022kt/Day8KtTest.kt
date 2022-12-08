package com.drinkscabinet.aoc2022kt

import Direction
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day8KtTest {

    private val testData = """30373
25512
65332
33549
35390"""
    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(21, this.part1(testData))
        assertEquals(1715, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(8, this.part2(testData))
        assertEquals(374400, this.part2(realData))

    }

    private fun part1(data: String): Int {
        val trees = GridString.parse(data)
        val seen = mutableSetOf<Coord>()
        // columns
        for( x in trees.getXRange() ) {
            var tallest = Char(0)
            for( y in trees.getYRange()) {
                val c = Coord(x, y)
                val height = trees.get(c)
                if( height > tallest) {
                    seen.add(c)
                    tallest = height
                }
            }
            // Now the other way
            tallest = Char(0)
            for( y in trees.getYRange().reversed()) {
                val c = Coord(x, y)
                val height = trees.get(c)
                if( height > tallest) {
                    seen.add(c)
                    tallest = height
                }
            }
        }
        // Rows
        for( y in trees.getYRange() ) {
            var tallest = Char(0)
            for( x in trees.getXRange()) {
                val c = Coord(x, y)
                val height = trees.get(c)
                if( height > tallest) {
                    seen.add(c)
                    tallest = height
                }
            }
            // Now the other way
            tallest = Char(0)
            for( x in trees.getXRange().reversed()) {
                val c = Coord(x, y)
                val height = trees.get(c)
                if( height > tallest) {
                    seen.add(c)
                    tallest = height
                }
            }
        }
        return seen.size
    }

    private fun part2(data: String): Int {
        val trees = GridString.parse(data)
        var maxScore = 0
        // make a list of all the coords
        val coords = trees.getCoords().toList()
        return coords.parallelStream().map { score(trees, it) }.max(Int::compareTo).orElse(0)
    }

    private fun score(trees: GridString, c: Coord): Int {
        val height = trees.get(c)
        var score = 1
        for( d in Direction.values()) {
            var pos = c.move(d)
            var seenCount = 0
            while(trees.contains(pos)) {
                seenCount += 1
                if (trees.get(pos) >= height) {
                    break
                }
                pos = pos.move(d)
            }
            score *= seenCount
        }
        return score
    }
}