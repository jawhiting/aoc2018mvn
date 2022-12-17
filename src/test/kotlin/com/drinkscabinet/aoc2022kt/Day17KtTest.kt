package com.drinkscabinet.aoc2022kt

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
    fun testMove() {
        val tiles = getTiles()
        for (t in tiles) {
            val grid = GridString.parse("#")
            var offset = Coord(2, grid.getXMax() + 3)
            println(t)
            for (i in 1..10) {
                offset = move(offset, t, grid, UpDown.R)
                println("$i $offset")
            }
            for (i in 1..10) {
                offset = move(offset, t, grid, UpDown.L)
                println("$i $offset")
            }
        }
    }

    @Test
    fun testMoveSeq() {
        val seq = moveSeq(testData).iterator()
        for (i in 1..100) {
            println(seq.next())
        }
    }

    @Test
    fun testTileSeq() {
        val seq = tileSeq().iterator()
        for (i in 1..12) {
            println(seq.next())
        }
    }

    @Test
    fun testPart1() {
        assertEquals(3068, part1(testData, 2022))
        assertEquals(3153, part1(realData, 2022))
    }

    @Test
    fun testPart2TestData() {
        assertEquals(1514285714288L, part2(testData))
    }

    @Test
    fun testPart2() {
        // 1528703703730 too low
        // 1553665689144 too low
        // 1553665689165 too high
        // 1553665689155 correct
        assertEquals(1553665689155L, part2(realData))
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

    fun part2(data: String): Long {
        // find repetition
        var blockCount = 0L
        val seen = mutableMapOf<String, Long>()
        val heights = mutableMapOf<Long, Long>()
        val shapeIter = tileSeq().iterator()
        val movesIter = moveSeq(data).iterator()
        val grid = GridString.parse("-------")
        val repeatFirst: Long
        val repeatSecond: Long
        // drop X shapes
        while (true) {
            addShape(grid, shapeIter.next(), movesIter)
            blockCount++
            heights[blockCount] = grid.getYMax()
            val seed = (moveI * 5 + tileI).toLong().toString()
            val sig = signature(grid, 20, seed)
            if (seen.contains(sig)) {
                println("repeated at $blockCount")
                println("Original at ${seen[sig]}")
                repeatFirst = seen[sig]!!
                repeatSecond = blockCount
                val y = grid.getYMax()
                for (yInc in 0L..30L) {
                    var line = ""
                    for (x in 0L..6L) {
                        line += grid[Coord(x, y - yInc)]
                    }
                    println(line)
                }
                break
            } else {
                seen[sig] = blockCount
            }
        }
        val target = 1000000000000L
        val repeatCount = (target - repeatFirst) / (repeatSecond - repeatFirst)
        val repeatHeight = heights[repeatSecond]!! - heights[repeatFirst]!!
        val remainder = (target - repeatFirst) % (repeatSecond - repeatFirst)

        return heights[repeatFirst]!! + repeatHeight * repeatCount + (heights[repeatFirst + remainder]!! - heights[repeatFirst]!!)
    }

    private fun getTiles(): List<GridString> {
        return tiles.chunks().map { GridString.parse(it) }.toList()
    }

    private fun addShape(grid: GridString, shape: GridString, moves: Iterator<UpDown>) {
        // Initial point is 3 above highest
        var offset = Coord(2, grid.getYMax() + 3 + 1)
        var startingOffset: Coord
        do {
            // move left or right
            val nextMove = moves.next()
            offset = move(offset, shape, grid, nextMove)
            // Don't care if it couldn't move left/right
            // Now move down one
            startingOffset = offset
            offset = move(offset, shape, grid, UpDown.U)
        } while (offset != startingOffset)
        // Finished moving down
        grid.add(offset, shape)
    }

    private var moveI = 0
    private var tileI = 0
    private fun moveSeq(moves: String) = sequence {

        val len = moves.length
        while (true) {
            val c = moves[moveI]
            moveI = (moveI + 1) % len
            yield(if (c == '<') UpDown.L else UpDown.R)
        }
    }

    private fun tileSeq() = sequence {
        val tiles = getTiles().toTypedArray()
        val len = tiles.size
        while (true) {
            yield(tiles[tileI])
            tileI = (tileI + 1) % len
        }
    }

    private fun signature(grid: GridString, rowCount: Long = 4, seed: String): String {
        // translate top rows into binary
        val top = grid.getYMax()
        var result = seed
        for (i in 0L until rowCount) {
            for (x in 0L..6L) {
                result += grid[Coord(x, top - i)]
            }
        }
        return result
    }

    /**
     * Move left or right, returning the appropriate offset (or the same one if it can't move)
     */
    private fun move(offset: Coord, shape: GridString, grid: GridString, dir: UpDown): Coord {
        if (dir == UpDown.L && offset.x == 0L) {
            return offset
        }
        if (dir == UpDown.R && offset.x + shape.getXMax() >= 6) {
            return offset
        }
        // Now check if applying the move will cause an overlap
        if (grid.overlaps(offset.move(dir), shape)) {
            return offset
        }
        return offset.move(dir)
    }
}