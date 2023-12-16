package com.drinkscabinet.aoc2023

import Direction
import GridString
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.exp

class Day14KtTest {

    private val testInput = """O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#...."""

    private val testInputResult = """OOOO.#.O..
OO..#....#
OO..O##..O
O..#.OO...
........#.
..#....#.#
..O..#.O.O
..O.......
#....###..
#....#...."""

    private val realData = Utils.input(this)
    fun tilt(grid: GridString, direction: Direction): GridString {
        val result = copyEmpty(grid)
        // sort the rocks based in direction
        val sortedRocks =
            when (direction) {
                Direction.N -> grid.getAll().filter { it.value == 'O' }.map { it.key }.sortedBy { it.y }
                Direction.S -> grid.getAll().filter { it.value == 'O' }.map { it.key }.sortedByDescending { it.y }
                Direction.E -> grid.getAll().filter { it.value == 'O' }.map { it.key }.sortedByDescending { it.x }
                Direction.W -> grid.getAll().filter { it.value == 'O' }.map { it.key }.sortedBy { it.x }
            }
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        sortedRocks.forEach {
            // Move in the target grid until we hit something (a rock, or the edge or another rock)
            var targetPos = it
            var nextPos = targetPos.move(direction)
            while (nextPos.x in xr && nextPos.y in yr && result[nextPos] == '.') {
                targetPos = nextPos
                nextPos = nextPos.move(direction)
            }
            // Place the rock
            result[targetPos] = 'O'
        }
        return result
    }

    fun copyEmpty(grid: GridString): GridString {
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        val result = GridString('.', true)
        grid.getAll().filter { it.value == '#' }.forEach { result[it.key] = '#' }
        return result
    }

    @Test
    fun testTilt() {
        val grid = GridString.parse(testInput)
        val expected = GridString.parse(testInputResult)
        assertEquals(expected.toString(), tilt(grid, Direction.N).toString())
        println(tilt(grid, Direction.S))
        println(tilt(grid, Direction.E))
        println("Original")
        println(grid)
        println(tilt(grid, Direction.W))
    }

    @Test
    fun testPart1() {
        assertEquals(136, part1(testInput))
        assertEquals(136, part1(realData))
    }

    fun score(grid: GridString): Long {
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        return grid.getAll().filter { it.value == 'O' }.map { it.key }.map { yr.last + 1 - it.y }.sum()
    }

    fun part1(data: String): Long {
        val grid = GridString.parse(data)
        val result = tilt(grid, Direction.N)
        return score(result)
    }

    fun cycle(grid: GridString): GridString {
        var result = tilt(grid, Direction.N)
        result = tilt(result, Direction.W)
        result = tilt(result, Direction.S)
        result = tilt(result, Direction.E)
        return result
    }

    @Test
    fun testPart2() {
        assertEquals(64, part2(testInput))
        assertEquals(64, part2(realData))
    }

    data class CycleInfo(val start: Long, val length: Long) {
        fun reduce(targetCycles: Long) : Long {
            var result = start
            // reduce the remaining cycles
            result += (targetCycles - result) % length
            return result
        }
    }

    fun cycleLength(grid: GridString): CycleInfo {
        var target = grid.toString()
        var results = mutableMapOf(target to 0)
        var i = 0
        var current = grid.copyOf()
        while(true) {
            current = cycle(current)
            val currentStr = current.toString()
            if(currentStr in results) {
                val cycleStart = results[currentStr]!!
                val cycleLength = i - cycleStart
                return CycleInfo(cycleStart.toLong(), cycleLength.toLong())
            }
            results[currentStr] = i++
            println("$i")
        }
    }
    fun part2(data: String): Long {
        var grid = GridString.parse(data)
        val cycleInfo = cycleLength(grid)
        println(cycleInfo)
        val reduce = cycleInfo.reduce(1000000000)
        println("Number $reduce")
        for(i in 1..reduce) {
            grid = cycle(grid)
        }
        println(grid)
        println(score(grid))
        return score(grid)
//        for(i in 1..3) {
//            grid = cycle(grid)
//            println("Cycle $i")
//            println(grid)
//        }

        return 0
    }

    private val cycle1 = """.....#....
....#...O#
...OO##...
.OO#......
.....OOO#.
.O#...O#.#
....O#....
......OOOO
#...O###..
#..OO#...."""
}