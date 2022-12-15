package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils

private val testData = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"""


internal fun split(s: String): Pair<String, String> {
    val l = s.length / 2
    return Pair(s.substring(0, l), s.substring(l))
}

internal fun score(c: Char): Int {
    if (c.code < 97) {
        return c.code - 'A'.code + 27
    }
    return c.code - 'a'.code + 1
}

internal fun score(s: String): Int {
    val p = split(s)
    val s1 = p.first.toCharArray().toSet()
    val s2 = p.second.toCharArray().toSet()
    val common = s1.intersect(s2)
    assert(common.size == 1)
    return score(common.first())
}

private fun score(ss: Iterable<String>): Int {
    return ss.map { it.toCharArray().toSet() }.reduce { a, s -> a.intersect(s) }.map(::score).first()
}

fun day3part1(data: String): Int {
    return data.lines().map(::score).sum()
}

fun day3part2(data: String): Int {
    return data.lines().chunked(3).map(::score).sum()
}

private fun main() {
    val realData = Utils.input(2022, 3)
    println(day3part1(testData))
    println(day3part1(realData))
    println(day3part2(testData))
    println(day3part2(realData))
}