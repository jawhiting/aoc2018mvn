package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import kotlin.streams.toList


val test_data = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"""


private fun split(s: String): Pair<String, String> {
    val l = s.length / 2
    return Pair(s.substring(0, l), s.substring(l))
}

private fun score(c: Char): Int {
    if (c.code < 97) {
        return c.code - 'A'.code + 27
    }
    return c.code - 'a'.code + 1
}

private fun score(s: String): Int {
    val p = split(s)
    val s1 = p.first.toCharArray().toSet()
    val s2 = p.second.toCharArray().toSet()
    val common = s1.intersect(s2)
    assert(common.size == 1)
    return score(common.first())
}

private fun score(ss: Iterable<String>): Int {
    var common = ss.first().toCharArray().toSet()
    ss.forEach { common = common.intersect(it.toCharArray().toSet()) }
    assert(common.size == 1)
    return score(common.first())
}
private fun main() {
    println(test_data.lines().map(::score).sum())
    println(Utils.input(2022, 3) .lines().map(::score).sum())

    println(test_data.lines().chunked(3).map(::score).sum())
    println(Utils.input(2022, 3).lines().chunked(3).map(::score).sum())
}