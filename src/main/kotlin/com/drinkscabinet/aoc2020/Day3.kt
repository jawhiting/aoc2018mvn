package com.drinkscabinet.aoc2020

import GridString
import com.drinkscabinet.Coord

fun main() {
    val grid = GridString.parse(input)

    println("Part1=${count(grid, Coord(3, 1))}")

    val deltas = listOf(Coord(1,1), Coord(3,1), Coord(5,1), Coord(7,1), Coord(1,2))
    var mult = 1L
    for (delta in deltas) {
        val count = count(grid, delta)
        println("Delta=$delta Count=$count")
        mult *= count
    }
    println("Part2=$mult")
}

fun count(grid: GridString, delta: Coord) : Long {
    var currentPos = Coord(0,0)
    val maxX = grid.getXMax()
    val maxY = grid.getYMax()

    var treeCount = 0L
    while( currentPos.y <= maxY ) {
        val modPos = Coord(currentPos.x % (maxX + 1), currentPos.y)
        val cell = grid.get(modPos)
        if( cell == '#') treeCount++
        currentPos = currentPos.move(delta)
    }
    println("treeCount = $treeCount")
    return treeCount
}

private val testInput = """
    ..##.......
    #...#...#..
    .#....#..#.
    ..#.#...#.#
    .#...##..#.
    ..#.##.....
    .#.#.#....#
    .#........#
    #.##...#...
    #...##....#
    .#..#...#.#
""".trimIndent()

private val input = """
    ......#..##..#...#...#.###.....
    #..#............#..........#...
    ..........#....#..........#....
    ....#..#.#..........#..#.....#.
    #.......#...#......#........###
    #####........#.#....##..##..#..
    ......#.#..#..#..##.#..#.##....
    .#..#.#..............##....##..
    ..##......#....#........#...###
    ...#....#.#....#.#..#......#..#
    ..................#.....#.....#
    #.#...#...#....#............#.#
    .#...#.....#...##........#.....
    ...#....#........#..#....#..###
    #...##.....##.#.#...........#.#
    .###........#.#.#.........#....
    ...#.............###.....#.#..#
    .####.#..#....#.....#.........#
    .#.#........#.#.....#.....#....
    .#.......#................##.##
    ...#.#..#...###.....#....#..##.
    ...#....##..#............##...#
    #...#............######...#.##.
    .........#........#.#...#..##..
    .....###..#.#.....##.#.#......#
    ..#.#...#.#..#.#.##..#.....#.#.
    ..#......#.#....#...#..........
    ..#...#.....#.#...##.....#.....
    .##...........####........##...
    ....#............#.#...........
    .....####.........#.##....###..
    #..#..#.#..............#.#.....
    ...#.#........#.........#......
    ......#.#.#...#.....#....#.....
    ........#.#...#####..#..#......
    .....#.#....#....#...........##
    .#...#.........#.......##......
    .#.##..##......#...............
    ...#.....#.......#.#.#.........
    .........#..#...#...#.#.##....#
    .#......##....#..#.........#...
    ....#.....#........#.........##
    ......#...........##...........
    .....#..............###.#....#.
    ........#..#...#..#..#..#..#.#.
    .#.....#.##.#..#..#.#.....#....
    ...#....#...#.#.....##.#...#..#
    #..#......#..#.###...........#.
    .##...##.#........#.#......#.#.
    ...#.#..#.#.......#..###...##..
    #.......#.#....#..........#....
    .#.....#..#.#.#..#..#........#.
    .#...#......#.#...#.##.....#.##
    ...######..#.#....#.........##.
    #.#.......................#....
    ..#..##...#...#.#..##.......#..
    .##..#.......##......##.#..#...
    #.#....##.......#..#...........
    ..#...#............#..#........
    ........#.#.........#...#..#..#
    .#...###...............##...#..
    ...........#.....#....#....###.
    #..#....##..#................##
    ...#.#..#..##......#....##....#
    ...#.##...#....#..#....#.......
    #...##..##.#.........#...#....#
    .##........###.#..........#....
    ..#..#..#...#.##..#.#......#...
    .......##..#....###.##.....#..#
    #....#...#.#.....#..###....##..
    .#.......#.........#....#.#..#.
    .........#.......#.#.......#...
    ..........#...##..#...#....#.##
    ..#........#.......#...........
    #....#.....##......#....#.#...#
    ......#.....#....#.....#..#....
    .#....##...#...##..............
    ..#....#......#...#....#...#...
    #....###...##..#.#....##......#
    ..#.......#.........#..#......#
    ...#...#.##.......#....##..#...
    ..#.#...#.##..#..#..#...#.#...#
    .#.........###....#....#.....#.
    .#.##.#..##..#...........#....#
    ....##..#..##.#.......#....#..#
    ....#..#.........##..#......#.#
    ..........#.#.#....##.#......##
    .##...#....###...#..........#..
    #..#.....#..#.#.#.#..#......#.#
    ......#....#......##.#......#.#
    ...#.....#.......#....#.......#
    .#.#................#..........
    ......#..#..#...............##.
    ##......#...#.####....#.#.#....
    ...#..##............#....#.....
    ..#..#.#...#..................#
    .##.#.#..##.###.....#..#.......
    ..#...#.#...#......#..#........
    .###..........##...###..##..#..
    #.#...#........#.......##......
    ..##...#........#....##...##...
    .......#.##.....#.#.##..#..##..
    ........#............#....##...
    ...#.#.#..#.........#.#.......#
    ..#..##.##...#.##...#....#...#.
    .....##.#...##............##...
    .#...#.###....#.......#...#...#
    .......#######.#....#.....#.#..
    ......#.......#............##..
    .....#...........#......#.....#
    ........#....#.##.#............
    .#........#.......##.#.#....#..
    #.....#..####.#................
    .....#.......................##
    .#.....#..##.#..##........#.#.#
    #...##....#..##................
    ......##.###..........#.....#..
    .#........#...#..............##
    ..#..........###.........#.....
    ....#.....##....#..#..#.#.#....
    ....#.......#.##...#.####.#....
    #........#............#.##.....
    ..#......##.....#..#...#.......
    ..#......###...#.##......#..#..
    #..#..#............#..#.###....
    ...##.........#..##...#..#.#...
    ..#.###..#.##.#........#..#....
    ......#..###.#........#........
    .#....#.#..#.....#..#..#.......
    #.....##.##...#...###.#.#..#.#.
    .#....#..#.........#..#....###.
    ......##.####...#....#........#
    ##..#........#..#..##...#......
    #.........#.........#...#..#.#.
    ..........#...................#
    ###....#....#....#......###...#
    #....##........#..###.#..#.....
    .#......#.....#.#.........#..#.
    ...#.......##.....#.........###
    ..............#........#.....##
    ....#.#..#.....###.#....##.....
    .........#..##.#....#.#........
    ...#....#.......#.#.#..#.#....#
    ...........#...#..........#.#..
    #.................##........###
    ####..#.#..#...#.....###.......
    ..#.#......##.#.......#........
    .......##........#..#.....#..#.
    ...#..#......#..#.#.......###..
    #....#...##..#.#.#.#.........#.
    ....#....#....#.#..#..........#
    ...###........#.#.###......##..
    ................#.....#.#...##.
    ..#..#.###...........#...###.#.
    .........................#..#.#
    #...#..#..##.###.....##.##.#...
    ...#..................#.#....#.
    ......#..##.#.......#.......#..
    .##....#.#................#....
    .#...#..#.#.#....##....#.......
    .##......#.....#..........#....
    ..#...........#..##.........#..
    ....#.#...........#..........##
    ....#.#.#...........#.#........
    ......#.....#..#....##....##...
    ............##...##......#.#.##
    #.#.....#..#....#..#...#.#...#.
    .#...###..#..#.......#.......#.
    .....#..#.##.....#....#...#....
    ##.....#..##.......##..#.#.#..#
    ....#.#......##....#.....#..###
    .#...#.#......#.##...#..##.....
    .#...#...#......##..#..#...#.#.
    .#.........#....##...###...##..
    ###.....#......####.....#.#....
    .....#..##.##................#.
    .#.................#...#..##.#.
    ....#....#..#.......#.....#....
    .##....#..#..#.....###.#..#..#.
    #.#.......#.....##...#.....#...
    #.#........#.#.###...#....#....
    .#.....#.....##.#...#..#.......
    ..###.#............#...##.###..
    .....#.....#..#..##............
    .#.#..#.#..##..#....#...##.....
    .#...........#..#.......#...#.#
    #.#.#.#.....##....#............
    ...#.................#.#......#
    .....##.............#...#.#....
    .##......#.#....#..........#.#.
    .#.##.......##...#...#.....#.#.
    #...#.#........#......##....#.#
    #....##....#....#...#..#..#.#.#
    ......#..........#...#.....#..#
    #..#....#....#..##.#..#.#...#..
    ......#..#.#....#.....#.#..#..#
    ...#.#...###........#.#......##
    ..#............................
    ...#.#..##...##...#...#......##
    ...#.####......#.........#....#
    .#...#.#...##....#......#.#....
    .#.....##..##.#................
    .#...............#.............
    ......#.....#...#..##..##......
    ...#..##.......#.......#..#.#.#
    ......##.....#..#.....#...#.#.#
    ........##........#.#........##
    .#....#.....###..#.......#...#.
    #...#....#.........#.......#...
    ...##..#........#####.#........
    ###..#....#.#..#...#.####......
    ..#..........#.#.............#.
    #......#.#....#.#.#....#.##....
    .#.#.#.............#....#...#..
    ......#.....#.#...#..###.#..#..
    .....#..#............#...#...##
    ..#......###..#........#.#.....
    #..##......#.#.#.#...........#.
    #..#...##.##.....#....#..#.....
    ...##.#..........#.#....#...#..
    .#.#.#.#..#.#...#......#.......
    ....#......###.#...............
    .........#...#....#...#.#....#.
    ##.#.........#...##............
    ........#..........#.#...#.....
    ..#........#....#.......#......
    #..#...............#..#...##.#.
    #........#.....##.#..#....#...#
    ..##....#....#.#...........##..
    ....#.#.........#..#.....#..#..
    .......##....#.#.#....###.#....
    ......#....#.#...#..#.........#
    .....##..#....#.#......#.#.#...
    #.##..##.#.......#..#...##.#.##
    ........#.#..#...##.#.#..#.....
    #..#......#......#...#.#..#....
    .....#......#.#....##....##....
    ....#.##...##..#..........##.#.
    .#....#.......#.........#......
    .#.......#.#...#...............
    ....#.##.......#.##..#.##..#...
    #..#.......#.....#..#..........
    ..#.##.......#....#.#..##..#...
    .#.....#...##.#.#..#...#.......
    .......#.........#......#.#....
    #.##.....##.......#....#.......
    ##.#.#.........##..#.....#....#
    ....#.#.#.#....#..#..##.......#
    #...#...........#.#............
    ...#...#.#..#..##..............
    ......#.......#.........#..#.#.
    #.....##.#....#...#..#.........
    #...#..###.##..###...##.....#..
    #....#.#.#...#.#..........#....
    ................#.#....#.....##
    #.##..............####.....#.##
    ................#.....#........
    #...#..#......#.....#......#...
    .........##...........#...#...#
    #.#....#...##.....#.....#..#..#
    .....#...##..##.............#..
    ....###.#.......#.........#...#
    ..#.......#......#..#...#.#....
    #.#....#......#.##....#.##.#...
    .#.#...#.......#.#...#.##..#...
    ..........#......#.....#.......
    ........#...#.....#...##...#.#.
    .....##....#.##..#........#.##.
    ..........##.....#..#........#.
    .#....#..#.......#.##..........
    .#..#..#...#...#........#.##...
    .#...#.##.......#...#........#.
    .....#....#.............#..#...
    ...#....##...#...#.....##......
    #.#####.........##...#.....#...
    ......#.......#....#.....#..#..
    ..#..............#.#..#..#.....
    ....#.................#...#....
    ###.#..##.#....#...#.#......#.#
    ..##......#.#........#.#...##..
    .....#...#...#..#.#..#..##..#..
    .##...#......#...#...##.#...#..
    .......###.#...........##.##...
    .#.##..#.#.###.......#..##...#.
    ..#....#.......#..##......#....
    .#....#.#..#..#.#.#....#...#...
    ..........##....#....#.#.......
    .....#.......#.#..###.#.###....
    .#.#....#.##..#.#..#.....#.#.#.
    ....#.....#.#.#............#...
    .###....#...##......##..###..#.
    ...#.#..#.....#...#....##..#...
    .#.#....#..........#...##.....#
    #.....##...#........#.#..##..#.
    .......#....#.#..........#...#.
    .........#..#.#.###.........##.
    ..................#.#....#....#
    ....#....#.#..#.......###.##.##
    ....#...#.................#....
    ...#..#####.......#.#..##.##...
    ##.#....#...............#..#...
    ....#..........#...........#.#.
    ..##.#.##.#..#.#....#..........
    .....#....#....##.#....#....#.#
    .......#..##.....###...#....#.#
    .#.......#..#.#.#...........#..
    .#...........##.#.##....#.#....
    ....#.#....#.#.#......##.......
    .........##......#.#.....###...
    ........#.#...#.##.....#.##.##.
    ##.#..##.#.........#....#......
    .#.#.#....#..........#.#....#..
    ....###.........#.#.#..........
    #..#....##.....#...............
    #.##....#.#...#.....#......#.#.
    ............#.##........#......
    .....#.#.....##..##............
    .##..........#.......#......#..
    ...##..##......#.....#..#....##
    .##.##...#.................##..
    #....#.#........#..#....#..##.#
    ....##..##......#....###.#.#..#
    .....#....#..#..#...##...#...#.
""".trimIndent()