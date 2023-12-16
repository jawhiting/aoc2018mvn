package com.drinkscabinet.aoc2023

import Direction
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import com.drinkscabinet.Vector
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16KtTest {

    private val testData = """.|...\....
|.-.\.....
.....|-...
........|.
..........
.........\
..../.\\..
.-.-/..|..
.|....-|.\
..//.|...."""

    private val realData = Utils.input(this)

    data class Box(val grid: GridString) {
        private val xr = grid.getXRange()
        private val yr = grid.getYRange()
        private val activeBeams = mutableListOf<Beam>()
        private val completedBeams = mutableListOf<Beam>()

        private val visited = mutableSetOf<Vector>()

        fun resolve(initial: Beam): Set<Coord> {
            addBeam(initial)
            while (activeBeams.isNotEmpty()) {
                val beam = activeBeams.removeAt(0)
                if (beam.step()) {
                    activeBeams.add(beam)
                } else {
                    completedBeams.add(beam)
                }
            }
            return visited.map { it.pos }.toSet()
        }

        private fun addBeam(beam: Beam) {
            activeBeams.add(beam)
            visited.add(beam.vector)
        }

        inner class Beam(
            private var position: Coord,
            private var direction: Direction,
        ) {

            // Add a property to return the beam vector dynamically
            val vector: Vector
                get() = Vector(position, direction)

            fun step(): Boolean {
                // look at current position, and move accordingly
                val current = grid[position]
                // If we're outside the grid, no more moving
                if (position.x !in xr || position.y !in yr) {
                    return false
                }
                // Potentially change direction
                when (current) {
                    '/' -> direction = when (direction) {
                        Direction.N -> Direction.E
                        Direction.E -> Direction.N
                        Direction.S -> Direction.W
                        Direction.W -> Direction.S
                    }

                    '\\' -> direction = when (direction) {
                        Direction.N -> Direction.W
                        Direction.E -> Direction.S
                        Direction.S -> Direction.E
                        Direction.W -> Direction.N
                    }

                    '|' -> when (direction) {
                        Direction.W, Direction.E -> {
                            // Spilt the beam - this one can go north, and we create a new south
                            direction = Direction.N
                            addBeam(Beam(position, Direction.S))
                        }

                        else -> {}
                    }

                    '-' -> when (direction) {
                        Direction.N, Direction.S -> {
                            // Spilt the beam - this one can go east, and we create a new west
                            direction = Direction.E
                            addBeam(Beam(position, Direction.W))
                        }

                        else -> {}
                    }
                }
                position = position.move(direction) // move in the new direction
                // If the new position has already been visited, this beam is finished
                if (vector in visited) {
                    return false
                }
                // if the new position is outside the grid, this beam is finished
                if (position.x !in xr || position.y !in yr) {
                    return false
                }
                visited.add(vector)
                return true
            }
        }
    }


    private fun entryPoints(grid: GridString) = sequence {
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        // top and bottom rows
        for (y in arrayOf(yr.first, yr.last)) {
            val d = if (y == yr.first) Direction.S else Direction.N
            for (x in xr) {
                yield(Vector(Coord(x, y), d))
            }
        }
        // left and right columns
        for (x in arrayOf(xr.first, xr.last)) {
            val d = if (x == xr.first) Direction.E else Direction.W
            for (y in yr) {
                yield(Vector(Coord(x, y), d))
            }
        }
    }


    fun part1(data: String): Int {
        val grid = GridString.parse(data)
        val box = Box(grid)
        return box.resolve(box.Beam(Coord(0, 0), Direction.E)).size
    }

    fun part2(data: String): Int {
        val grid = GridString.parse(data)
        val entries = entryPoints(grid).toList()
        return entries.parallelStream().mapToInt { run(grid, it) }.max().asInt
    }

    private fun run(grid: GridString, entry: Vector): Int {
        val box = Box(grid)
        return box.resolve(box.Beam(entry.pos, entry.delta as Direction)).size
    }

    @Test
    fun testPart1() {
        assertEquals(46, part1(testData))
        assertEquals(8323, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(51, part2(testData))
        assertEquals(8491, part2(realData))

    }
}