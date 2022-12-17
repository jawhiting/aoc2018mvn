package com.drinkscabinet.aoc2022kt

import Direction
import GridString
import UpDown
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import com.drinkscabinet.chunks
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day17KtTest {

    private val testData = """>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"""

    // IMPORTANT - have manually flipped the L tile because of gridstring direction
    private val tiles = """####

.#.
###
.#.

###
..#
..#

#
#
#
#

##
##"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testTiles() {
        val tiles = getTiles()
        for(t in tiles ) {
            println(t.toString(nums=true, invertV = false))
            println(t.toString(nums=true, invertV = true))
        }
    }

    @Test
    fun testMove() {
        val tiles = getTiles()
        for( t in tiles ) {
            val grid = GridString.parse("#")
            var offset = Coord(2,grid.getXMax()+3)
            println(t)
            for( i in 1..10 ) {
                offset = move(offset, t, grid, UpDown.R)
                println("$i $offset")
            }
            for( i in 1..10 ) {
                offset = move(offset, t, grid, UpDown.L)
                println("$i $offset")
            }
        }
    }
    @Test
    fun testMoveSeq() {
        val seq = moveSeq(testData).iterator()
        for( i in 1..100 ) {
            println(seq.next())
        }
    }

    @Test
    fun testTileSeq() {
        val seq = tileSeq().iterator()
        for( i in 1..12 ) {
            println(seq.next())
        }
    }

    @Test
    fun testPart1() {
        assertEquals(3068, part1(testData, 2022))
        assertEquals(3153, part1(realData, 2022))
    }

    @Test
    fun testPart2() {
//        assertEquals(70, day3part2(testData))
//        assertEquals(2510, day3part2(realData))
    }

    fun part1(data: String, tileCount: Int): Long {
        val shapeIter = tileSeq().iterator()
        val movesIter = moveSeq(data).iterator()
        val grid = GridString.parse("-------")
        // drop X shapes
        for (i in 1..tileCount) {
            addShape(grid, shapeIter.next(), movesIter)
        }
        return grid.getYMax()
    }

    private fun getTiles(): List<GridString> {
        return tiles.chunks().map { GridString.parse(it) }.toList()
    }

    private fun addShape(grid: GridString, shape: GridString, moves: Iterator<UpDown>) {
        // Initial point is 3 above highest
        var offset = Coord(2, grid.getYMax() + 3 + 1)
        var startingOffset = offset.copy()
        // temp draw
//        val grid2 = grid.copyOf()
//        grid2.add(offset, shape)
//        println(grid2.toString(invertV = true))
        do {
            // move left or right
            val nextMove = moves.next()
            offset = move(offset, shape, grid, nextMove)
            // Don't care if it couldn't move left/right
            // Now move down one
            startingOffset = offset
            offset = move(offset, shape, grid, UpDown.U)
        } while(offset != startingOffset)
        // Finished moving down
        grid.add(offset, shape)
//        println(grid.toString(invertV = true))
    }

    private fun moveSeq(moves: String) = sequence {
        var i = 0
        val len = moves.length
        while(true) {
            val c = moves[i]
            i = (i + 1) % len
            yield(if(c=='<') UpDown.L else UpDown.R)
        }
    }

    private fun tileSeq() = sequence {
        val tiles = getTiles().toTypedArray()
        var i = 0
        val len = tiles.size
        while(true) {
            yield(tiles[i])
            i = (i+1)%len
        }
    }

    private fun signature(grid: GridString): Long {
        // translate top rows into binary

    }

    /**
     * Move left or right, returning the appropriate offset (or the same one if it can't move)
     */
    private fun move(offset: Coord, shape: GridString, grid: GridString, dir: UpDown): Coord {
        if( dir == UpDown.L && offset.x == 0L ) {
            return offset
        }
        if( dir == UpDown.R && offset.x + shape.getXMax() >= 6) {
            return offset
        }
        // Now check if applying the move will cause an overlap
        if( grid.overlaps(offset.move(dir), shape) ) {
            return offset
        }
        return offset.move(dir)
    }
}