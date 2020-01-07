package com.drinkscabinet.aoc2019

import com.drinkscabinet.Utils

private fun main() {
    println(deck(10).contentToString())
    println(newStack(deck(10)).contentToString())
    println(cut(deck(10), 3).contentToString())
    println(cut(deck(10), -4).contentToString())
    println(increment(deck(10), 3).contentToString())

    println(process(10, testInput1).contentToString())
    println(process(10, testInput2).contentToString())
    println(process(10, testInput3).contentToString())
    println(process(10, testInput4).contentToString())
    val result = process(10007, input)
    println(result.indexOf(2019))
    // 9172 too high
}

private fun process(size: Int, instructions: String) : IntArray {
    var current = deck(size)
    for (instruction in instructions.lines()) {
        if( instruction.startsWith("cut")) {
            current = cut(current, Utils.extractInts(instruction).first())
        }
        else if (instruction.endsWith("stack")) {
            current = newStack(current)
        }
        else {
            current = increment(current, Utils.extractInts(instruction).first())
        }
    }
    return current
}

private fun deck(size: Int ) : IntArray {
    return IntArray(size, {it})
}

private fun newStack(arr: IntArray) : IntArray {
    return arr.reversedArray()
}

private fun cut(arr: IntArray, n: Int) : IntArray {
    val result = IntArray(arr.size)
    if( n >= 0 ) {
        arr.copyInto(result, 0, n)
        arr.copyInto(result, arr.size-n, 0, n)
    }
    else {
        // negative
        val offset = arr.size + n
        arr.copyInto(result, 0, offset)
        arr.copyInto(result, arr.size-offset, 0, offset)
    }
    return result
}

private fun increment(arr: IntArray, n: Int) : IntArray {
    val result = IntArray(arr.size)
    for( i in arr.indices) {
        result[i*n % arr.size] = arr[i]
    }
    return result
}

private val testInput1 = """
    deal with increment 7
    deal into new stack
    deal into new stack
""".trimIndent()

private val testInput2 = """
    cut 6
    deal with increment 7
    deal into new stack
""".trimIndent()

private val testInput3 = """
    deal with increment 7
    deal with increment 9
    cut -2
""".trimIndent()

private val testInput4 = """
    deal into new stack
    cut -2
    deal with increment 7
    cut 8
    cut -4
    deal with increment 7
    cut 3
    deal with increment 9
    deal with increment 3
    cut -1
""".trimIndent()


private val input = """
    cut -1353
    deal with increment 63
    cut -716
    deal with increment 55
    cut 1364
    deal with increment 61
    cut 1723
    deal into new stack
    deal with increment 51
    cut 11
    deal with increment 65
    cut -6297
    deal with increment 69
    cut -3560
    deal with increment 20
    cut 1177
    deal with increment 29
    cut 6033
    deal with increment 3
    cut -3564
    deal into new stack
    cut 6447
    deal into new stack
    cut -4030
    deal with increment 3
    cut -6511
    deal with increment 42
    cut -8748
    deal with increment 38
    cut 5816
    deal with increment 73
    cut 9892
    deal with increment 16
    cut -9815
    deal with increment 10
    cut 673
    deal with increment 12
    cut 4518
    deal with increment 52
    cut 9464
    deal with increment 68
    cut 902
    deal with increment 11
    deal into new stack
    deal with increment 45
    cut -5167
    deal with increment 68
    deal into new stack
    deal with increment 24
    cut -8945
    deal into new stack
    deal with increment 36
    cut 3195
    deal with increment 52
    cut -1494
    deal with increment 11
    cut -9658
    deal into new stack
    cut -4689
    deal with increment 34
    cut -9697
    deal with increment 39
    cut -6857
    deal with increment 19
    cut -6790
    deal with increment 59
    deal into new stack
    deal with increment 52
    cut -9354
    deal with increment 71
    cut 8815
    deal with increment 2
    cut 6618
    deal with increment 47
    cut -6746
    deal into new stack
    cut 1336
    deal with increment 53
    cut 6655
    deal with increment 17
    cut 8941
    deal with increment 25
    cut -3046
    deal with increment 14
    cut -7818
    deal with increment 25
    cut 4140
    deal with increment 60
    cut 6459
    deal with increment 27
    cut -6791
    deal into new stack
    cut 3821
    deal with increment 13
    cut 3157
    deal with increment 13
    cut 8524
    deal into new stack
    deal with increment 12
    cut 5944
""".trimIndent()