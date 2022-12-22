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

    private fun solve(data: String, part2: Boolean): Long {
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

    private fun tracePath(grid: GridString, start: Vector, path: String, part2: Boolean): Vector {
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

    private fun findStart(grid: GridString): Coord {
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
        assertEquals(162038, solve(realData, true))
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
            nextPos = if (s.isNotEmpty()) {
                // cross the seam
                s.first().next(nextPos)
            } else {
                nextPos.next()
            }

            // Check if its valid
            val nextVal = grid[nextPos.pos]
            if (nextVal == '#') {
                // Stop early
                return current
            }
            if (nextVal == ' ') {
                throw RuntimeException("Shouldn't hit a space: $pos $distance current: $current next: $nextPos")
            }
            current = nextPos
        }
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
            // 2 - 3
            seams.addAll(
                Seam.create(
                    Coord(101, 50).to(Coord(150, 50)),
                    Coord(100, 51).to(Coord(100, 100)), UpDown.D, UpDown.R
                )
            )
            // 2 - 5
            seams.addAll(
                Seam.create(
                    Coord(150, 50).to(Coord(150, 1)),
                    Coord(100, 101).to(Coord(100, 150)), UpDown.R, UpDown.R
                )
            )
            // 3 - 4
            seams.addAll(
                Seam.create(
                    Coord(51, 51).to(Coord(51, 100)),
                    Coord(1, 101).to(Coord(50, 101)), UpDown.L, UpDown.U
                )
            )
            // 1 - 4
            seams.addAll(
                Seam.create(
                    Coord(51, 1).to(Coord(51, 50)),
                    Coord(1, 150).to(Coord(1, 101)), UpDown.L, UpDown.L
                )
            )
            // 5 - 6
            seams.addAll(
                Seam.create(
                    Coord(51, 150).to(Coord(100, 150)),
                    Coord(50, 151).to(Coord(50, 200)), UpDown.D, UpDown.R
                )
            )
            // 1 - 6
            seams.addAll(
                Seam.create(
                    Coord(51, 1).to(Coord(100, 1)),
                    Coord(1, 151).to(Coord(1, 200)), UpDown.U, UpDown.L
                )
            )
            // 2 - 6
            seams.addAll(
                Seam.create(
                    Coord(101, 1).to(Coord(150, 1)),
                    Coord(1, 200).to(Coord(50, 200)), UpDown.U, UpDown.D
                )
            )
        }
        return seams
    }

    /*
1   55   11   1
    01   00   5
         01   0

     1111122222     1
     1111122222
     1111122222
     1111122222
     1111122222     50
     33333          51
     33333
     33333
     33333
     33333          100
4444455555          101
4444455555
4444455555
4444455555
4444455555          150
66666               151
66666
66666
66666
66666               200

1   55   11   1
    01   00   5
         01   0
    */
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