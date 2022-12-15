package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import com.drinkscabinet.chunks

private fun main() {
    val input = Utils.input(2022, 1)
    println(input.chunks().map { s -> s.lines().sumOf { it.toInt() } }.max())
    println(input.chunks().map { s -> s.lines().sumOf { it.toInt() } }.sortedDescending().take(3).sum())
}