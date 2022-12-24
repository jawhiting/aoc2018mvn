package com.drinkscabinet.aoc2022kt

import GridString
import PathFinder
import UpDown
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import com.drinkscabinet.Vector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day24KtTest {

    private val testData = """#.#####
#.....#
#>....#
#.....#
#...v.#
#.....#
#####.#"""

    private val testComplex = """#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(18L, solve(testComplex, false))
    }

    @Test
    fun testPart1Real() {
        // 4495 too high
        //1536 too low
        assertEquals(228, solve(realData, false))
    }

    private fun solve(data: String, part2: Boolean): Long {
        return Solver(data).solve()
    }

    @Test
    fun testPart2() {
        assertEquals(20, solve(testData, true))
    }

    @Test
    fun testPart2real() {
        // 2697722344572 too low
        // 3451534022349 too high
        assertEquals(862, solve(realData, true))
    }

    class Solver(data: String) {
        val grid = GridString.parse(data).translate(Coord(-1, -1))
        val moveGrid = grid.copyOf()

        init {
            moveGrid.applyAll { _, c -> if (c == '#' || c == '.') c else '.' }
            moveGrid[Coord(0, -2)] = '#'
        }

        val lcm = Utils.lcm(grid.getXMax(), grid.getYMax())
        val blizzards = Blizzard.parse(grid).toList()
        val rowBlizzards: MutableMap<Long, List<Blizzard>> = mutableMapOf()

        init {
            for (row in grid.getYRange()) {
                rowBlizzards[row] = blizzards.filter { it.horizontal && it.position.pos.y == row }.toList()
            }
        }

        val colBlizzards: MutableMap<Long, List<Blizzard>> = mutableMapOf()

        init {
            for (col in grid.getXRange()) {
                colBlizzards[col] = blizzards.filter { it.vertical && it.position.pos.x == col }.toList()
            }
        }

        private val icons = mapOf(UpDown.U to '^', UpDown.R to '>', UpDown.D to 'v', UpDown.L to '<')


        fun simulate(turns: Long) {
            for (i in 0..turns) {
                val grid2 = moveGrid.copyOf()
                grid2.applyAll { _, c -> if (c == '#' || c == '.') c else '.' }
                for (b in blizzards) {
//                    println("Blizzard: $b")
                    val icon = icons[b.position.delta]
//                    println("Position at time $i is ${b.positionAt(i)} icon is $icon")
                    grid2[b.positionAt(i)] = icons[b.position.delta]!!
//                    println("Grid after is")
//                    println(grid2.toString(nums = true))
                }
                println("After Turn $i")
                println(grid2.toString(nums = true))
            }
        }

        fun solve(): Long {
            println("lcm is $lcm")
//            simulate(lcm*2+1)
//            println(State(9, Coord(5, 3)).nextStates())
            val startState = State(0, Coord(0, -1))
            val endState = State(-1, Coord(grid.getXMax() - 1, grid.getYMax()))
            val result = PathFinder.dijkstra2(startState, endState) { it.nextStates() }
            println(result.first.size)
//            for( r in result.first ) {
//                println(r)
//            }
            // find smallest result

            return result.first.filter { it.key.pos == endState.pos }.minBy { it.value }.value.toLong()
//            return result.first[endState]!!.toLong()
        }

        inner class State(val time: Long, val pos: Coord) {


            fun nextStates(): List<Pair<State, Int>> {
                val next = mutableListOf<Pair<State, Int>>()
                val nextTime = (time + 1) % lcm
                // Options are wait or move
                next.add(State(nextTime, pos) to 1)
                // move options
                for ((coord, char) in moveGrid.neighbours4(pos)) {
                    if (char == '.') {
                        next.add(State(nextTime, coord) to 1)
                    }
                }
                // Filter out ones where there will be a blizzard
//                return next
                return next.filter { !it.first.inBlizzard() }
            }

            private fun inBlizzard(): Boolean {
                // See if any of the blizzards hit this state
                for (b in rowBlizzards.getOrDefault(pos.y, listOf())) {
                    if (b.positionAt(time) == pos) {
                        return true
                    }
                }
                for (b in colBlizzards.getOrDefault(pos.x, listOf())) {
                    if (b.positionAt(time) == pos) {
                        return true
                    }
                }
                return false
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as State
                // if one of the times is -1, ignore
                if (time == -1L || other.time == -1L) {
                    return pos == other.pos
                }
                if (time != other.time) return false
                if (pos != other.pos) return false

                return true
            }

            override fun hashCode(): Int {
                var result = time.hashCode()
                result = 31 * result + pos.hashCode()
                return result
            }

            override fun toString(): String {
                return "State(time=$time, pos=$pos)"
            }
        }
    }

    data class Blizzard(val position: Vector, val cycleLength: Long) {
        fun positionAt(time: Long): Coord {
            if(horizontal) {
                var nextX = (position.pos.x + (position.delta.x * time)) % cycleLength
                if (nextX < 0) nextX += cycleLength
                return Coord(nextX, position.pos.y)
            }
            else {
                var nextY = (position.pos.y + (position.delta.y * time)) % cycleLength
                if (nextY < 0) nextY += cycleLength
                return Coord(position.pos.x, nextY)
            }
        }

        override fun toString(): String {
            return "Blizzard(position=$position, cycleLength=$cycleLength, horizontal=$horizontal, vertical=$vertical)"
        }

        val horizontal: Boolean
            get() = position.delta.y == 0

        val vertical: Boolean
            get() = position.delta.x == 0


        companion object {
            fun parse(grid: GridString) = sequence {
                // assume that top left of grid is -1, -1 (ie blizzards start at 0,0
                val xRange = grid.getXRange()
                // Last is the # for the wall, so we don't need to add 1
                val horizontalCycle = xRange.last
                val yRange = grid.getYRange()
                val verticalCycle = yRange.last
                for ((pos, char) in grid.getAll()) {
                    when (char) {
                        '^' -> yield(Blizzard(Vector(pos, UpDown.U), verticalCycle))
                        'v' -> yield(Blizzard(Vector(pos, UpDown.D), verticalCycle))
                        '>' -> yield(Blizzard(Vector(pos, UpDown.R), horizontalCycle))
                        '<' -> yield(Blizzard(Vector(pos, UpDown.L), horizontalCycle))
                    }
                }
            }
        }
    }

    @Test
    fun testBlizzardPosition() {
        val blizzard = Blizzard(Vector(Coord(1, 1), UpDown.R), 5)
        assertEquals(Coord(2, 1), blizzard.positionAt(1))
        assertEquals(Coord(3, 1), blizzard.positionAt(2))
        assertEquals(Coord(4, 1), blizzard.positionAt(3))
        assertEquals(Coord(0, 1), blizzard.positionAt(4))
        assertEquals(Coord(1, 1), blizzard.positionAt(5))
        assertEquals(Coord(2, 1), blizzard.positionAt(6))

        val blizzard2 = Blizzard(Vector(Coord(1, 1), UpDown.L), 5)
        assertEquals(Coord(0, 1), blizzard2.positionAt(1))
        assertEquals(Coord(4, 1), blizzard2.positionAt(2))
        assertEquals(Coord(3, 1), blizzard2.positionAt(3))
        assertEquals(Coord(2, 1), blizzard2.positionAt(4))
        assertEquals(Coord(1, 1), blizzard2.positionAt(5))
        assertEquals(Coord(0, 1), blizzard2.positionAt(6))
        assertEquals(Coord(4, 1), blizzard2.positionAt(7))
    }
}

