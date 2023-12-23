package com.drinkscabinet.aoc2023

import Direction
import GridString
import PathFinder
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day23KtTest {

    private val testInput = """#S#####################
#.......#########...###
#######.#########.#.###
###.....#.>.>.###.#.###
###v#####.#v#.###.#.###
###.>...#.#.#.....#...#
###v###.#.#.#########.#
###...#.#.#.......#...#
#####.#.#.#######.#.###
#.....#.#.#.......#...#
#.#####.#.#.#########v#
#.#...#...#...###...>.#
#.#.#v#######v###.###v#
#...#.>.#...>.>.#.###.#
#####v#.#.###v#.#.###.#
#.....#...#...#.#.#...#
#.#########.###.#.#.###
#...###...#...#...#.###
###.###.#.###v#####v###
#...#...#.#.>.>.#.>.###
#.###.###.#.###.#.#v###
#.....###...###...#...#
#####################E#"""

    private val realData = Utils.input(this)

    fun part1(data: String) : Int {
        val grid = GridString.parse(data, ' ', true)

        val start = grid.getAll('S').first()
        val end = grid.getAll('E').first()


        return longestPath(start, end, grid, mutableSetOf(start))
    }

    fun part2(data: String) : Int {
        val removedSlopes = data.replace('>', '.').replace('v', '.').replace('<', '.').replace('^', '.')
        val grid = GridString.parse(removedSlopes, ' ', true)

        val start = grid.getAll('S').first()
        val end = grid.getAll('E').first()


        return longestPath(start, end, grid, mutableSetOf(start))
    }

    private fun longestPath(current: Coord, target: Coord, grid: GridString, visited: MutableSet<Coord>) : Int {
        if(current == target) {
            println("Found path: ${visited.size-1}")
            return visited.size-1
        }

        fun nextMoves(from: Coord) : List<Pair<Coord, Int>> {
            val current = grid[from]
            val directions = when(current) {
                '>' -> listOf(Direction.E)
                'v' -> listOf(Direction.S)
                '<' -> listOf(Direction.W)
                '^' -> listOf(Direction.N)
                'S' -> listOf(Direction.S)
                else -> Direction.entries
            }
            // Get possible moves
            return grid.neighbours(from, directions).filter{it.first !in visited}.filter { it.second != '#' && it.second != ' '}.map { it.first to -1 }
        }

        val next = nextMoves(current)
        if(next.isEmpty()) return 0

        var longest = 0
        for(n in next) {
            visited.add(n.first)
            val pathLength = longestPath(n.first, target, grid, visited)
            longest = maxOf(longest, pathLength)
            visited.remove(n.first)
        }
        return longest
    }

    @Test
    fun testPart1() {
        assertEquals(94, part1(testInput))
        assertEquals(2030, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(154, part2(testInput))
        assertEquals(2030, part2(realData))
    }
}