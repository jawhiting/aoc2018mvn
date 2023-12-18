package com.drinkscabinet.aoc2023

import GridString
import PathFinder
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import com.drinkscabinet.Vector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17KtTest {

    private val testData = """2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533"""

    private val realData = Utils.input(this)

    data class State(val vector: Vector, val run: Int, val target: Coord) {

        fun possibleMoves() = sequence {
            for (d in Direction.entries) {
                if (d.opposite() == vector.delta) continue
                val c = vector.pos.move(d)
                if (d == vector.delta) {
                    yield(State(Vector(c, d), run + 1, target))
                } else {
                    yield(State(Vector(c, d), 1, target))
                }
            }
        }

        fun possibleMoves2() = sequence {
            if (run in 1..3) {
                yield(State(vector.next(), run + 1, target))
            } else {
                for (d in Direction.entries) {
                    if (d.opposite() == vector.delta) continue
                    val c = vector.pos.move(d)
                    if (d == vector.delta) {
                        yield(State(Vector(c, d), run + 1, target))
                    } else {
                        yield(State(Vector(c, d), 1, target))
                    }
                }
            }
        }


        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as State

            // special case for target
            if (vector.pos == other.vector.pos && vector.pos == target) return true

            if (vector != other.vector) return false
            if (run != other.run) return false
            if (target != other.target) return false

            return true
        }

        override fun hashCode(): Int {
            if (vector.pos == target) return target.hashCode()

            var result = vector.hashCode()
            result = 31 * result + run
            result = 31 * result + target.hashCode()
            return result
        }


    }

    fun part1(data: String): Int {
        val grid = GridString.parse(data, '9')
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        val targetCoord = Coord(xr.last, yr.last)
        val target = State(Vector(targetCoord, Direction.E), 0, targetCoord)
        val start = State(Vector(Coord(0, 0), Direction.E), 0, targetCoord)

        val path = PathFinder.dijkstra2(start, target) { s ->
            s.possibleMoves().filter { it.vector.pos.x in xr && it.vector.pos.y in yr && it.run < 4 }
                .map { it to grid[it.vector.pos].digitToInt() }.toList()
        }

        println(draw(grid, path.second, target))
        return path.first[target]!!
    }

    fun part2(data: String): Int {
        val grid = GridString.parse(data, '9')
        val xr = grid.getXRange()
        val yr = grid.getYRange()
        val targetCoord = Coord(xr.last, yr.last)
        val target = State(Vector(targetCoord, Direction.E), 0, targetCoord)
        val start = State(Vector(Coord(0, 0), Direction.E), 0, targetCoord)

        val path = PathFinder.dijkstra2(start, target) { s ->
            s.possibleMoves2().filter { it.vector.pos.x in xr && it.vector.pos.y in yr && it.run < 11 }
                .map { it to grid[it.vector.pos].digitToInt() }.toList()
        }

//        println(draw(grid, path.second, target))
        return path.first[target]!!
    }


    private fun draw(grid: GridString, second: Map<State, State>, target: State): GridString {
        val result = grid.copyOf()
        var current: State? = target
        do {
            result[current!!.vector.pos] = '*'
            println(current)
            current = second[current]
        } while (current != null)
        return result
    }

    @Test
    fun testPart1() {
        assertEquals(102, part1(testData))
        assertEquals(855, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(94, part2(testData))
        assertEquals(980, part2(realData))
    }


}