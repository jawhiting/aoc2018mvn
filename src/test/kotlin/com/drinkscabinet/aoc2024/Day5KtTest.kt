package com.drinkscabinet.aoc2024

import Direction8
import GridString
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day5KtTest {

    private val testData = """47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(143, this.part1(testData))
        assertEquals(4996, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(123, this.part2(testData))
        assertEquals(6311, this.part2(realData))
    }

    private fun part1(data: String): Int {
        val rules = readRules(data.split("\n\n")[0])
        val updates = readUpdates(data.split("\n\n")[1])

        return updates.filter { check(it, rules) }.sumOf { it[it.lastIndex / 2] }
    }

    private fun part2(data: String): Int {
        val rules = readRules(data.split("\n\n")[0])
        val updates = readUpdates(data.split("\n\n")[1])

        // Sort all the updates
        val cmp = RuleComparator(rules.second)
        // Just want the invalid ones
        val sorted = updates.filter { !check(it, rules) }.map { it.sortedWith(cmp)  }.toList()

        return sorted.sumOf { it[it.lastIndex / 2] }
    }

    private fun check(update: List<Int>, rules: Pair<Map<Int, Set<Int>>, Map<Int, Set<Int>>>) : Boolean {
        val updateSet = update.toSet()
        val seen = mutableSetOf<Int>()
        val before = rules.first
        val after = rules.second
        for(i in update) {
            // get any rule
            val b = before.getOrDefault(i, emptySet())
            // all the numbers in b that are in the update must already have been encountered
            val bu = b.intersect(updateSet)
            if(bu.intersect(seen).size != bu.size) {
                return false
            }
            val a = after.getOrDefault(i, emptySet())
            // all the numbers in a must not have been encountered
            if(a.intersect(seen).isNotEmpty()) {
                return false
            }
            seen.add(i)
        }
        return true
    }

    private fun readRules(data: String) : Pair<Map<Int, Set<Int>>, Map<Int, Set<Int>>> {
        // numbers in the set must be after the key
        val after = mutableMapOf<Int, MutableSet<Int>>()
        // numbers in the set must be before the key
        val before = mutableMapOf<Int, MutableSet<Int>>()
        for(l in data.lines()) {
            val i = Utils.extractInts(l)
            after.getOrPut(i[0]) { mutableSetOf<Int>() }.add(i[1])
            before.getOrPut(i[1]) { mutableSetOf<Int>() }.add(i[0])
        }
        return before to after
    }

    private fun readUpdates(data: String) : List<List<Int>> {
        return data.lines().map{ Utils.extractInts(it).toList() }.toList()
    }

    private class RuleComparator(val afters: Map<Int, Set<Int>>) : Comparator<Int> {

        override fun compare(o1: Int?, o2: Int?): Int {
            val a = this.afters[o1]
            if(a != null && a.contains(o2)) {
                return -1
            }
            return 1
        }

    }

    @Test
    fun testReadRules() {
        val r = readRules(testData.split("\n\n")[0])
        print(r)
        assertEquals(6, r.first.size)
    }

    @Test
    public fun testReadUpdates() {
        val r = readUpdates(testData.split("\n\n")[1])
        print(r)
        assertEquals(6, r.size)
    }

}