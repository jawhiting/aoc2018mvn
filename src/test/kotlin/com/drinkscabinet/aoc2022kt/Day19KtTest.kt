package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


data class Blueprint(
    val id: Int, val oreCost: Int, val clayCost: Int, val obsidianCost: Pair<Int, Int>, val geodeCost: Pair<Int, Int>
) {
    companion object {
        fun parse(line: String): Blueprint {
            val nums = Utils.extractUInts(line)
            assert(nums.size == 7)
            return Blueprint(nums[0], nums[1], nums[2], nums[3] to nums[4], nums[5] to nums[6])
        }
    }
}

data class State(
    val time: Int,
    val orCount: Int,
    val crCount: Int,
    val obCount: Int,
    val grCount: Int,
    val ore: Int,
    val clay: Int,
    val obsidian: Int,
    val geode: Int,
    val action: String
) {

    fun nextStates(blueprint: Blueprint, timeLimit: Int): List<State> {
        if (time >= timeLimit) {
            return listOf()
        }

        val result = mutableListOf<State>()
        // Prioritise geodes
        if (obCount > 0) {
            for (t in 0 until timeLimit - time) {
                val nextState = tick(t)
                if (nextState.canBuildGeode(blueprint)) {
                    result.add(nextState.buildGeode(blueprint))
                    break
                }
            }
            // Also include final state where we just wait out the timer
            result.add(tick(timeLimit - time))
        }
        // Next obsidian
        if (crCount > 0) {
            for (t in 0 until timeLimit - time) {
                val nextState = tick(t)
                if (nextState.canBuildObsidian(blueprint)) {
                    result.add(nextState.buildObsidian(blueprint))
                    break
                }
            }
        }
        // Next clay
        for (t in 0 until timeLimit - time) {
            val nextState = tick(t)
            if (nextState.canBuildClay(blueprint)) {
                result.add(nextState.buildClay(blueprint))
                break
            }
        }
        // Next ore
        for (t in 0 until timeLimit - time) {
            val nextState = tick(t)
            if (nextState.canBuildOre(blueprint)) {
                result.add(nextState.buildOre(blueprint))
                break
            }
        }

        return result
    }

    fun tick(count: Int): State {
        return this.copy(
            time = time + count,
            ore = ore + orCount * count,
            clay = clay + crCount * count,
            obsidian = obsidian + obCount * count,
            geode = geode + grCount * count,
            action = "Wait"
        )
    }

    fun maxPossible(timeLimit: Int): Int {
        return geode + (0 until timeLimit - time).sumOf { grCount + it }
    }

    private fun canBuildOre(blueprint: Blueprint): Boolean {
        return ore >= blueprint.oreCost
    }

    private fun canBuildClay(blueprint: Blueprint): Boolean {
        return ore >= blueprint.clayCost
    }

    private fun canBuildObsidian(blueprint: Blueprint): Boolean {
        return ore >= blueprint.obsidianCost.first && clay >= blueprint.obsidianCost.second
    }

    private fun canBuildGeode(blueprint: Blueprint): Boolean {
        return ore >= blueprint.geodeCost.first && obsidian >= blueprint.geodeCost.second
    }

    private fun buildGeode(blueprint: Blueprint): State {
        return this.copy(
            time = time + 1,
            ore = ore + orCount - blueprint.geodeCost.first,
            clay = clay + crCount,
            obsidian = obsidian + obCount - blueprint.geodeCost.second,
            geode = geode + grCount,
            grCount = grCount + 1,
            action = "Build Geode Robot"
        )
    }

    private fun buildObsidian(blueprint: Blueprint): State {
        return this.copy(
            time = time + 1,
            ore = ore + orCount - blueprint.obsidianCost.first,
            clay = clay + crCount - blueprint.obsidianCost.second,
            obsidian = obsidian + obCount,
            geode = geode + grCount,
            obCount = obCount + 1,
            action = "Build Obsidian Robot"
        )
    }

    private fun buildClay(blueprint: Blueprint): State {
        return this.copy(
            time = time + 1,
            ore = ore + orCount - blueprint.clayCost,
            clay = clay + crCount,
            obsidian = obsidian + obCount,
            geode = geode + grCount,
            crCount = crCount + 1,
            action = "Build Clay Robot"
        )
    }

    private fun buildOre(blueprint: Blueprint): State {
        return this.copy(
            time = time + 1,
            ore = ore + orCount - blueprint.oreCost,
            clay = clay + crCount,
            obsidian = obsidian + obCount,
            geode = geode + grCount,
            orCount = orCount + 1,
            action = "Build Ore Robot"
        )
    }
}


class Solver(val blueprint: Blueprint, private val timeLimit: Int) {
    private var maxScore: Pair<ArrayDeque<State>, Int> = ArrayDeque<State>() to 0

    fun run(): Pair<ArrayDeque<State>, Int> {
        val initialState = State(0, 1, 0, 0, 0, 0, 0, 0, 0, "Start")
        return score(initialState, ArrayDeque())
    }

    private fun score(state: State, stack: ArrayDeque<State>): Pair<ArrayDeque<State>, Int> {
        val nextStates = state.nextStates(blueprint, timeLimit)
        if (nextStates.isEmpty()) {
            // reached end, return number of geodes
            return ArrayDeque(stack) to state.geode
        }
        for (s in nextStates.filter { it.maxPossible(timeLimit) > maxScore.second }) {
            stack.addLast(s)
            val score = score(s, stack)
            if (score.second > maxScore.second) {
                println("Found higher score $score vs $maxScore")
                for (st in stack) {
                    println(st)
                }
                maxScore = score
            }
            stack.removeLast()
        }
        return maxScore
    }
}

class Day19KtTest {

    private val testData =
        """Blueprint 1:  Each ore robot costs 4 ore.  Each clay robot costs 2 ore.  Each obsidian robot costs 3 ore and 14 clay.  Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2:  Each ore robot costs 2 ore.  Each clay robot costs 3 ore.  Each obsidian robot costs 3 ore and 8 clay.  Each geode robot costs 3 ore and 12 obsidian."""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testBlueprint1() {
        val blueprint = Blueprint.parse(testData.lines()[0])
        val solver = Solver(blueprint, 24)
        assertEquals(9, solver.run().second)
    }

    @Test
    fun testBlueprint2() {
        val blueprint = Blueprint.parse(testData.lines()[1])
        val solver = Solver(blueprint, 24)
        assertEquals(12, solver.run().second)
    }

    @Test
    fun testPart1() {
        assertEquals(33, part1(testData))
    }

    @Test
    fun testPart1Real() {
        // 4495 too high
        //1536 too low
        assertEquals(1565, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(56, part2(testData.lines()[0]))
        assertEquals(62, part2(testData.lines()[1]))
    }

    @Test
    fun testPart2real() {
        assertEquals(10672, part2(realData))
    }


    fun part1(data: String): Int {
        val blueprints = data.lines().map { Solver(Blueprint.parse(it), 24) }.toList()
        val bpScores = blueprints.parallelStream().map { it.blueprint.id to it.run().second }.toList().toMap()
        println(bpScores)
        return bpScores.map { it.key * it.value }.sum()
    }


    fun part2(data: String): Int {
        val blueprints = data.lines().map { Solver(Blueprint.parse(it), 32) }.toList().take(3)
        val bpScores = blueprints.parallelStream().map { it.blueprint.id to it.run().second }.toList().toMap()
        println(bpScores)
        return bpScores.values.fold(1) { a, i -> a * i }
    }
}