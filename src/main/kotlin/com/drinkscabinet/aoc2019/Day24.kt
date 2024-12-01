package com.drinkscabinet.aoc2019

import Direction
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Coord3
import kotlin.math.pow

private fun main() {
    println(Day24(GridString.parse(testInput2)).score())
    val day = Day24(GridString.parse(input))
    day.part1()

    val g = InfiniteGrid(mutableMapOf(0L to GridString.parse(testInput1)))
    println(g.neighbours(Coord3(0, 0, 0)))
    println(g.neighbours(Coord3(2, 1, 0)))
    println(g.neighbours(Coord3(2, 3, 0)))
    println(g.neighbours(Coord3(2, 2, 0)))
}

private class InfiniteGrid(var levels: MutableMap<Long, GridString> = mutableMapOf()) {
    fun getValue(pos: Coord3): Char {
        return getGrid(pos.z)[Coord(pos.x, pos.y)]
    }

    fun setValue(pos: Coord3, value: Char) {
        getGrid(pos.z)[Coord(pos.x, pos.y)] = value
    }

    fun getGrid(level: Long): GridString {
        return levels.computeIfAbsent(level) { GridString() }
    }

    fun copyOf(): InfiniteGrid {
        return InfiniteGrid(levels.map { it.key to it.value.copyOf() }.toMap().toMutableMap())
    }

    fun neighbours(pos: Coord3): Iterable<Pair<Coord3, Char>> {
        return neighbourCoords(pos).map { it to getValue(it) }
    }

    fun neighbourCoords(pos: Coord3): Iterable<Coord3> {
        // Get the 4 neighbours on the current level
        val currentLevelPos = Coord(pos.x, pos.y)
        val neighbours = Direction.entries.map { currentLevelPos.move(it) }.toList()
        // Now check for out of bounds coords and replace them with another level
        return neighbours.map { convertNeighbour(pos, it) }.flatten().toSet()
    }

    fun convertNeighbour(originalPos: Coord3, neighbour: Coord): Iterable<Coord3> {
        var result = mutableListOf<Coord3>()
        // Going inwards
        if (neighbour.x == 2L && neighbour.y == 2L) {
            // Now figure out which side to show
            if (originalPos.y == 1L) {
                // top row
                for (x in 0L..4L) result.add(Coord3(x, 0L, originalPos.z + 1L))
            }
            if (originalPos.y == 3L) {
                // bottom row
                for (x in 0L..4L) result.add(Coord3(x, 4L, originalPos.z + 1L))
            }
            if (originalPos.x == 1L) {
                // top row
                for (y in 0L..4L) result.add(Coord3(0L, y, originalPos.z + 1L))
            }
            if (originalPos.x == 3L) {
                // top row
                for (y in 0L..4L) result.add(Coord3(4L, y, originalPos.z + 1L))
            }
            return result
        }
        // Going outwards
        if (neighbour.y == -1L) {
            // top row
            result.add(Coord3(2, 1, originalPos.z - 1))
        }
        if (neighbour.y == 5L) {
            // bottom row
            result.add(Coord3(2, 3, originalPos.z - 1))
        }
        if (neighbour.x == -1L) {
            // left col
            result.add(Coord3(1, 2, originalPos.z - 1))
        }
        if (neighbour.x == 5L) {
            // right col
            result.add(Coord3(3, 2, originalPos.z - 1))
        }
        // finally, if no mapping happened
        if (result.isEmpty()) {
            result.add(Coord3(neighbour.x, neighbour.y, originalPos.z))
        }
        return result
    }

    fun coordsToVisit(): Set<Coord3> {
        val result = mutableSetOf<Coord3>()
        for (e in levels.entries) {
            var coords = e.value.getAll('#')
            result.addAll(coords.map { Coord3(it.x, it.y, e.key) })
        }
        // now find all the neighbours
        val ns = result.map { neighbourCoords(it) }.flatten().toSet()
        result.addAll(ns)
        return result
    }

    private fun nextChar(pos: Coord3, c: Char): Char {
        val neighbours = neighbours(pos)
        val bugCount = neighbours.count { it.second == '#' }

        return if (c == '#') {
            if (bugCount == 1) '#' else '.'
        } else {
            if (bugCount == 1 || bugCount == 2) '#' else '.'
        }
    }

    fun next(): InfiniteGrid {
        val next = this.copyOf()
        coordsToVisit()
        // todo
        return next
    }
}

private class Part2(var grid: InfiniteGrid) {

    fun next(): InfiniteGrid {
        var nextGrid = grid.copyOf()
        // coords to visit are all the ones with hashes plus all their neighbours
        return nextGrid
    }


}

private class Day24(val start: GridString) {

    var current = start.copyOf()
    var generation = 0

    var levels = mutableMapOf(0 to start.copyOf())

    fun part1() {
        val seen = mutableSetOf<Long>()
        var score = score()
        while (seen.add(score)) {
            next()
            score = score()
        }
        println("Score $score after $generation")
    }

    fun next() {
        val nextGrid = current.copyOf()
        nextGrid.applyAll(this::nextChar)
        current = nextGrid
        ++generation
    }

    private fun nextChar(pos: Coord, c: Char): Char {
        val neighbours = current.neighbours4(pos)
        val bugCount = neighbours.count { it.second == '#' }

        return if (c == '#') {
            if (bugCount == 1) '#' else '.'
        } else {
            if (bugCount == 1 || bugCount == 2) '#' else '.'
        }
    }


    fun score(): Long {
        println(current.toString(true))
        return current.getAll('#').map { 5 * it.y + it.x }.map { 2.0.pow(it.toInt()).toLong() }.sum()
    }
}


private val testInput1 = """
    ....#
    #..#.
    #..##
    ..#..
    #....
""".trimIndent()

private val testInput2 = """
    .....
    .....
    .....
    #....
    .#...
""".trimIndent()

private val input = """
    #.#..
    .#.#.
    #...#
    .#..#
    ##.#.
""".trimIndent()