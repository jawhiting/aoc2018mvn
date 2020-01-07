package com.drinkscabinet.aoc2019

import GridString
import com.drinkscabinet.Coord
import java.lang.Math.pow
import kotlin.math.pow

private fun main() {
    println(Day24(GridString.parse(testInput2)).score())

    val day = Day24(GridString.parse(input))
    day.part1()
}

private class Day24(val start: GridString) {

    var current = start.copyOf()
    var generation = 0

    fun part1() {
        val seen = mutableSetOf<Long>()
        var score = score()
        while( seen.add(score) ) {
            next()
            score = score()
        }
        println("Score $score after $generation")
    }

    fun next() {
        val nextGrid = current.copyOf()
        nextGrid.applyAll( this::nextChar )
        current = nextGrid
        ++generation
    }

    private fun nextChar(pos: Coord, c: Char) : Char {
        val neighbours = current.neighbours4(pos)
        val bugCount = neighbours.count { it.second == '#' }

        return if( c == '#') {
            if( bugCount == 1) '#' else '.'
        } else {
            if( bugCount == 1 || bugCount == 2 ) '#' else '.'
        }
    }


    fun score() : Long {
        println(current.toString(true))
        return current.getAll('#').map { 5*it.y + it.x }.map{ 2.0.pow(it.toInt()).toLong()}.sum()
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