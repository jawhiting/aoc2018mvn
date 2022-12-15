package com.drinkscabinet.aoc2020

import java.lang.Math.pow
import java.util.function.BiFunction
import kotlin.math.pow

fun main() {
    val adaptors = input.lines().map { it.toInt() }.toSortedSet().toMutableList()

    // Add start
    adaptors.add(0, 0)
    // Add end
    adaptors.add(adaptors.maxOrNull()!!+3)

    println(adaptors)

    var runLength = 0

    val diffs = mutableMapOf<Int, Int>()
    var runLengths = mutableMapOf<Int, Int>()
    for( i in 0..adaptors.lastIndex-1) {
        val diff = adaptors[i+1] - adaptors[i]
        if( diff == 3 ) {
            runLengths.merge(runLength, 1) { a, b -> a + b }
            runLength = 0
        }
        else {
            runLength++
        }
        diffs.merge(diff, 1) { a, b -> a + b }
    }

    println(runLengths)
    println(diffs)
    println(diffs[1]!! * diffs[3]!!)

    // 3-gap can't be bridged
    // 0,3,4,5,6,9
    //  3 1 1 1 3
    // 3,4,5,6, 3,5,6, 3,4,6, 3,6 - 2 numbers between anchors -> 4 combinations
    // 0,3,4,5,6,7,10
    //  3 1 1 1 1 3
    // 34567, 3457, 3467, 3567, 367, 357, 347,

    val combos = mutableMapOf<Int, Int>()
    combos[0] = 1
    combos[1] = 1
    combos[2] = 2
    combos[3] = 4
    combos[4] = 7

    var part2result = 1L
    for (runLength in runLengths.keys) {
        part2result *= combos[runLength]!!.toDouble().pow(runLengths[runLength]!!).toInt()
    }
    println("Part2=$part2result")
}

private val testInput = """
    16
    10
    15
    5
    1
    11
    7
    19
    6
    12
    4
""".trimIndent()

private val testInput2 = """
    28
    33
    18
    42
    31
    14
    46
    20
    48
    47
    24
    23
    49
    45
    19
    38
    39
    11
    1
    32
    25
    35
    8
    17
    7
    9
    4
    2
    34
    10
    3
""".trimIndent()

private val input = """
    26
    97
    31
    7
    2
    10
    46
    38
    112
    54
    30
    93
    18
    111
    29
    75
    139
    23
    132
    85
    78
    99
    8
    113
    87
    57
    133
    41
    104
    98
    58
    90
    13
    91
    20
    68
    103
    127
    105
    114
    138
    126
    67
    32
    145
    115
    16
    141
    1
    73
    45
    119
    51
    40
    35
    150
    118
    53
    80
    79
    65
    135
    74
    47
    128
    64
    17
    4
    84
    83
    147
    142
    146
    9
    125
    94
    140
    131
    134
    92
    66
    122
    19
    86
    50
    52
    108
    100
    71
    61
    44
    39
    3
    72
""".trimIndent()