package com.drinkscabinet.aoc2019

import com.drinkscabinet.Utils

class Day2(val orig: IntArray) {
    var ip = 0
    var program = orig.copyOf()

    fun part1() {
        while(program[ip] != 99 ) {
            if( !apply(ip)) {
                println(program.contentToString())
                return
            }
        }
    }

    fun part2(n: Int, v: Int): Int {
        program = orig.copyOf()
        program[1] = n
        program[2] = v
        part1()
        return program[0]
    }

    fun apply(pos: Int) : Boolean {
        val inst = program[ip]
        if( inst == 1) {
            program[program[ip+3]] = program[program[ip+1]] + program[program[ip+2]]
        }
        else if( inst == 2) {
            program[program[ip+3]] = program[program[ip+1]] * program[program[ip+2]]
        }
        else {
            return false
        }
//        println(""+ ip + ":" + program.contentToString())
        ip += 4
        return true
    }

}

private val testInput = "1,9,10,3,2,3,11,0,99,30,40,50"

private fun main() {
    Day2(Utils.extractInts(testInput)).part1()
    Day2(Utils.extractInts("2,4,4,5,99,0")).part1()
    Day2(Utils.extractInts("1,1,1,4,99,5,6,0,99")).part1()

    val orig = Utils.extractInts(input)
    var part1 = orig.copyOf()
    part1[1] = 12
    part1[2] = 2
    Day2(part1).part1()

    val d2 = Day2(orig)
    println(d2.part2(12, 2))
    for( n in 0..1000 ) {
        for( v in 0..1000) {
            val r = d2.part2(n, v)
            if( r != 1 ) println("$n $v $r")
            if( r == 19690720 ) return

        }
    }
}


private val input = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,1,5,19,23,1,23,5,27,1,27,13,31,1,31,5,35,1,9,35,39,2,13,39,43,1,43,10,47,1,47,13,51,2,10,51,55,1,55,5,59,1,59,5,63,1,63,13,67,1,13,67,71,1,71,10,75,1,6,75,79,1,6,79,83,2,10,83,87,1,87,5,91,1,5,91,95,2,95,10,99,1,9,99,103,1,103,13,107,2,10,107,111,2,13,111,115,1,6,115,119,1,119,10,123,2,9,123,127,2,127,9,131,1,131,10,135,1,135,2,139,1,10,139,0,99,2,0,14,0"