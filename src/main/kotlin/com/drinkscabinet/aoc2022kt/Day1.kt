package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import com.drinkscabinet.chunks

private fun main() {
    val input = Utils.input(2022, 1)
    println(input.chunks().map { c -> c.lines().sumOf { s -> s.toInt() } }.max())
    println(input.chunks().map { c -> c.lines().sumOf { s -> s.toInt() } }.sortedDescending().take(3).sum())
}