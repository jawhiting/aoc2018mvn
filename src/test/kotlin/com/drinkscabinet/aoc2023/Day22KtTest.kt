package com.drinkscabinet.aoc2023

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.min
import kotlin.math.max

class Day22KtTest {

    private val testData = """1,0,1~1,2,1
0,0,2~2,0,2
0,2,3~2,2,3
0,0,4~0,2,4
2,0,5~2,2,5
0,1,6~2,1,6
1,1,8~1,1,9"""

    private val test2 = """0,0,1~0,0,2
1,0,1~1,0,1
0,0,3~1,0,3
2,0,1~2,0,1
2,0,2~2,0,2
3,0,1~3,0,1
2,0,3~3,0,3"""

    private val realData = Utils.input(this)
    data class Brick(
        val id: Long,
        val grid: GridString,
        val z: IntRange,
        val above: MutableSet<Long> = mutableSetOf(),
        val below: MutableSet<Long> = mutableSetOf()
    ) {

        fun moveDown(): Brick {
            return Brick(id, grid, z.first - 1..z.last - 1, above, below)
        }

        override fun toString() :String {
            return "Brick(id=$id, grid=${grid.getAll().size}, z=$z, above=$above, below=$below)"
        }

        companion object {
            fun parse(input: String, id: Long): Brick {
                val (a, b) = input.split("~")
                val start = Utils.extractInts(a)
                val end = Utils.extractInts(b)
                val grid = GridString('.', true)
                val z = min(start[2], end[2])..max(start[2], end[2])
                for (x in min(start[0], end[0])..max(start[0], end[0])) {
                    for (y in min(start[1], end[1])..max(start[1], end[1])) {
                        grid.add(Coord(x, y), '#', id)
                    }
                }
                return Brick(id, grid, z)
            }
        }
    }

    data class Stack(val bricks: Map<Long, Brick>) {

        private val layout = createLayout(bricks)

        fun settle(): Stack {
            // Sort the bricks by z order
            val orderedBricks = bricks.values.sortedBy { it.z.first }
            val layout = mutableMapOf<Int, GridString>()
            val settledBricks = mutableListOf<Brick>()
            // Take each brick and move down
            for (brick in orderedBricks) {
                val settledBrick = settleLevel(brick, layout)
                addToLayout(settledBrick, layout)
                settledBricks.add(settledBrick)
            }
            return Stack(settledBricks.associateBy { it.id })
        }

        fun draw(z: Int, brick: Brick) : GridString {
            val base = layout.getOrDefault(z, GridString('.', true))
            val above = layout.getOrDefault(z+1, GridString('.', true))

            val result = base.copyOf()
            for((coord, c) in brick.grid.getAll()) {
                result[coord] = '~'
            }
            for((coord, c) in above.getAll()) {
                if(c == '#') {
                    if(result[coord] == '#') {
                        result[coord] = 'X'
                    }
                    else if (result[coord] == '~') {
                        result[coord] = '='
                    }
                    else {
                        result[coord] = 'O'
                    }
                }
            }
            return result
        }

        private fun settleLevel(brick: Brick, currentLayout: Map<Int, GridString>): Brick {
            // determine the lowest z this can settle to
            var prevBrick = brick
            var nextBrick = brick.moveDown()
            while (prevBrick.z.first > 1 && canFit(nextBrick, currentLayout)) {
                prevBrick = nextBrick
                nextBrick = nextBrick.moveDown()
            }
            return prevBrick
        }

        private fun canFit(brick: Brick, currentLayout: Map<Int, GridString>): Boolean {
            // get the current grid for the lowest z
            val targetGrid = currentLayout.getOrDefault(brick.z.first, GridString('.', true))
            return !targetGrid.overlaps(Coord(0, 0), brick.grid)
        }

        companion object {
            fun parse(input: String): Stack {
                return Stack(input.lines().mapIndexed { index: Int, s: String -> Brick.parse(s, index.toLong()) }
                    .associateBy { it.id })
            }

            fun addToLayout(brick: Brick, layout: MutableMap<Int, GridString>) {
                for (z in brick.z) {
                    layout.getOrPut(z) { GridString('.', true) } + brick.grid
                }
            }

            fun below(brick: Brick, layout: MutableMap<Int, GridString>): Set<Long> {
                // get lowest level of the brick
                val brickGrid = brick.grid
                val gridBelow = layout.getOrDefault(brick.z.first - 1, GridString('.', true))
                val belowBrickIds = mutableSetOf<Long>()
                for ((coord, _) in brickGrid.getAll()) {
                    // Look at the grid below
                    if (gridBelow[coord] == '#') {
                        belowBrickIds.add(gridBelow.getExtra(coord)!!)
                    }
                }
                return belowBrickIds
            }

            fun createLayout(brickMap: Map<Long, Brick>): Map<Int, GridString> {
                val layout = mutableMapOf<Int, GridString>()
                val orderedBricks = brickMap.values.sortedBy { it.z.first }
                for (brick in orderedBricks) {
                    addToLayout(brick, layout)
                    // get the bricks below
                    val belowBrickIds = below(brick, layout)
                    brick.below.addAll(belowBrickIds)
                    belowBrickIds.map { brickMap[it]!! }.forEach { it.above.add(brick.id) }
                }
                return layout
            }
        }
    }

    fun part1(input: String): Int {
        val s = Stack.parse(input)
//        println(s)
        s.settle().bricks.values.sortedBy { it.z.first }.forEach { println(it) }
        val settledStack = s.settle()
        // find all the bricks where all their parents have more than one support
        var canDestroy = 0
        for(b in settledStack.bricks.values) {
            // get all the bricks above
            var aboveHaveSupport = true
            for(a in b.above) {
                val aboveBrick = settledStack.bricks[a]!!
                if (aboveBrick.below.size < 2) {
                    aboveHaveSupport = false
                    break
                }
            }
            if(aboveHaveSupport) {
                println("Can destroy $b above: ${b.above.map { it to settledStack.bricks[it]!!.below }}")
                println(settledStack.draw(b.z.last, b))
                canDestroy++
            }
        }
        return canDestroy
    }

    @Test
    fun testPart1() {
//        assertEquals(5, part1(testData))
        assertEquals(4, part1(test2))
        // 451 too high
//        assertEquals(5, part1(realData))
    }
}