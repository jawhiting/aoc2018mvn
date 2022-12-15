package com.drinkscabinet.aoc2022kt

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

data class Computer(var cycle: Int = 0, var x: Int = 1, val xValues: MutableList<Int> = arrayListOf(1)) {

    private fun tick() {
        cycle += 1
        xValues.add(x)
    }

    fun execute(instruction: String): Unit {
        if (instruction == "noop") {
            tick()
        } else if (instruction.startsWith("addx")) {
            val amount = Utils.extractInts(instruction)[0]
            tick()
            tick()
            x += amount
        }
    }
}


class Day10KtTest {

    private val testData = """noop
addx 3
addx -5"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(0, this.part1(testData))
        assertEquals(13140, this.part1(testData2))
        assertEquals(17380, this.part1(realData))
    }

    @Test
    fun testPart2() {
        this.part2(testData2)
        this.part2(realData)
    }

    private fun execute(data: String, debug: Boolean = false): List<Int> {
        val computer = Computer()
        for (line in data.lines()) {
            computer.execute(line)
        }
        if (debug) {
            for (i in computer.xValues.indices) {
                println("Cycle: $i xValue: ${computer.xValues[i]}")
            }
        }
        return computer.xValues
    }

    private fun part1(data: String): Int {
        val xValues = execute(data)
        // Calculation
        var result = 0
        for (i in 20..xValues.indices.last step 40) {
            val calc = i * xValues[i]
            println("Calc for index $i xValue=${xValues[i]} result=$calc")
            result += i * xValues[i]
        }
        return result
    }

    private fun part2(data: String): Unit {
        val xValues = execute(data)
        draw(xValues)
    }

    private fun draw(xValues: List<Int>): Unit {
        val grid = GridString()
        for (c in 1..240) {
            val y = (c - 1) / 40
            val x = (c - 1) % 40
            val gridPos = Coord(x, y)
            val xVal = xValues[c]
            if (abs(xVal - x) <= 1) {
                grid.add(gridPos, '#')
            }
        }
        println(grid)
    }

    private val testData2 = """addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop"""

}