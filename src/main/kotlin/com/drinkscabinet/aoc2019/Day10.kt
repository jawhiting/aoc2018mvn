package com.drinkscabinet.aoc2019

import GridString
import com.drinkscabinet.Coord
import kotlin.math.*

private class Day10(val gs: GridString) {

    private val asteroids = gs.getAll('#')

    fun maxSeen() : Int {
        return asteroids.map{ countSee(it)}.maxOrNull()!!
    }

    fun maxCoord() : Coord {
        return asteroids.maxBy { countSee(it) }!!
    }

    fun countSee(a: Coord) : Int {
        val see =  asteroids.filter { canSee(a, it) }
//        println(see)
        return see.size
    }

    fun canSee(a: Coord, b: Coord) : Boolean {
        if( a.equals(b) ) return false
        // check all other asteroids
        val blocked = asteroids.filter { between(a, b, it) }.find{ distance(a, b, it) == 0.0 }
        return blocked == null
    }

}


private fun main() {
    println(distance(Coord(1L,1), Coord(2L, 2), Coord(3L, 3)))


    val gs = parse(testInput1)
    println(gs)
    println(gs.getAll('#'))

    val d = Day10(gs)
    println(d.canSee(Coord(3,4), Coord(3,4)))
    println(d.countSee(Coord(3,4)))
    println(d.maxSeen())
    println(Day10(parse(testInput210)).maxSeen())
    println(Day10(parse(input)).maxSeen())
    println(Day10(parse(input)).maxCoord())
    println(Day10(parse(input)).countSee(Coord(17, 23)))
}

private fun parse(s: String) : GridString {
    val gs = GridString()
    for((y, l) in s.lines().withIndex()) {
        for((x, c) in l.asSequence().withIndex()) {
            gs.add(Coord(x, y), c)
        }
    }
    return gs
}

private fun distance(a: Coord, b: Coord, p: Coord) : Double {
    val y1 = a.y
    val y2 = b.y
    val x1 = a.x
    val x2 = b.x
    val x0 = p.x
    val y0 = p.y

    val top = abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1)
    val bottom = sqrt(((y2-y1) * (y2-y1) + (x2-x1)*(x2-x1)).toDouble())
    val dist = top / bottom
    return dist
}

private fun between(a: Coord, b: Coord, p: Coord) : Boolean {
    if( p.equals(a) || p.equals(b)) return false
    return LongRange(min(a.x, b.x), max(a.x, b.x)).contains(p.x) &&
     LongRange(min(a.y, b.y), max(a.y, b.y)).contains(p.y)
}

private fun angle(a: Coord, b: Coord): Double {
    atan()
}


private val testInput1 = """
    .#..#
    .....
    #####
    ....#
    ...##
""".trimIndent()

private val testInput33 = """
    ......#.#.
    #..#.#....
    ..#######.
    .#.#.###..
    .#..#.....
    ..#....#.#
    #..#....#.
    .##.#..###
    ##...#..#.
    .#....####
""".trimIndent()

private val testInput210 = """
    .#..##.###...#######
    ##.############..##.
    .#.######.########.#
    .###.#######.####.#.
    #####.##.#.##.###.##
    ..#####..#.#########
    ####################
    #.####....###.#.#.##
    ##.#################
    #####.##.###..####..
    ..######..##.#######
    ####.##.####...##..#
    .#####..#.######.###
    ##...#.##########...
    #.##########.#######
    .####.#.###.###.#.##
    ....##.##.###..#####
    .#.#.###########.###
    #.#.#.#####.####.###
    ###.##.####.##.#..##
""".trimIndent()

private val input = """
    .###..#######..####..##...#
    ########.#.###...###.#....#
    ###..#...#######...#..####.
    .##.#.....#....##.#.#.....#
    ###.#######.###..##......#.
    #..###..###.##.#.#####....#
    #.##..###....#####...##.##.
    ####.##..#...#####.#..###.#
    #..#....####.####.###.#.###
    #..#..#....###...#####..#..
    ##...####.######....#.####.
    ####.##...###.####..##....#
    #.#..#.###.#.##.####..#...#
    ..##..##....#.#..##..#.#..#
    ##.##.#..######.#..#..####.
    #.....#####.##........#####
    ###.#.#######..#.#.##..#..#
    ###...#..#.#..##.##..#####.
    .##.#..#...#####.###.##.##.
    ...#.#.######.#####.#.####.
    #..##..###...###.#.#..#.#.#
    .#..#.#......#.###...###..#
    #.##.#.#..#.#......#..#..##
    .##.##.##.#...##.##.##.#..#
    #.###.#.#...##..#####.###.#
    #.####.#..#.#.##.######.#..
    .#.#####.##...#...#.##...#.
""".trimIndent()