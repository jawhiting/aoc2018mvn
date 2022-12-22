package com.drinkscabinet.aoc2022kt

import Delta
import GridString
import UpDown
import com.drinkscabinet.Coord
import com.drinkscabinet.Utils
import com.drinkscabinet.Vector
import com.drinkscabinet.chunks
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class Day22KtTest {

    private val testData = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(6032, solve(testData, false))
    }

    @Test
    fun testPart1Real() {
        // 4495 too high
        //1536 too low
        assertEquals(106094, solve(realData, false))
    }

    fun solve(data: String, part2: Boolean): Long {
        val gridAndPath = parse(data)
        val grid = gridAndPath.first
        val path = gridAndPath.second
        println(grid.toString(nums = true))
        println(path)

        // Find starting point
        val start = Vector(findStart(grid), UpDown.R)
        println("Start is $start")
        val pos = tracePath(grid, start, path, part2)
        // Score the result
        val dirValue: Map<Delta, Int> = mapOf(UpDown.R to 0, UpDown.D to 1, UpDown.L to 2, UpDown.U to 3)
        return pos.pos.y * 1000 + pos.pos.x * 4 + dirValue[pos.delta]!!
    }

    fun tracePath(grid: GridString, start: Vector, path: String, part2: Boolean): Vector {
        var pos = start
        val xRange = grid.getXRange()
        val yRange = grid.getYRange()
        val seams = getSeams(grid)
        for (op in operations(path)) {
            when (op) {
                "R" -> pos = pos.rotate(1)
                "L" -> pos = pos.rotate(-1)
                else -> {
                    val distance = op.toInt()
                    pos = if (part2) {
                        move2(grid, seams, pos, distance)
                    } else {
                        move1(grid, xRange, yRange, pos, distance)
                    }
                }
            }
            println(pos)
        }
        grid[pos.pos] = 'e'
        println(grid.toString(nums = true))
        return pos
    }


    private fun move1(grid: GridString, xRange: LongRange, yRange: LongRange, pos: Vector, distance: Int): Vector {
        var current = pos
        for (i in 1..distance) {
            grid[current.pos] = icons[current.delta]!!
            var nextPos = current
            do {
                nextPos = moveWithWrap1(nextPos, xRange, yRange)
            } while (grid[nextPos.pos] == ' ')

            // Check if its valid
            val nextVal = grid[nextPos.pos]
            if (nextVal == '#') {
                // Stop early
                return current
            }
            current = nextPos
        }
        return current
    }

    private fun moveWithWrap1(pos: Vector, xRange: LongRange, yRange: LongRange): Vector {
        val next = pos.next()
        return Vector(next.pos.wrap(xRange, yRange), next.delta)
    }

    fun operations(path: String) = sequence<String> {
        var acc = ""
        for (c in path.toCharArray()) {
            if (c.isDigit()) {
                acc += c
            } else {
                yield(acc)
                acc = ""
                yield("" + c)
            }
        }
        // end of string
        if (acc.isNotEmpty()) {
            yield(acc)
        }
    }

    fun findStart(grid: GridString): Coord {
        val y = grid.getYMin()
        for (x in grid.getXRange()) {
            val c = Coord(x, y)
            if (grid[c] != ' ') {
                return c
            }
        }
        throw RuntimeException("Can't find start")
    }

    fun parse(data: String): Pair<GridString, String> {
        val chunks = data.chunks()
        assert(chunks.size == 2)
        val grid = GridString.parse(chunks[0], default = ' ')
        grid.normalise()
        grid.translate(Coord(1, 1))
        return grid to chunks[1].trim()
    }

    @Test
    fun testPart2() {
        assertEquals(5031, solve(testData, true))
    }

    @Test
    fun testPart2real() {
        // 2697722344572 too low
        // 3451534022349 too high
        assertEquals(3451534022348, solve(realData, true))
    }

    private val icons = mapOf(UpDown.U to '^', UpDown.R to '>', UpDown.D to 'v', UpDown.L to '<')

    private fun move2(grid: GridString, seams: List<Seam>, pos: Vector, distance: Int): Vector {
        var current = pos
        for (i in 1..distance) {
            grid[current.pos] = icons[current.delta]!!
            var nextPos = current
            // If we're on a seam, ask that for next pos
            val s = seams.filter { it.crossing(nextPos) }.toList()
            assert(s.size < 2)
            if (s.isNotEmpty()) {
                // cross the seam
                nextPos = s.first().next(nextPos)
            } else {
                nextPos = nextPos.next()
            }

            // Check if its valid
            val nextVal = grid[nextPos.pos]
            if (nextVal == '#') {
                // Stop early
                println(grid)
                return current
            }
            if (nextVal == ' ') {
                throw RuntimeException("Shouldn't hit a space: $pos $distance current: $current next: $nextPos")
            }
            current = nextPos
        }
        println(grid)
        return current
    }

    /*
xx123456789012
01        ...#
02        .#..
03        #...
04        ....
05...#.......#
06........#...
07..#....#....
08..........#.
09        ...#....
10        .....#..
11        .#......
12        ......#.


     */

    private fun getSeams(grid: GridString): List<Seam> {
        val seams = mutableListOf<Seam>()
        if (grid.getXMax() < 20) {
            // test data
            // 1 - 3
            seams.addAll(
                Seam.create(
                    Coord(9, 4).to(Coord(9, 1)),
                    Coord(8, 5).to(Coord(5, 5)), UpDown.L, UpDown.U
                )
            )
            // 1 - 2
            seams.addAll(
                Seam.create(
                    Coord(9, 1).to(Coord(12, 1)),
                    Coord(4, 5).to(Coord(1, 5)), UpDown.U, UpDown.U
                )
            )
            // 5 - 3
            seams.addAll(
                Seam.create(
                    Coord(9, 9).to(Coord(9, 12)),
                    Coord(8, 8).to(Coord(5, 8)), UpDown.L, UpDown.D
                )
            )
            // 5 - 2
            seams.addAll(
                Seam.create(
                    Coord(9, 12).to(Coord(12, 12)),
                    Coord(4, 8).to(Coord(1, 8)), UpDown.D, UpDown.D
                )
            )
            // 4 - 6
            seams.addAll(
                Seam.create(
                    Coord(12, 8).to(Coord(12, 5)),
                    Coord(13, 9).to(Coord(16, 9)), UpDown.R, UpDown.U
                )
            )
            // 6 - 2
            seams.addAll(
                Seam.create(
                    Coord(13, 12).to(Coord(16, 12)),
                    Coord(1, 8).to(Coord(1, 5)), UpDown.D, UpDown.L
                )
            )
            // 6 - 1
            seams.addAll(
                Seam.create(
                    Coord(16, 9).to(Coord(16, 12)),
                    Coord(12, 4).to(Coord(12, 1)), UpDown.D, UpDown.L
                )
            )
        } else {
            // real data
        }
        return seams
    }

    @Test
    fun testSeam() {
        val seams = Seam.create(
            Coord(9, 1).to(Coord(12, 1)),
            Coord(4, 5).to(Coord(1, 5)), UpDown.U, UpDown.U
        )
        assertEquals(2, seams.size)
        val seamA = seams[0]
        val seamB = seams[1]
        assertTrue(seamA.crossing(Vector(Coord(10, 1), UpDown.U)))
        assertFalse(seamA.crossing(Vector(Coord(10, 1), UpDown.R)))
        assertEquals(Vector(Coord(3, 5), UpDown.D), seamA.next(Vector(Coord(10, 1), UpDown.U)))
    }
}

data class Seam(val coordMap: Map<Coord, Coord>, val inDir: Delta, val outDir: Delta) {

    fun crossing(v: Vector): Boolean {
        return v.delta == inDir && v.pos in coordMap.keys
    }

    fun next(v: Vector): Vector {
        if (crossing(v)) {
            return Vector(coordMap[v.pos]!!, outDir)
        } else {
            throw IllegalArgumentException("Vector $v does not cross seam $this")
        }
    }

    companion object {
        fun create(sideA: Sequence<Coord>, sideB: Sequence<Coord>, dirAtoB: Delta, dirBtoA: Delta): List<Seam> {
            // Create a seam for each direction
            val aToB = Seam(sideA.zip(sideB).toMap(), dirAtoB, dirBtoA.rotate(2))
            val bToA = Seam(sideB.zip(sideA).toMap(), dirBtoA, dirAtoB.rotate(2))
            return listOf(aToB, bToA)
        }
    }
}