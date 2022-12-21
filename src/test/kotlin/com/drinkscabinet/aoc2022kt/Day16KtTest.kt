package com.drinkscabinet.aoc2022kt

import PathFinder
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


data class Valve(val id: String, val rate: Int, val exitNames: List<String>) {

    fun distanceTo(other: Valve): Int {
        val cached = distances[id to other.id]
        if (cached != null) {
            return cached
        }
        val result = PathFinder.dijkstra2(this.id, other.id, ::nextMoves)
        val distance = result.first[other.id]!!
        distances[id to other.id] = distance
        distances[other.id to id] = distance
        return distance
    }

    fun distanceTo(other: String): Int {
        val cached = distances[id to other]
        if (cached != null) {
            return cached
        }
        return distanceTo(valvesByName[other]!!)
    }

    companion object {
        val valvesByName = mutableMapOf<String, Valve>()
        val distances = mutableMapOf<Pair<String, String>, Int>()
        val viableValves = mutableListOf<Valve>()

        private fun nextMoves(name: String): List<Pair<String, Int>> {
            return valvesByName[name]!!.exitNames.map { it to 1 }
        }

        private fun parseLine(line: String): Valve {
            val details = line.split(";")
            assert(details.size == 2)
            val name = details[0].substring(6, 8)
            val rate = Utils.extractUInts(details[0])[0]
            val exitList = details[1].substring(23)
            val exitNames = exitList.split(",").map(String::trim).toList()
            val valve = Valve(name, rate, exitNames)
            valvesByName[name] = valve
            return valve
        }

        fun parse(data: String): Valve {
            reset()
            for (line in data.lines()) {
                parseLine(line)
            }
            return resolve()
        }

        fun reset() {
            valvesByName.clear()
            viableValves.clear()
            distances.clear()
        }

        private fun resolve(): Valve {
            // Now calculate distances between all the "viable" valves
            viableValves.addAll(valvesByName.values.filter { it.rate > 0 })
            val root = valvesByName["AA"]!!
            for (v in viableValves) {
                for (v2 in viableValves) {
                    println("Distance from ${v.id} to ${v2.id} is ${v.distanceTo(v2)}")
                }
                // also do root
                root.distanceTo(v)
            }
            distances.forEach(::println)
            return root
        }
    }

    override fun toString(): String {
        return "Valve(id='$id', rate=$rate, exitNames=$exitNames)"
    }
}

class Day16KtTest {

    private val testData = """Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @BeforeEach
    fun setUp() {
        State2.maxChild = 0
        Valve.reset()
    }

    @Test
    fun testPart1() {
        assertEquals(1651, this.part1(testData))
    }

    @Test
    fun testPart1real() {
        assertEquals(1647, this.part1(realData))
    }

    private fun part1(data: String): Int {
        val root = Valve.parse(data)
        return solve(root, 1, 30)
    }

    @Test
    fun testPart2() {
        assertEquals(1707, this.part2(testData))
    }

    @Test
    fun testPart2real() {
        assertEquals(2169, this.part2(realData))
    }

    private fun part2(data: String): Int {
        val root = Valve.parse(data)
        return solve(root, 2, 26)
    }

    private fun solve(root: Valve, actorCount: Int, maxTime: Int): Int {
        val positions = List(actorCount) { root.id }.toTypedArray()
        val times = List(actorCount) { 0 }.toTypedArray()
        return State2(positions, times, 0, Valve.viableValves.map { it.id }.toSet()).maxScore(maxTime, moves = listOf())
    }

    data class State2(val positions: Array<String>, val times: Array<Int>, val score: Int, val closed: Set<String>) {

        private fun maxPossible(targetTime: Int, valves: Map<String, Valve>): Int {

            if (times.all { it >= targetTime } || closed.isEmpty()) {
                return 0
            }
            // assume best case, that we can get from each valve in 1 step, whats the max possible remaining score
            // we can use this to prune
            val rates = closed.map { valves[it]!!.rate }.sortedDescending().toMutableList()
            val remaining = times.map { targetTime - it }.toIntArray()
            var result = 0

            while (remaining.any { it > 0 } && rates.isNotEmpty()) {
                // take the next item with the longest time
                val timeIndex = remaining.indices.maxBy { remaining[it] }
                // move earliest time
                result += (remaining[timeIndex] - 1) * rates.removeFirst()
                // increment earliest time
                remaining[timeIndex] -= 1
            }
            return result
        }

        fun maxScore(targetTime: Int, level: Int = 0, moves: List<Pair<Int, String>>): Int {
            if (times.all { it >= targetTime } || closed.isEmpty()) {
                if (score > maxChild) {
                    println("New high score")
                    println("Moves:")
                    moves.forEach { println("Time ${it.first} Move: ${it.second}") }
                }
                return score
            }

            // perhaps prune
            val maxPossible = maxPossible(targetTime, Valve.valvesByName)
            if (score + maxPossible < maxChild) {
                return maxChild
            }

            // Decide who to move - always move the earliest one first
            val timeIndex = times.indices.minBy { times[it] }
            // Options are:
            // if current is closed, we should open
            // or we can move to one of the other closed valves to open that
            val currentValve = Valve.valvesByName[positions[timeIndex]]!!
            if (positions[timeIndex] in closed) {
                // open it, and execute child
                val nextTimes = times.copyOf()
                nextTimes[timeIndex] += 1
                val result = State2(
                    positions,
                    nextTimes,
                    score + (currentValve.rate * (targetTime - nextTimes[timeIndex])),
                    closed.minus(positions[timeIndex])
                ).maxScore(
                    targetTime,
                    level + 1,
                    moves.plus(times[timeIndex] to "$timeIndex Open valve ${positions[timeIndex]}")
                )
                if (result > maxChild) {
                    println("New max: $result vs $maxChild")
                    maxChild = result
                }
            } else {
                // Now move instead
                for (c in closed) {
                    // don't move to self
                    if (positions[timeIndex] == c) {
                        continue
                    }
                    // What time will it be when we get there
                    val nextTimes = times.copyOf()
                    nextTimes[timeIndex] += currentValve.distanceTo(c)
                    val nextPositions = positions.copyOf()
                    nextPositions[timeIndex] = c
                    val result = State2(nextPositions, nextTimes, score, closed).maxScore(
                        targetTime, level + 1, moves.plus(times[timeIndex] to "$timeIndex Move to valve $c")
                    )
                    if (result > maxChild) {
                        println("$level New max: $result vs $maxChild")
                        maxChild = result
                    }
                }
            }
            return maxChild
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as State2

            if (!positions.contentEquals(other.positions)) return false
            if (!times.contentEquals(other.times)) return false
            if (score != other.score) return false
            if (closed != other.closed) return false

            return true
        }

        override fun hashCode(): Int {
            var result = positions.contentHashCode()
            result = 31 * result + times.contentHashCode()
            result = 31 * result + score
            result = 31 * result + closed.hashCode()
            return result
        }

        companion object {
            var maxChild = 0
        }
    }
}