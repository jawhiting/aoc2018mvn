package com.drinkscabinet.aoc2020

import com.drinkscabinet.Coord3
import com.drinkscabinet.Coord4


private fun main() {
    // set up initial state
    val startGrid = GridString.parse(input)
    // extract 2d vals and turn to 3d
    var grid = startGrid.getAll('#').map{ Coord3(it.x, it.y, 0)}.toSet()
    println(grid)

    for( i in 1..6) {
        grid = step(grid)
        println("After $i cycle there are ${grid.size}")
    }

    // Part2
    var grid4 = startGrid.getAll('#').map{ Coord4(it.x, it.y, 0, 0)}.toSet()
    for( i in 1..6) {
        grid4 = step4(grid4)
        println("After $i cycle there are ${grid4.size}")
    }
}

fun step(active: Set<Coord3>) : Set<Coord3> {
    val result = mutableSetOf<Coord3>()
    // find bounds
    val xRange = active.minByOrNull { it.x }!!.x -1 .. active.maxByOrNull { it.x }!!.x +1
    val yRange = active.minByOrNull { it.y }!!.y -1 .. active.maxByOrNull { it.y }!!.y +1
    val zRange = active.minByOrNull { it.z }!!.z -1 .. active.maxByOrNull { it.z }!!.z +1
    for( x in xRange) {
        for( y in yRange) {
            for( z in zRange) {
                val c = Coord3(x,y,z)
                val activeNeighbours = c.neighbours26().count { active.contains(it) }
                if( active.contains(c)) {
                    // currently active
                    if( activeNeighbours == 2 || activeNeighbours == 3) {
                        // remain active
                        result.add(c)
                    }
                    else {
                        // go inactive
                    }
                }
                else {
                    // currently inactive
                    if( activeNeighbours == 3) {
                        // become active
                        result.add(c)
                    }
                    else {
                        // remain inactive
                    }
                }
            }
        }
    }
    return result
}

fun step4(active: Set<Coord4>) : Set<Coord4> {
    val result = mutableSetOf<Coord4>()
    // find bounds
    val xRange = active.minByOrNull { it.x }!!.x -1 .. active.maxByOrNull { it.x }!!.x +1
    val yRange = active.minByOrNull { it.y }!!.y -1 .. active.maxByOrNull { it.y }!!.y +1
    val zRange = active.minByOrNull { it.z }!!.z -1 .. active.maxByOrNull { it.z }!!.z +1
    val wRange = active.minByOrNull { it.w }!!.w -1 .. active.maxByOrNull { it.w }!!.w +1
    for( x in xRange) {
        for( y in yRange) {
            for( z in zRange) {
                for( w in wRange) {
                    val c = Coord4(x, y, z, w)
                    val activeNeighbours = c.neighbours80().count { active.contains(it) }
                    if (active.contains(c)) {
                        // currently active
                        if (activeNeighbours == 2 || activeNeighbours == 3) {
                            // remain active
                            result.add(c)
                        } else {
                            // go inactive
                        }
                    } else {
                        // currently inactive
                        if (activeNeighbours == 3) {
                            // become active
                            result.add(c)
                        } else {
                            // remain inactive
                        }
                    }
                }
            }
        }
    }
    return result
}


private val testInput = """
    .#.
    ..#
    ###
""".trimIndent()


private val input = """
    .###.#.#
    ####.#.#
    #.....#.
    ####....
    #...##.#
    ########
    ..#####.
    ######.#
""".trimIndent()