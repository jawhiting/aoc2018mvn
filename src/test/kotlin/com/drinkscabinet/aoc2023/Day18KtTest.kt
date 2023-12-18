package com.drinkscabinet.aoc2023

import Direction
import GridString
import UpDown
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.text.NumberFormat
import kotlin.math.abs

class Day18KtTest {

    private val testData = """R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)"""

    private val realData = Utils.input(this)

    data class Instruction(val direction: UpDown, val steps: Long) {
        companion object {
            fun parse(s: String): Instruction {
                val parts = s.split(" ")
                val direction = UpDown.valueOf(parts[0])
                val steps = parts[1].toLong()
                return Instruction(direction, steps)
            }

            @OptIn(ExperimentalStdlibApi::class)
            fun parse2(s: String): Instruction {
                val parts = s.split(" ")
                val inst = parts[2].substring(2..<parts[2].lastIndex)
                val steps = inst.substring(0..<inst.lastIndex).hexToLong()
                val dirVal = when(inst.last().digitToInt()) {
                    0 -> UpDown.R
                    1 -> UpDown.D
                    2 -> UpDown.L
                    3 -> UpDown.U // should never happen
                    else -> throw RuntimeException("Invalid direction")
                }
                return Instruction(dirVal, steps)
            }
        }
    }

    fun part1(data: String) : Long {
        val grid = GridString('.', true)

        var current = Coord(0,0)
        val points = mutableListOf<Coord>(current)

        grid[current] = '#'
        for(line in data.lines()) {
            val i = Instruction.parse(line)
            println(i)
            // apply the instruction
            for(s in 1..i.steps) {
                current += i.direction
                grid[current] = '#'
            }
            points.add(current)
        }
        // Print the grid
        println(grid)
        // fill the grid
        floodFill(grid)
        // count all # and . in the range
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        var count = 0L
        var perimiter = 0L
        var interior = 0L
        for(x in xr) {
            for(y in yr) {
                val c = grid[Coord(x, y)]
                if(c == '#') perimiter++
                if(c == '.') interior++
                if(c == '#' || c == '.') count++
            }
        }
        println("Perimiter: $perimiter")
        println("Interior: $interior")
        println("Count: $count")
        println("Points: ${points.toSet().size}")
        val shoe = shoelaceArea(points)
        println("Shoe " + shoe)
        println("Shoe + perim " + (shoe + perimiter))
        val picks = interior + (perimiter / 2.0) - 1
        println("Picks $picks")
        println("Adjusted " + (shoe + (perimiter/2) + 1))
        return count
    }

    private fun floodFill(grid: GridString): GridString {
        val toVisit = mutableSetOf<Coord>()
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        // put in all the edge nodes
        for(x in arrayOf(grid.getXMin(), grid.getXMax())) {
            for(y in yr) {
                if(grid[Coord(x, y)] == '.') toVisit.add(Coord(x, y))
            }
        }
        for(y in arrayOf(grid.getYMin(), grid.getYMax())) {
            for(x in xr) {
                if(grid[Coord(x, y)] == '.') toVisit.add(Coord(x, y))
            }
        }
        while(toVisit.isNotEmpty()) {
            val c = toVisit.first()
            toVisit.remove(c)
            if(grid[c] == '.') {
                grid[c] = 'O'
                toVisit.addAll(grid.neighbours4(c).filter { it.first.x in xr && it.first.y in yr && it.second == '.' }.map { it.first })
            }
//            println("ToVisit: ${toVisit.size}")
        }
        return grid
    }

    @Test
    fun testPart1() {
        assertEquals(62L, part1(testData))
        assertEquals(35991L, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(952408144115L, part2(testData))
        assertEquals(54058824661845L, part2(realData))
    }
    fun part2(data: String) : Long {

        var current = Coord(0,0)
        val points = mutableListOf<Coord>(current)
        var perimiter = 0L

        for(line in data.lines()) {
            val instruction = Instruction.parse2(line)
//            println(instruction)
            // apply the instruction
            current = current.move(instruction.direction, instruction.steps)
            perimiter += instruction.steps
            points.add(current)
        }
        val result = (shoelaceArea(points.toList()) + perimiter/2 + 1).toLong()
        println(result)
        return result
    }

    fun shoelaceArea(v: List<Coord>): Double {
        val n = v.size
        var a: Double = 0.0
        for (i in 0 until n - 1) {
            a += v[i].x * v[i + 1].y - v[i + 1].x * v[i].y
        }
        return abs(a + v[n - 1].x * v[0].y - v[0].x * v[n -1].y) / 2.0
    }

}