package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import com.drinkscabinet.contains
import com.drinkscabinet.overlaps
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

data class Cycle(val red: Int, val green: Int, val blue: Int) {
    fun possible(r: Int,  g: Int,  b: Int) : Boolean {
        return red <= r && green <= g && blue <= b
    }

    companion object {
        fun create(line: String) : Cycle {
            val balls = line.split(",")
            var red = 0
            var green = 0
            var blue = 0
            for(b in balls) {
                val count = Utils.extractInts(b)[0]
                if(b.contains("red")) red = count
                if(b.contains("green")) green = count
                if(b.contains("blue")) blue = count
            }
            return Cycle(red, green, blue)
        }
    }
}

class Game(val id: Int, private val cycles: List<Cycle>) {

    fun possible( r: Int,  g: Int,  b: Int) : Boolean {
        return cycles.all { it.possible(r,g,b) }
    }

    fun power() : Int {
        return cycles.maxOf { it.red } * cycles.maxOf { it.green } * cycles.maxOf { it.blue }
    }

    companion object {
        fun create(line: String) : Game {
            val idSplit = line.split(":")
            val gameId = Utils.extractInts(idSplit[0])[0]
            val cycleStrings = idSplit[1].split(";")
            val cycles = cycleStrings.map { Cycle.create(it) }.toList()
            return Game(gameId, cycles)
        }
    }
}


class Day2KtTest {

    private val testData = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"""


    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(8, this.part1(testData))
        assertEquals(2551, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(2286, this.part2(testData))
        assertEquals(62811, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val games = data.lines().map { Game.create(it) }.toList()
        return games.filter { it.possible(12, 13, 14) }.map { it.id }.sum()
    }

    private fun part2(data: String): Int {
        val games = data.lines().map { Game.create(it) }.toList()
        return games.sumOf { it.power() }
    }


}