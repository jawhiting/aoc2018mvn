package com.drinkscabinet.aoc2020

import com.drinkscabinet.Utils
import java.lang.Exception
import java.util.*

private fun main() {
    val deck1 = LinkedList<Int>(Utils.extractInts(input.substringAfter("Player 1:").substringBefore("Player 2:")).toList())
    val deck2 = LinkedList<Int>(Utils.extractInts(input.substringAfter("Player 2:")).toList())

    println(deck1)
    println(deck2)

    val game = Game(0, deck1, deck2)

    val winner = game.play()

    println(deck1)
    println(deck2)

    val winningDeck = if (winner) deck1 else deck2
    println(winningDeck.mapIndexed { i, x -> (winningDeck.size - i) * x }.sum())
}

var globalRoundCounter = 1L

private class Game(val level: Int, val deck1: LinkedList<Int>, val deck2: LinkedList<Int>) {
    private val previousConfigurations = mutableSetOf<Pair<List<Int>, List<Int>>>()
    private var round = 1
    private val gameCache = getCacheForLevel(level)

    fun play(): Boolean {
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            // Have we seen this config before?
            if (seenPreviously(deck1, deck2)) return true
            // Play a round
            round()
            ++round
        }
        if (deck2.isEmpty()) return true
        if (deck1.isEmpty()) return false
        throw Exception("Should not get here $deck1  $deck2")
    }

    // return true if player 1 wins
    fun round() {
        // Now draw top card
//        println("Player1 deck: $deck1")
//        println("Player2 deck: $deck2")
        val card1 = deck1.pop()
        val card2 = deck2.pop()

        if( globalRoundCounter++ % 1000000L == 0L) {
            println("$globalRoundCounter Playing $level $round with cards $card1 vs $card2")
        }
        val winner: Boolean
        if (card1 <= deck1.size && card2 <= deck2.size) {
            // recurse
            // see if we can avoid the subgame
            val cacheResult = gameCache.result(deck1, deck2)
            if( cacheResult == null ) {
                val subGame = Game(level + 1, LinkedList(deck1.subList(0, card1)), LinkedList(deck2.subList(0, card2)))
                winner = subGame.play()
                // store for later
                gameCache.record(deck1, deck2, winner)
            }
            else {
//                println("Cache hit")
                winner = cacheResult
            }
        } else {
            winner = card1 > card2
        }
        if (winner) {
            // player 1
            deck1.add(card1)
            deck1.add(card2)
        } else {
            deck2.add(card2)
            deck2.add(card1)
        }
    }

    private fun seenPreviously(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Boolean {
        if (previousConfigurations.contains(deck1 to deck2)) return true
        // add to list
        previousConfigurations.add(deck1.toList() to deck2.toList())
        return false
    }
}

private val gameCacheByLevel = mutableMapOf<Int, GameCache>()

private fun getCacheForLevel(level: Int): GameCache {
    return gameCacheByLevel.computeIfAbsent(level) { GameCache() }
}

private class GameCache {
    val configWinner = mutableMapOf<Pair<List<Int>, List<Int>>, Boolean>()

    fun result(deck1: List<Int>, deck2: List<Int>): Boolean? {
        return configWinner[deck1 to deck2]
    }

    fun record(deck1: List<Int>, deck2: List<Int>, result: Boolean) {
        configWinner[deck1.toList() to deck2.toList()] = result
    }
}

private val previousConfigurations = mutableSetOf<Pair<List<Int>, List<Int>>>()

// return true if deck1 wins
private fun round(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Boolean {
    if (seenPreviously(deck1, deck2)) return true

    // Now draw top card
    val card1 = deck1.pop()
    val card2 = deck2.pop()
    if (card1 <= deck1.size && card2 <= deck2.size) {
        // recurse
        return round(deck1, deck2)
    } else {
        return card1 > card2
    }
}

private fun seenPreviously(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Boolean {
    if (previousConfigurations.contains(deck1 to deck2)) return true
    // add to list
    previousConfigurations.add(deck1.toList() to deck2.toList())
    return false
}

private fun part1() {
    val deck1 = LinkedList<Int>(Utils.extractInts(input.substringAfter("Player 1:").substringBefore("Player 2:")).toList())
    val deck2 = LinkedList<Int>(Utils.extractInts(input.substringAfter("Player 2:")).toList())

    println(deck1)
    println(deck2)

    var roundCount = 1
    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {

        // Compare top cards
        val card1 = deck1.pop()!!
        val card2 = deck2.pop()!!

        println("Round ${roundCount++} $card1 vs $card2 [${deck1.size} ${deck2.size}]")

        if (card1 > card2) {
            deck1.addLast(card1)
            deck1.addLast(card2)
        } else {
            deck2.addLast(card2)
            deck2.addLast(card1)
        }
    }

    println(deck1)
    println(deck2)

    val winner = if (deck1.isNotEmpty()) deck1 else deck2
    println(winner.mapIndexed { i, x -> (winner.size - i) * x }.sum())
}

private val testInput2 = """
    Player 1:
    43
    19

    Player 2:
    2
    29
    14
""".trimIndent()

private val testInput = """
    Player 1:
    9
    2
    6
    3
    1

    Player 2:
    5
    8
    4
    7
    10
""".trimIndent()


private val input = """
    Player 1:
    26
    16
    33
    8
    5
    46
    12
    47
    39
    27
    50
    10
    34
    20
    23
    11
    43
    14
    18
    1
    48
    28
    31
    38
    41

    Player 2:
    45
    7
    9
    4
    15
    19
    49
    3
    36
    25
    24
    2
    21
    37
    35
    44
    29
    13
    32
    22
    17
    30
    42
    40
    6
""".trimIndent()