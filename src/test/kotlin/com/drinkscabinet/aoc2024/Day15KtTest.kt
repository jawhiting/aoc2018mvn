package com.drinkscabinet.aoc2024

import Direction
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day15KtTest {

    private val testData = """##########
#..O..O.O#
#......O.#
#.OO..O.O#
#..O@..O.#
#O#..O...#
#O..O..O.#
#.OO.O.OO#
#....O...#
##########

<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
>^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^"""

    private val testData2 = """########
#..O.O.#
##@.O..#
#...O..#
#.#.O..#
#...O..#
#......#
########

<^^>>>vv<v>>v<<"""

    private val testData3 = """#######
#...#.#
#.....#
#..OO@#
#..O..#
#.....#
#######

<vv<<^^<<^^"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(10092, this.part1(testData))
        assertEquals(2028, this.part1(testData2))
        assertEquals(1371036, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(618, this.part2(testData3))
        assertEquals(9021, this.part2(testData))
        assertEquals(1392847, this.part2(realData))
    }

    private fun part1(data: String): Long {
        val gd = parse(data)
        val grid = gd.first
        val dirs = gd.second
//        println(grid)

        var pos = grid.getAll('@').first()
        grid[pos] = '.'

        val warehouse = Warehouse(grid, pos)

        dirs.forEach {
            warehouse.moveRobot(it)
//            println("Move $it:\n\n")
//            warehouse.log()
        }

        return warehouse.score()
    }

    private data class Warehouse(val grid: GridString, var pos: Coord) {

        fun moveRobot(d: Direction) {
            val r = move(pos, d)
            if (r > 0) {
                pos += d
            }
        }

        fun score(): Long {
            return grid.getAll('O').sumOf { it.y * 100 + it.x }
        }

        fun log() {
            grid[pos] = '@'
            println(grid.toString())
            grid[pos] = '.'
        }

        /** Move the specified position in the specified direction
         * returns how many steps it moved
         */
        fun move(p: Coord, d: Direction): Int {
            // What's in the next square
            val next = grid[p + d]
            if (next == '#') {
                // wall, can't move
                return 0
            }
            if (next == 'O') {
                // Box, push it
                val pushResult = move(p + d, d)
                if (pushResult == 0) {
                    // couldn't move it
                    return 0
                }
                // Otherwise, the space is now empty
            }
            // Now next space should be empty
            grid[p + d] = grid[p]
            grid[p] = '.'
            return 1
        }
    }

    private fun part2(data: String): Long {
        val gd = parse(data)
        val grid = widen(gd.first)
        val dirs = gd.second
//        println(grid)

        var pos = grid.getAll('@').first()
        grid[pos] = '.'

        val warehouse = Warehouse2(grid, pos)

        dirs.forEach {
            warehouse.moveRobot(it)
//            println("Move $it:\n\n")
//            warehouse.log()
        }

        return warehouse.score()
    }

    private data class Warehouse2(val grid: GridString, var pos: Coord) {

        fun moveRobot(d: Direction) {
            if(d == Direction.E || d == Direction.W) {
                val r = moveEW(pos, d)
                if (r > 0) {
                    pos += d
                }
            }
            else {
                // Check if it can move
                if(canMoveNS(pos, d)) {
                    // Yes can move
                    moveNS(pos, d)
                    pos += d
                }
            }
        }

        fun score(): Long {
            return grid.getAll('[').sumOf { it.y * 100 + it.x }
        }

        fun log() {
            grid[pos] = '@'
            println(grid.toString())
            grid[pos] = '.'
        }

        /** Move the specified position in the specified direction
         * returns how many steps it moved
         */
        fun moveEW(p: Coord, d: Direction): Int {
            // What's in the next square
            val next = grid[p + d]
            if (next == '#') {
                // wall, can't move
                return 0
            }
            // E/W movement
            if (next == '[' || next == ']') {
                // Box, push it
                val pushResult = moveEW(p + d, d)
                if (pushResult == 0) {
                    // couldn't move it
                    return 0
                }
                // Otherwise, the space is now empty
            }
            // Now next space should be empty
            grid[p + d] = grid[p]
            grid[p] = '.'
            return 1
        }

        fun canMoveNS(p1: Coord, d: Direction): Boolean {
            // whats in the direction
            val next = grid[p1 + d]
            if (next == '#') return false
            if (next == '[') {
                // left side of a box, can only move if both can move
                return canMoveNS(p1 + d, d) && canMoveNS((p1 + d) + Direction.E, d)
            }
            if (next == ']') {
                // right side of a box, can only move if both can move
                return canMoveNS((p1 + d) + Direction.W, d) && canMoveNS(p1 + d, d)
            }
            // must be able to move to empty square
            return true
        }

        fun moveNS(p1: Coord, d: Direction) {
            // Only call after checking canMove
            var log = false
            if(d == Direction.N && p1 == pos) {
                log = true
            }
            val next = grid[p1 + d]
            if (next == '#') throw RuntimeException("Trying to move a wall")
            if (next == '[') {
                // left side of a box, move both sides
                moveNS(p1 + d, d)
                moveNS((p1 + d) + Direction.E, d)
            }
            if (next == ']') {
                // right side of a box, move both sides
                moveNS((p1 + d) + Direction.W, d)
                moveNS(p1 + d, d)
            }
            // Now next space should be empty
            grid[p1 + d] = grid[p1]
            grid[p1] = '.'
        }
    }

    private fun parse(data: String): Pair<GridString, List<Direction>> {
        val s = data.split("\n\n")
        val g = GridString.parse(s[0])
        val dirs = "^v<>".toList().toSet()
        val d = s[1].filter(dirs::contains).map(Direction::from).toList()
        return g to d
    }

    private fun widen(grid: GridString): GridString {
        val result = GridString()
        grid.getAll().forEach { (p, c) ->
            val p2 = Coord(p.x * 2, p.y)
            val p3 = Coord(p.x * 2 + 1, p.y)
            if (c == '#') {
                result[p2] = '#'
                result[p3] = '#'
            }
            if (c == 'O') {
                result[p2] = '['
                result[p3] = ']'
            }
            if (c == '@') {
                result[p2] = '@'
            }
        }
        return result
    }

}