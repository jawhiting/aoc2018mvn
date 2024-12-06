package com.drinkscabinet.aoc2024

import Direction
import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day6KtTest {

    private val testData = """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#..."""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(41, this.part1(testData, false))
        assertEquals(4988, this.part1(realData, false))
    }

    @Test
    fun testPart2() {
        assertEquals(6, this.part1(testData, true))
        assertEquals(1697, this.part1(realData, true))
    }

    private fun part1(data: String, part2: Boolean): Int {
        val grid = GridString.parse(data)
        val grid2 = grid.copyOf()
        val startPos = grid.getAll('^').first()
        val startDir = Direction.N

        var pos = startPos
        var dir = startDir

        while(grid.contains(pos)) {
            // mark where we've been
            grid2[pos] = 'X'
            // Check next step
            var nextInDir = pos + dir
            if(grid[nextInDir] == '#') {
                // crate encountered, we need to turn
                dir = dir.rotate(1)
                nextInDir = pos + dir
            }
            pos = nextInDir
        }
        // Now count all the X
        if(!part2) return grid2.getAll('X').count()

        // Now do part 2 - use the X positions as options
        var options = grid2.getAll('X').toMutableSet()
        options.remove(startPos)
        var count = 0
        var curr = 0
        var total = options.size
        for(c in options) {
            val gridObstacle = grid.copyOf()
            gridObstacle[c] = '#'
            if(!exits(startPos, startDir, gridObstacle)) {
                count++
                println("Loops with $c")
            }
//            println("Tested ${curr++} out of $total")
        }
        return count
    }

    private fun exits(start: Coord, startDir: Direction, grid: GridString) : Boolean {
        // navigate the path, track vector at each coord
        val visited = mutableMapOf(start to mutableSetOf(startDir))
        val grid2 = grid.copyOf()
        var pos = start
        var dir = startDir

        while(grid.contains(pos)) {
            // mark where we've been
            grid2[pos] = 'X'
            // add to path
            visited.getOrPut(pos){mutableSetOf<Direction>()}.add(dir)
            // Check next step
            var nextInDir = pos + dir
            while(grid[nextInDir] == '#') {
                // crate encountered, we need to turn
                dir = dir.rotate(1)
                nextInDir = pos + dir
            }
            pos = nextInDir
            // if the new position is one we already visited, exit
            if(visited.getOrDefault(pos, emptySet<Direction>()).contains(dir)) return false
        }
        // exited the grid
        return true
    }

//    @Test
//    fun testExits() {
//        var
//    }
}