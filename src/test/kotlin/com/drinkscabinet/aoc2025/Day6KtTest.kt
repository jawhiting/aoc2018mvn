package com.drinkscabinet.aoc2025

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs


class Day6KtTest {

    private val testData = """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  """



    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(4277556, this.part1(testData))
        assertEquals(5877594983578, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(3263827, this.part2(testData))
        assertEquals(11159825706149, this.part2(realData))
    }

    private fun part1(data: String): Long {
        var result = 0L
        val nums = mutableListOf<List<Long>>()
        val operators = mutableListOf<String>()
        for(line in data.lines()) {
            val n = Utils.extractLongs(line).toList()
            if(n.isNotEmpty())
                nums.add(n)
            else
                line.split(' ').filter { it.isNotEmpty() }.forEach { operators.add(it) }
        }
        for(i in operators.indices) {
            val op = operators[i]
            val isAdd = op == "+"
            var colResult = if(isAdd) 0L else 1L
            for(j in nums.indices) {
                if(isAdd)
                    colResult += nums[j][i]
                else
                    colResult *= nums[j][i]
            }
//            println("Result for col $i is $colResult")
            result += colResult
        }

        return result
    }

    private fun part2(data: String): Long {
        var result = 0L
        val grid = GridString.parse(data, ' ')
        val operators = mutableListOf<String>()
        data.lines().last().split(' ').filter { it.isNotEmpty() }.forEach { operators.add(it) }

        var i = 0 // current problem/operator
        var c = 0L
        val nums = mutableListOf<Long>()
        while(i < operators.size) {
            if(grid.isColumnEmpty(c)) {
                // calc result
                val isAdd = operators[i] == "+"
                val colResult = nums.reduce { acc, l -> if(isAdd) acc + l else acc * l }
//                println("Col result is $colResult operator $i is ${operators[i]} isAdd $isAdd")
                result += colResult
                // move to next operator
                c++
                i++
                nums.clear()
            }
            else {
                val colNum = grid.getColumn(c).filter { it.isDigit() }.map{it.digitToInt().toLong()}.reduce { acc, l -> acc*10 + l }
//                println("Col $c is $colNum")
                nums.add(colNum)
                c++
            }
        }
        return result
    }
}