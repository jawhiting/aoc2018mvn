package com.drinkscabinet.aoc2023

import GridString
import com.drinkscabinet.Coord
import com.drinkscabinet.Grid
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day3KtTest {

    private val testData = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598.."""


    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(4361, this.calculate(testData, false))
        // 334335 too low
        // 334781 too low
        assertEquals(529618, this.calculate(realData, false))
    }

    @Test
    fun testPart2() {
        assertEquals(467835, this.calculate(testData, true))
        assertEquals(77509019, this.calculate(realData, true))
    }

    data class Num(val id: Int, val num: Int, val coords: Set<Coord>, val isPart: Boolean)

    private fun calculate(data: String, part2: Boolean): Int {
        var nextId = 0
        val grid = GridString.parse(data)
        val parts = mutableListOf<Int>()
        val nonParts = mutableListOf<Int>()
        val nums = mutableMapOf<Coord, Num>()
        for(y in grid.getYRange()) {
            var num = 0
            var isPart = false
            val coords = mutableSetOf<Coord>()
            for (x in grid.getXRange()) {
                // check if coord is a digit
                val coord = Coord(x, y)
                if(grid[coord] in '0'..'9') {
                    val n = grid[coord].digitToInt()
                    num = num * 10 + n
                    coords.add(coord)
                    if(!isPart) {
                        // check neighbours for symbols
                        val neighbours = grid.neighbours8(coord)
                        if (!neighbours.all { it.second in '0'..'9' || it.second == '.' }) {
                            isPart = true
                        }
                    }
                }
                else {
                    // non numeric, so reset
                    if(num > 0) {
                        if(isPart) {
                            parts.add(num)
                        }
                        else {
                            nonParts.add(num)
                        }
                        // make a copy of coords set

                        val n = Num(nextId++, num, coords.toSet(), isPart)
                        for(c in coords) {
                            nums[c] = n
                        }
                        coords.clear()
                    }
                    num = 0
                    isPart = false
                }
            }
            // end of row, reset
            if(num > 0) {
                if(isPart) {
                    parts.add(num)
                }
                else {
                    nonParts.add(num)
                }
                val n = Num(nextId++, num, coords.toSet(), isPart)
                for(c in coords) {
                    nums[c] = n
                }
                coords.clear()
            }
        }

        return if(part2)
            gears(grid, nums)
        else
            parts.sum()
    }

    private fun gears(grid: GridString, nums: Map<Coord, Num>): Int {
        val gears = grid.getAll('*')
        var gearSum = 0
        for(c in gears) {
            val neighbors = grid.neighbours8(c)
            val neighborsNums = neighbors.map{it.first}.filter{ nums.containsKey(it)}.map { nums[it]!! }.toSet()
//            println("Gear at $c has $neighborsNums")
            if(neighborsNums.size == 2) {
                // multiply all of the num values together
                val ratio = neighborsNums.map{it.num}.reduce{a, b -> a * b}
//                println("Gear ratio$ratio")
                gearSum += ratio
            }
        }
        return gearSum
    }
}