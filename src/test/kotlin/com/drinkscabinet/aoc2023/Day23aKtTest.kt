package com.drinkscabinet.aoc2023

import Direction
import GridString
import PathFinder
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day23aKtTest {

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

    fun part1(data: String): Int {
        val grid = GridString.parse(data, ' ', true)

        val start = grid.getAll('S').first()
        val end = grid.getAll('E').first()


        return longestPath(start, end, grid, mutableSetOf(start))
    }

    private val longestRoutes = mutableMapOf<Coord, MutableSet<Coord>>()

    private fun longestPath(current: Coord, target: Coord, grid: GridString, visited: MutableSet<Coord>): Int {
        if (current == target) {
            println("Found path: ${visited.size - 1}")
            return visited.size - 1
        }

        val next = nextMoves(current, grid, visited)
        if (next.isEmpty()) return 0

        var longest = 0
        for (n in next) {
            visited.add(n)
            val pathLength = longestPath(n, target, grid, visited)
            longest = maxOf(longest, pathLength)
            visited.remove(n)
        }
        return longest
    }

    private fun longestPath2(current: Coord, target: Coord, graph: Map<Coord, Map<Coord, Int>>, length: Int, visited: MutableSet<Coord>): Int {
        if (current == target) {
//            println("Found path: $length")
            return length
        }

        val next = graph[current]!!.filter { it.key !in visited }
        if (next.isEmpty()) return 0

        var longest = 0
        for (n in next) {
            visited.add(n.key)
            val nextLength = length + n.value
            val pathLength = longestPath2(n.key, target, graph, nextLength, visited)
            longest = maxOf(longest, pathLength)
            visited.remove(n.key)
        }
        return longest
    }

    fun nextMoves(from: Coord, grid: GridString, visited: Set<Coord>): List<Coord> {
        val current = grid[from]
        val directions = when (current) {
            '>' -> listOf(Direction.E)
            'v' -> listOf(Direction.S)
            '<' -> listOf(Direction.W)
            '^' -> listOf(Direction.N)
            'S' -> listOf(Direction.S)
            else -> Direction.entries
        }
        // Get possible moves
        return grid.neighbours(from, directions).filter { it.first !in visited }
            .filter { it.second != '#' && it.second != ' ' }.map { it.first }
    }

    @Test
    fun testPart1() {
        assertEquals(94, part1(testInput))
        assertEquals(2030, part1(realData))
    }

    fun countJunctions(grid: GridString): Set<Coord> {
        val exits = grid.getAll().filter { it.value == '.' }.map { it.key to nextMoves(it.key, grid, emptySet()).size }

        println(exits.count { it.second == 3 })
        println(exits.count { it.second == 4 })

        val display = grid.copyOf()
        for ((k, v) in exits) {
            if(v > 2)
                display[k] = "$v"[0]
        }
        println(display)
        return exits.filter { it.second > 2 }.map { it.first }.toSet()
    }

    fun traverseJunction(coord: Coord, grid: GridString, junctions: Set<Coord>) : Map<Coord, Int> {
        // Take every exit from this junction and follow until we reach another
        val result = mutableMapOf<Coord, Int>()
        for(exit in nextMoves(coord, grid, emptySet())) {
            // Keep going until we reach a junction
            val visited = mutableSetOf(coord, exit)
            var current = exit
            while (current !in junctions) {
                val next = nextMoves(current, grid, visited)
                if (next.isEmpty()) break
                assert(next.size == 1)
                current = next.first()
                visited.add(current)
            }
            if (current in junctions) {
                result[current] = visited.size-1
            }
        }
        return result
    }

    fun part2(data: String) : Int {
        // Correct answer 6390
        val removedSlopes = data.replace('>', '.').replace('v', '.').replace('<', '.').replace('^', '.')
        val grid = GridString.parse(removedSlopes, ' ', true)
        val start = grid.getAll('S').first()
        val end = grid.getAll('E').first()

        val junctions = countJunctions(grid).plus(start).plus(end)
        // add start and end
        val graph = mutableMapOf<Coord, Map<Coord, Int>>()
        for(j in junctions) {
            val result = traverseJunction(j, grid, junctions)
            println("Junction $j destinations:")
            result.entries.forEach { println("${it.key} -> ${it.value}") }
            graph[j] = result
        }

        // Now draw the graph
//        drawGraph(graph)

        return longestPath2(start, end, graph, 0, mutableSetOf())


//        assertEquals(154, part2(testInput))
//        assertEquals(2030, part2(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(154, part2(testInput))
        assertEquals(6390, part2(realData))
    }

    fun drawGraph(graph: Map<Coord, Map<Coord, Int>>) {
        for(e in graph.entries) {
            for(e2 in e.value.entries) {
                println("${coordName(e.key)} -- ${coordName(e2.key)} [label = ${e2.value}]")
            }
        }
    }

    fun coordName(c: Coord) : String {
        return "x${c.x}y${c.y}"
    }
}