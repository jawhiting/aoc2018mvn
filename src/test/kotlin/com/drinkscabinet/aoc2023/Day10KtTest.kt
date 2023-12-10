package com.drinkscabinet.aoc2023

import Direction
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10KtTest {
    private val testData = """.....
.S-7.
.|.|.
.L-J.
....."""

    private val testData2 = """...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
..........."""

    private val realData = Utils.input(this)

    private data class Pipe(val symbol: Char, val directions: Set<Direction>)

    // | is a vertical pipe connecting north and south.
    //- is a horizontal pipe connecting east and west.
    //L is a 90-degree bend connecting north and east.
    //J is a 90-degree bend connecting north and west.
    //7 is a 90-degree bend connecting south and west.
    //F is a 90-degree bend connecting south and east.
    private val pipes = listOf(
        Pipe('|', setOf(Direction.N, Direction.S)),
        Pipe('-', setOf(Direction.E, Direction.W)),
        Pipe('L', setOf(Direction.N, Direction.E)),
        Pipe('J', setOf(Direction.N, Direction.W)),
        Pipe('7', setOf(Direction.S, Direction.W)),
        Pipe('F', setOf(Direction.S, Direction.E))
    )

    // Now index pipes by symbol and store in a member variable
    private val pipeMap = pipes.associateBy { it.symbol }

    private fun findStart(grid: GridString): Coord {
        return grid.getAll().entries.filter { it.value == 'S' }.map { it.key }.first()
    }

    private fun findFirstPipe(grid: GridString, start: Coord): Coord {
// Look at neighbours that are pipes, find which ones link to this
        val neighbours = grid.neighbours4(start).filter { it.second in pipeMap.keys }
        // Which of these pipes connects to us
        for (n in neighbours) {
            val nDirs = pipeMap[n.second]!!.directions
            // Do any of those applied to the neighbours position point to us
            if (grid.neighbours(n.first, nDirs).any { it.first == start }) {
                // one of them points to us, so it must be that one
                return n.first
            }
        }
        throw RuntimeException("No pipe found")
    }

    private fun firstPipeLetter(grid: GridString, start: Coord): Char {
        val neighbours = grid.neighbours4(start).filter { it.second in pipeMap.keys }
        // Which of these pipes connects to us, and with which direction
        val dirs = mutableSetOf<Direction>()
        for (n in neighbours) {
            val nDirs = pipeMap[n.second]!!.directions
            // Do any of those applied to the neighbours position point to us
            for (d in nDirs) {
                if (n.first.move(d) == start) {
                    dirs.add(d)
                }
            }
        }
        assert(dirs.size == 2)
        // Flip then find the letter
        val flipped = dirs.map { it.opposite() }.toSet()
        return pipeMap.entries.filter { it.value.directions == flipped }.map { it.key }.first()
    }

    private fun loop(start: Coord, grid: GridString): List<Coord> {
        var prev = start
        var current = findFirstPipe(grid, start)
        val list = mutableListOf(current)
        do {
            val currentChar = grid[current]
            val currentPipe = pipeMap[currentChar]!!
            val next = grid.neighbours(current, currentPipe.directions).first { it.first != prev }.first
            list.add(next)
            prev = current
            current = next
        } while (current != start)
        return list
    }

    @Test
    fun testPart1() {
        assertEquals(4, part1(testData))
        assertEquals(6979, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(1, part2(testData))
        assertEquals(4, part2(testData2))
        assertEquals(443, part2(realData))
    }

    fun part1(data: String): Long {
        val grid = GridString.parse(data)
        val start = findStart(grid)
        return loop(start, grid).size.toLong() / 2
    }

    fun part2(data: String): Long {
        val grid = GridString.parse(data)
        val start = findStart(grid)
        val startChar = firstPipeLetter(grid, start)
        grid[start] = startChar
        val pipes = loop(start, grid).toSet()
        val grid2 = grid.copyOf()
        grid2.applyAll { coord, c -> if (coord in pipes) c else '.' }
        println(grid2)
        val stretched = stretch(grid2)
        println(stretched)
        floodFill(stretched)
        println(stretched)
        return countInterior(stretched)
    }

    private fun stretch(grid: GridString): GridString {
        val result = GridString()
        for (y in grid.getYRange()) {
            for (x in grid.getXRange()) {
                val c = grid[Coord(x, y)]
                result[Coord(2 * x, 2 * y)] = c
                if (c in arrayOf('F', 'L', '-')) {
                    result[Coord(2 * x + 1, 2 * y)] = '-'
                }
                if (c in arrayOf('F', '7', '|')) {
                    result[Coord(2 * x, 2 * y + 1)] = '|'
                }
            }
        }
        return result
    }

    private fun countInterior(grid: GridString) : Long {
        var count = 0L
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        for(y in yr step 2) {
            for(x in xr step 2) {
                if(grid[Coord(x, y)] == '.') count++
            }
        }
        return count
    }

    private fun floodFill(grid: GridString): GridString {
        val toVisit = mutableSetOf<Coord>()
        // put in all the edge nodes
        for(x in arrayOf(grid.getXMin(), grid.getXMax())) {
            for(y in grid.getYRange()) {
                if(grid[Coord(x, y)] == '.') toVisit.add(Coord(x, y))
            }
        }
        for(y in arrayOf(grid.getYMin(), grid.getYMax())) {
            for(x in grid.getXRange()) {
                if(grid[Coord(x, y)] == '.') toVisit.add(Coord(x, y))
            }
        }
        val xr = grid.getXRange()
        val yr = grid.getYRange()
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
}