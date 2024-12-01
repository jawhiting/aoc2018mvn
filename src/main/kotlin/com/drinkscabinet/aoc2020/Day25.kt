package com.drinkscabinet.aoc2020

private fun main() {
    println(transformWithTarget(7, 5764801))
    println(transformWithTarget(7, 17807724))

    println(transform(17807724, 8))
    println(transform(5764801, 11))


    val part1card = 19241437L
    val part1door = 17346587L

    val cardLoop = transformWithTarget(7, part1card)
    val doorLoop = transformWithTarget(7, part1door)
    println("$cardLoop and $doorLoop")

    println(transform(part1card, doorLoop))
    println(transform(part1door, cardLoop))

}


fun transform(subject: Long, loopSize: Int): Long {
    var current = 1L
    for (i in 1..loopSize) {
        current = (current * subject) % 20201227
    }
    return current
}

fun transformWithTarget(subject: Long, target: Long): Int {
    var loopCount = 0
    var current = 1L
    while (current != target) {
        current = (current * subject) % 20201227
        ++loopCount
    }

    return loopCount
}