package com.drinkscabinet.aoc2020

import com.drinkscabinet.Utils




fun main() {
    println(saidAtTurn(testInput, 2020))
    println(saidAtTurn("1,3,2", 2020))
    println(saidAtTurn("2,1,3", 2020))
    println(saidAtTurn("1,2,3", 2020))
    println(saidAtTurn("2,3,1", 2020))
    println(saidAtTurn("3,2,1", 2020))
    println(saidAtTurn("3,1,2", 2020))
    println(saidAtTurn(input, 2020))
    println("Part2")
    println(saidAtTurn(input, 30000000))
}

fun saidAtTurn(s: String, t: Long) : Long {

    val whenLastSaid = mutableMapOf<Long, Long>()
    val whenLastSaid2 = IntArray(t.toInt(), {-1})
    val initial = Utils.extractLongs(s)
    var turn = 1L
    for (i in initial) {
        whenLastSaid[i] = turn
        ++turn
    }
    // Now start the game
    var firstTimeSaid = true
    var previouslySaid = 0L
    while (true) {
        // if the last iteration was first time said, we say 0
        var toSay = 0L
        if( firstTimeSaid ) {
            toSay = 0
            firstTimeSaid = false
        }
        else {
            // determine difference
            toSay = turn-1 - previouslySaid
        }

        // Now examine what we're about to say
        if( !whenLastSaid.containsKey(toSay)) {
            firstTimeSaid = true
        }
        else {
            previouslySaid = whenLastSaid[toSay]!!
        }
        whenLastSaid[toSay] = turn
//        println("Turn: $turn Value: $toSay")
        if (turn == t) return toSay
        ++turn
    }
}


private val testInput = """
    0,3,6
""".trimIndent()

private val input = """0,6,1,7,2,19,20"""