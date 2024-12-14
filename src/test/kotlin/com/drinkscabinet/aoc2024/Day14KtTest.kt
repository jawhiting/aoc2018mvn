package com.drinkscabinet.aoc2024

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.floor
import kotlin.math.max
import kotlin.test.assertNull


class Day14KtTest {

    private val testData = """p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(12, this.part1(testData, 11, 7))
        // 232832880 too high
        assertEquals(229980828, this.part1(realData, 101, 103))
    }

    @Test
    fun testPart2() {
        // 5896 probably 5897
        // says 5897 too low
        assertEquals(7132, this.part2(realData, 101, 103))
    }

    private data class Robot(val pos: Coord, val velocity: Coord) {
        fun move(steps: Int, maxX: Int, maxY: Int) : Robot {
            var nextX = (pos.x + velocity.x*steps) % maxX
            if(nextX < 0) nextX = maxX + nextX
            var nextY = (pos.y + velocity.y*steps) % maxY
            if(nextY < 0) nextY = maxY + nextY
            return Robot(Coord(nextX, nextY), velocity)
        }
    }

    private fun part1(data: String, maxX: Int, maxY: Int): Long {
        var robots = parse(data).toList()
        // Now step them
        robots = robots.map { it.move(100, maxX, maxY) }.toList()
        return score(robots, maxX, maxY)
    }

    private fun part2(data: String, maxX: Int, maxY: Int): Long {
        var robots = parse(data).toList()
        // Now step them
        var longest = 0
        var longestSteps = 0
        var stepsTaken = 0
        for(i in 0..maxX*maxY) {
            robots = robots.map { it.move(1, maxX, maxY) }.toList()
            stepsTaken++
            var line = longestLine(robots)
            if(line > longest) {
                longest = line
                longestSteps = stepsTaken
                println("Found new longest $line at $stepsTaken")
                // Now print
                println(GridString().setAll(robots.map { it.pos }, 'X'))
            }
        }
        return longestSteps.toLong()
    }

    private fun longestLine(robots: List<Robot>) : Int {
        val sorted = robots.sortedBy { it.pos }
        var curX = -1L
        var curY = -1L
        var maxRun = 0
        var currentRun = 0
        for(r in sorted) {
            // If we're on a new row, reset
            if(r.pos.y != curY) {
                curX = -1
                curY = r.pos.y
                currentRun = 0
            }
            else {
                // now if we're in a run
                if(r.pos.x == curX +1) {
                    currentRun++
                    maxRun = max(maxRun, currentRun)
                }
                else {
                    // not in a run
                    currentRun = 0
                }
                // move x marker
                curX = r.pos.x
            }
        }
        return maxRun
    }

    private fun score(robots: List<Robot>, maxX: Int, maxY: Int) : Long {
        val midX = (maxX-1)/2
        val midY = (maxY-1)/2
        var counts = arrayListOf(0,0,0,0)

        for(r in robots) {
            if(r.pos.x < midX) {
                if(r.pos.y < midY) {
                    counts[0]++
                }
                else if(r.pos.y > midY){
                    counts[1]++
                }
            }
            else if(r.pos.x > midX){
                if(r.pos.y < midY) {
                    counts[2]++
                }
                else if(r.pos.y > midY){
                    counts[3]++
                }
            }
        }
        return counts[0]*counts[1]*counts[2]*counts[3].toLong()
    }

    private fun parse(data: String) = sequence {
        data.lines().forEach {
            val nums = Utils.extractInts(it)
            yield(Robot(Coord(nums[0], nums[1]), Coord(nums[2], nums[3])))
        }
    }

}