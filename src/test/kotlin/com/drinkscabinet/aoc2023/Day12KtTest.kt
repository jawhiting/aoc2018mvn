package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import com.github.jsonldjava.shaded.com.google.common.collect.Table
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.exp

class Day12KtTest {

    private val testData = """???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1"""

    private val realData = Utils.input(this)

    @Test
    fun testMatches() {
        assertEquals(1, ways(testData.lines()[0]))
        assertEquals(4, ways(testData.lines()[1]))
    }

    @Test
    fun testExpand() {
        assertEquals(".#?.#?.#?.#?.# 1,1,1,1,1", expand(".# 1"))
        assertEquals("???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3", expand("???.### 1,1,3"))
    }

    private fun expand(s: String): String {
        // repeat the string 5 times joined by x
        val parts = s.split(" ")
        val spring = repeatAndJoin(parts[0], "?")
        val target = repeatAndJoin(parts[1], ",")
        return "$spring $target"
    }

    private fun repeatAndJoin(s: String, delim: String): String {
        return listOf(s, s, s, s, s).joinToString(delim)
    }

    @Test
    fun testPart1() {
        assertEquals(21, part1(testData))
        assertEquals(6827, part1(realData))
    }

    @Test
    fun testPart1a() {
        assertEquals(21, part1a(testData))
        assertEquals(6827, part1a(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(525152, part2(testData))
        assertEquals(6827, part2(realData))
    }

    private fun part1(data: String): Long {
        return data.lines().sumOf { ways(it) }
    }

    private fun part1a(data: String): Long {
        return data.lines().sumOf { ways2(it) }
    }

    private fun part2(data: String): Long {
        var sum = 0L
        for ((i, line) in data.lines().withIndex()) {
            val expanded = expand(line)
            println("$i $expanded")
            val c = ways2(expanded)
            println("$c")
            sum += c
        }
        return sum
    }

    private val groupCache = mutableMapOf<String, MutableList<Int>>("." to mutableListOf(0), "#" to mutableListOf(1), "" to mutableListOf(0))
    private fun countGroups(data: String): MutableList<Int> {
        return countGroupsIncremental(data).filter { it != 0 }.toMutableList()
//        return groupCache.computeIfAbsent(data) { countGroups2(it) }
    }

    private fun countGroups2(data: String): MutableList<Int> {
        val groups = data.split(".").filter { it.isNotBlank() }
        return groups.map { it.length }.toMutableList()
    }

    private fun countGroupsIncremental(data: String): MutableList<Int> {
        val cached = groupCache[data]
        if(cached != null) return cached
        val prev = countGroupsIncremental(data.dropLast(1)).toMutableList()
        if(data.last() == '.') {
            if(prev.last() != 0) {
                prev.add(0)
            }
        }
        else {
            prev[prev.size - 1]++
        }
        groupCache[data] = prev
        return prev
    }

    @Test
    fun testCountGroups() {
        assertEquals(listOf(1, 2), countGroups(".#...##.."))
        assertEquals(listOf(1, 2, 1), countGroups(".#...##.#."))
        assertEquals(listOf<Int>(), countGroups("......"))
        assertEquals(listOf<Int>(5), countGroups("#####"))

//        assertEquals(listOf(1, 2), countGroupsIncremental(".#...##.."))
//        assertEquals(listOf(1, 2, 1), countGroupsIncremental(".#...##.#."))
//        assertEquals(listOf<Int>(), countGroupsIncremental("......"))
//        assertEquals(listOf<Int>(5), countGroupsIncremental("#####"))
    }

    @Test
    fun testBuildAndMatch() {
        assertEquals(1L, buildAndMatch(".#...##..", 0, "", mutableListOf(1, 2)))
        assertEquals(1L, buildAndMatch2Start(".#...##..", mutableListOf(1, 2)))
        assertEquals(1L, buildAndMatch(".#...##.#.", 0, "", mutableListOf(1, 2, 1)))
        assertEquals(1L, buildAndMatch("???.###", 0, "", mutableListOf(1, 1, 3)))
        assertEquals(4L, buildAndMatch(".??..??...?##.", 0, "", mutableListOf(1, 1, 3)))
        assertEquals(10L, buildAndMatch("?###????????", 0, "", mutableListOf(3, 2, 1)))
        assertEquals(10L, buildAndMatch2Start("?###????????", mutableListOf(3, 2, 1)))
    }

    @Test
    fun testProgressMatch() {
        assertEquals(true, progressMatch(".#...##..", mutableListOf(1, 2)))
        assertEquals(true, progressMatch(".#...##..", mutableListOf(1, 2, 3)))
        // might be part way through a run
        assertEquals(true, progressMatch(".#...##..##", mutableListOf(1, 2, 3)))
        assertEquals(false, progressMatch(".#...##..####", mutableListOf(1, 2, 3)))
        assertEquals(false, progressMatch(".#...#..", mutableListOf(1, 2, 3)))
        assertEquals(true, progressMatch("......", mutableListOf(1, 2, 3)))
        assertEquals(true, progressMatch("", mutableListOf(1, 2, 3)))
    }

    private fun ways(data: String): Long {
        val s = data.split(" ")[0]
        val target = Utils.extractInts(data).toMutableList()
        return buildAndMatch(s, 0, "", target)
    }

    private fun ways2(data: String): Long {
        val s = data.split(" ")[0]
        val target = Utils.extractInts(data).toMutableList()
        return buildAndMatch2Start(s, target)
    }

    private val waysCache = mutableMapOf<String, MutableMap<MutableList<Int>, Long>>()
    private fun ways(data: String, target: MutableList<Int>): Long {
        val sCache = waysCache.getOrPut(data) {mutableMapOf()}
        return sCache.computeIfAbsent(target) {buildAndMatch(data, 0, "", target)}
    }

    private fun progressMatch(partial: String, target: MutableList<Int>): Boolean {
        val partialGroups = countGroups(partial)
        if (partialGroups.size > target.size) return false
        for (i in partialGroups.indices) {
            // special case for partial run
            if (i == partialGroups.indices.last && partial.last() == '#' && partialGroups[i] <= target[i]) return true
            if (partialGroups[i] != target[i]) return false
        }
        return true
    }

    fun buildAndMatch2Start(template: String, target: MutableList<Int>) : Long {
        val groups = template.split(".").filter { it.isNotBlank() }
        return buildAndMatch2(groups, target)
    }
    private fun buildAndMatch2(groups: List<String>, target: List<Int>): Long {
        if(groups.isEmpty() && target.isEmpty()) return 1
        if(groups.isEmpty()) return 0
        val group = groups.first()
        val groupLength = group.length
        val hashCount = group.count { it == '#' }
        if(hashCount > 0 && target.isEmpty()) return 0
        var count = 0L
        // Now pick targets
        var targetTotal = 0
        val subTargets = mutableListOf<Int>()
        val remainingTargets = target.toMutableList()
        while(targetTotal <= groupLength && remainingTargets.isNotEmpty()) {
            val nextTarget = remainingTargets.removeFirst()!!
            targetTotal += nextTarget
            subTargets += nextTarget
            // See how many children at this stage
            val factor = ways(group, subTargets)
            // and recurse
            count += factor * buildAndMatch2(groups.drop(1), remainingTargets)
        }
        return count
    }

    private fun buildAndMatch(template: String, pos: Int, built: String, target: MutableList<Int>): Long {
        var count = 0L
        var nextBuilt = built
        // prune
        if (!progressMatch(built, target)) return 0
        for (i in pos until template.length) {
            if (template[i] == '?') {
                count += buildAndMatch(template, i + 1, "$nextBuilt#", target)
                count += buildAndMatch(template, i + 1, "$nextBuilt.", target)
                return count
            } else {
                nextBuilt += template[i]
            }
        }
        return if (countGroups(nextBuilt) == target) {
            1
        } else {
            0
        }
    }


    private fun matches(
        s: String,
        sp: Int,
        currentRun: Int,
        currentIsHash: Boolean,
        target: MutableList<Int>,
        targetPos: Int
    ): Long {

        // If we were in a run, see if it ended
        if (currentRun > 0 && !currentIsHash) {
            // now check vs current target
//            if (curr)
        }
        var run = currentRun + (if (currentIsHash) 1 else 0)
        var currentPos = sp + 1

        // Out of targets - only valid if rest of string is non-hash
        if (targetPos == target.size && currentIsHash) return 0
        if (targetPos == target.size) {
            // scan rest of string
            while (currentPos < s.length) {
                if (s[currentPos] == '#') return 0
                currentPos++
            }
            return 1
        }
        // Exits
        // run too long
        if (targetPos in target.indices && run > target[targetPos]) return 0
        // end of string
        if (currentPos == s.length) {
            // targets matched
            if (targetPos == target.size - 1 && currentRun == target[targetPos]) return 1
            return 0
        }
        // Now scan
        while (currentPos < s.length) {

        }
        return 0
    }
}