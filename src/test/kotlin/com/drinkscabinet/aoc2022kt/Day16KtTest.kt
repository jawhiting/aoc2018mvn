package com.drinkscabinet.aoc2022kt

import PathFinder
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.max


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
            for (line in data.lines()) {
                parseLine(line)
            }
            return resolve()
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


    @Test
    fun testPart1() {
        assertEquals(1651, this.part1(testData, 30))
        assertEquals(1647, this.part1(realData, 30))

    }

    @Test
    fun testPart2() {
//        assertEquals(56000011, this.part2(testData, 20))
//        assertEquals(13081194638237, this.part2(realData, 4000000))
    }


    private fun part1(data: String, minutes: Int): Int {
        val root = Valve.parse(data)
//        println(Valve.valvesByName)
//        println(root.distanceTo(Valve.valvesByName["AA"]!!))
//        println(root.distanceTo(Valve.valvesByName["BB"]!!))
//        println(root.distanceTo(Valve.valvesByName["JJ"]!!))
        return solve(root, minutes)
    }

    data class State(val pos: String, val score: Int, val time: Int, val closed: Set<String>) {

        private fun maxPossible(targetTime: Int): Int {
            if( time >= targetTime || closed.isEmpty()) {
                return 0
            }
            // assume best case, that we can get from each valve in 1 step, whats the max possible remaining score
            // we can use this to prune
            val rates = closed.map { Valve.valvesByName[it]!!.rate }.sortedDescending().toMutableList()
            var remaining = targetTime - time
            var result = (remaining-1) * rates.removeFirst()
            remaining -= 2
            while( remaining  > 0 && rates.isNotEmpty()) {
                result += remaining * rates.removeFirst()
                remaining -= 2
            }
            return result
        }

        fun maxScore(targetTime: Int, level: Int = 0, moves: List<Pair<Int, String>>): Int {
            val indent = " ".repeat(level)
            if (time >= targetTime || closed.isEmpty()) {
//                println("${indent}End state: $this")
                if( score >= maxChild ) {
                    println("New high score")
                    println("Moves:")
                    moves.forEach { println("Time ${it.first} Move: ${it.second}") }
                }
                return score
            }

            // perhaps prune
            val maxPossible = maxPossible(targetTime)
            if( score + maxPossible < maxChild ) {
//                println("Pruning $this based on max possible score: ${score+maxPossible} vs $maxChild")
                return maxChild
            }
//            println("${indent}Current: $this")
            // Options are:
            // if current is closed, we can open
            // or we can move to one of the other closed valves to open that
            val currentValve = Valve.valvesByName[pos]!!
            if (pos in closed) {
                // open it, and execute child
                val nextTime = time + 1
//                println("${indent}Opening valve $pos")
                val result = State(
                    pos,
                    score + (currentValve.rate * (targetTime - nextTime)),
                    nextTime,
                    closed.minus(pos)
                ).maxScore(targetTime, level + 1, moves.plus(time to "Open valve $pos"))
//                println("${indent}Result $result")
                if( result > maxChild ) {
                    println("${indent}New max: $result vs $maxChild")
                    maxChild = result
                }
            }
            for (c in closed) {
                // don't move to self
                if(pos == c) {
                    continue
                }
                // What time will it be when we get there
                val nextTime = time + currentValve.distanceTo(c)
//                println("${indent}Moving to $c by $nextTime")
                val result = State(c, score, nextTime, closed).maxScore(targetTime, level+1, moves.plus(time to "Move to valve $c"))
//                println("${indent}Result $result vs $maxChild")
                if( result > maxChild ) {
                    println("${level} New max: $result vs $maxChild")
                    maxChild = result
                }
            }
            return maxChild
        }

        companion object {
            var maxChild = 0
        }
    }

    data class State2(val pos1: String, val time1: Int, val pos2: String, val time2: Int, val score: Int, val closed: Set<String>) {

        fun maxPossible(targetTime: Int): Int {
            if( (time1 >= targetTime && time2 >= targetTime) || closed.isEmpty()) {
                return 0
            }
            // assume best case, that we can get from each valve in 1 step, whats the max possible remaining score
            // we can use this to prune
            val rates = closed.map { Valve.valvesByName[it]!!.rate }.sortedDescending().toMutableList()
            val remaining = arrayOf(time1, time2)
            while( (remaining[0] < targetTime || remaining[1] < targetTime) && closed.isNotEmpty()) {
                // take the next item with the longest time
                val timeIndex =
            }

            // my move
            var result = (remaining-1) * rates.removeFirst()
            // elephant move
            if(rates.isNotEmpty() ) {
                result += (remaining-1) * rates.removeFirst()
            }
            remaining -= 2
            while( remaining  > 0 && rates.isNotEmpty()) {
                result += remaining * rates.removeFirst()
                if( rates.isNotEmpty()) {
                    // elephant move
                    result += remaining * rates.removeFirst()
                }
                remaining -= 2
            }
            return result
        }

        fun maxScore(targetTime: Int, level: Int = 0, moves: List<Pair<Int, String>>): Int {
            val indent = " ".repeat(level)
            if (time >= targetTime || closed.isEmpty()) {
//                println("${indent}End state: $this")
                if( score >= maxChild ) {
                    println("New high score")
                    println("Moves:")
                    moves.forEach { println("Time ${it.first} Move: ${it.second}") }
                }
                return score
            }

            // perhaps prune
            val maxPossible = maxPossible(time, targetTime, closed)
            if( score + maxPossible < maxChild ) {
//                println("Pruning $this based on max possible score: ${score+maxPossible} vs $maxChild")
                return maxChild
            }
//            println("${indent}Current: $this")
            // Options are:
            // if current is closed, we can open
            // or we can move to one of the other closed valves to open that
            val currentValve = Valve.valvesByName[pos]!!
            if (pos in closed) {
                // open it, and execute child
                val nextTime = time + 1
//                println("${indent}Opening valve $pos")
                val result = State(
                    pos,
                    score + (currentValve.rate * (targetTime - nextTime)),
                    nextTime,
                    closed.minus(pos)
                ).maxScore(targetTime, level + 1, moves.plus(time to "Open valve $pos"))
//                println("${indent}Result $result")
                if( result > maxChild ) {
                    println("${indent}New max: $result vs $maxChild")
                    maxChild = result
                }
            }
            for (c in closed) {
                // don't move to self
                if(pos == c) {
                    continue
                }
                // What time will it be when we get there
                val nextTime = time + currentValve.distanceTo(c)
//                println("${indent}Moving to $c by $nextTime")
                val result = State(c, score, nextTime, closed).maxScore(targetTime, level+1, moves.plus(time to "Move to valve $c"))
//                println("${indent}Result $result vs $maxChild")
                if( result > maxChild ) {
                    println("${level} New max: $result vs $maxChild")
                    maxChild = result
                }
            }
            return maxChild
        }

        companion object {
            var maxChild = 0
        }
    }


    private fun solve(root: Valve, minutes: Int): Int {
        return State(root.id, 0, 0, Valve.viableValves.map { it.id }.toSet()).maxScore(minutes, moves = listOf())
    }
}