package com.drinkscabinet.aoc2023

import Direction
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

class Day21KtTest {

    private val testData = """...........
.....###.#.
.###.##..#.
..#.#...#..
....#.#....
.##..S####.
.##..#...#.
.......##..
.##.#.####.
.##..##.##.
..........."""

    private val realData = Utils.input(this)

    fun reachIn(pos: Coord, grid: GridString, depth: Int, xr: LongRange, yr: LongRange): Set<Coord> {
        val neighbors = Direction.entries.map{pos.move(it)}.filter { isEmpty(it, grid, xr, yr) }.toSet()
        if (depth == 1) {
            return neighbors
//            return grid.neighbours4(normalise(pos, xr, yr)).map { it.first }.filter{isEmpty(it, grid, xr, yr)}.toSet()
        }
//        val next = grid.neighbours4(normalise(pos, xr, yr)).map { it.first }.filter{isEmpty(it, grid, xr, yr)}.toSet()
        return neighbors.flatMap { reachIn(it, grid, depth - 1, xr, yr) }.toSet()
    }

    fun isEmpty(pos: Coord, grid: GridString, xr: LongRange, yr: LongRange): Boolean {
        return grid[normalise(pos, xr, yr)] == '.'
    }

    fun part1(input: String, depth: Int, debug: Boolean = false): Long {
        val grid = GridString.parse(input, ' ', true)
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        // find S in the grid
        val startPos = grid.getAll().filter { it.value == 'S' }.map { it.key }.first()
        grid[startPos] = '.'
        // Now go in increments of 2
        var toVisit = HashSet<Coord>(1000000)
        toVisit.add(startPos)
        var lastRing = HashSet<Coord>(1000000)
        lastRing.add(startPos)
        val visited = HashSet<Coord>(1000000)
        var visitedCount = 1L
        var steps = 0
        while (steps < depth) {
            steps += 2
            val nextToVisit = HashSet<Coord>(1000000)
            for (c in toVisit) {
                val inTwo = reachIn(c, grid, 2, xr, yr)
                for (p in inTwo) {
                    if (!lastRing.contains(p) && !toVisit.contains(p)) {
                        // it is a new one
                        if(visited.add(p)) {
                            nextToVisit.add(p)
                            visitedCount++
                        }
                    }
                }
            }
            lastRing = toVisit
            toVisit = nextToVisit
//            if(steps%100 == 0)
            if((steps-65) % 131 == 0)
                println("$steps, $visitedCount")
//            if(debug)
//                drawExtended(grid, visited, toVisit)
        }
        return visitedCount
//        return visited.size
//        println(reachIn(startPos, grid, 1))
//        println(reachIn(startPos, grid, 2))
//        return reachIn(startPos, grid, depth).size
        // {{196, 33614},{458, 182666},{720, 450822},{982, 838082}}
        //458, 182666
        //720, 450822
        //982, 838082
    }

    @Test
    fun testPart1() {
        assertEquals(16, part1(testData, 6))
        assertEquals(3666, part1(realData, 64))
    }

    private fun normalise(c: Coord, xr: LongRange, yr: LongRange): Coord {
        var x = c.x % (xr.last + 1)
        var y = c.y % (yr.last + 1)
        if (x < 0) x += xr.last + 1
        if (y < 0) y += yr.last + 1
        return Coord(x, y)
    }

    private fun drawExtended(grid: GridString, visited: Set<Coord>, nextToVisit: Set<Coord>) {
        val g2 = GridString(' ', true)
        val xr = visited.minBy { it.x }.x - 5..visited.maxBy { it.x }.x + 5
        val yr = visited.minBy { it.y }.y - 5..visited.maxBy { it.y }.y + 5
        val gxr = grid.getXRange()
        val gyr = grid.getYRange()
        for (y in yr) {
            for (x in xr) {
                val c = Coord(x, y)
                g2[c] = grid[normalise(c, gxr, gyr)]
            }
        }
        // Now plot visited on
        for (c in visited) {
            g2[c] = 'O'
        }
        for (c in nextToVisit) {
            g2[c] = '@'
        }
        println(g2.toString())
    }

    @Test
    fun testPart2() {
//        println(normalise(Coord(0, 0), xr = 0L..11, yr = 0L..11))
//        println(normalise(Coord(1, 1), xr = 0L..11, yr = 0L..11))
//        println(normalise(Coord(-1, -1), xr = 0L..11, yr = 0L..11))
//        val grid = GridString.parse(testData, ' ', true)
//        drawExtended(grid, setOf(Coord(-20, -20), Coord(20, 20)))
//        assertEquals(50, part1(testData, 10))
////        assertEquals(1594, part1(testData, 24, true))
//        assertEquals(1594, part1(testData, 50, ))
//        assertEquals(16733044, part1(testData, 5000))
//        assertEquals(16733044, part1(realData, 5000))
        // 196, 33614
        //458, 182666
        //720, 450822
        //982, 838082
        // -12.4549 + 1.52404 x + 0.867549 x^2 for x = 26501365 exact answer
        // 609299090087971 too high
        // 609304770478614 too high
        // 609292723079066 too low
        // 609298746763951 invalid
        // 609298746763952 correct
        println(solve(4650))
        println(solve2(4650))
        println(solve(4912))
        println(solve2(4912))
        println(solve(26501365))
        assertEquals(609298746763952, solve2(26501365))
    }

    fun solve2(target: Long) : Long {
        // got coefficients from wolfram
        val x2 = (BigDecimal.valueOf(14888) * BigDecimal.valueOf(target).pow(2)) / BigDecimal.valueOf(17161)
        val x = BigDecimal.valueOf(26154) * BigDecimal.valueOf(target) / BigDecimal.valueOf(17161)
        val c = BigDecimal.valueOf(213738) / BigDecimal.valueOf(17161)
        return (x2 + x - c).toLong()
    }

    fun solve(target: Long) : Long {
        // 4388	16710926	1935612	119104
        //4650	18765642	2054716	119104
        //4912	20939462	2173820	119104
        val d2 = 119104L
        val d = 1935612L
        val y = 16710926L
        val x = 4388L
        val delta = 131L*2L

        var x2 = x
        var y2 = y
        var diff = d

        while(x2 < target) {
            x2 += delta
            diff += d2
            y2 += diff
//            println("$x2, $y2, $diff")
        }
        return y2
    }
}