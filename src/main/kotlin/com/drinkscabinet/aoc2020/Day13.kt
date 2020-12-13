package com.drinkscabinet.aoc2020

import com.drinkscabinet.Utils


private fun main() {
    println(nextAfter(939, 59))
    println(nextAfter(939, 7))

    // Part1
    val time = input.lines().first().toLong()
    val buses = Utils.extractLongs(input.lines()[1])

    for (bus in buses) {
        val next = nextAfter(time, bus)
        println("Bus $bus next leaves at $next")
    }

    // next departure
    val min = buses.minByOrNull { nextAfter(time, it) }!!
    println(min)
    val minDepart = nextAfter(time, min)
    println(minDepart)
    println((minDepart-time) * min)

    part2a("17,x,13,19")
    part2a("67,7,59,61")
    part2a("67,x,7,59,61")
    part2a("67,7,x,59,61")
    part2a("1789,37,47,1889")
//    val startingPoint = 100_000_000_000_000
    part2a(input2)
}

private fun nextAfter(time: Long, period: Long) : Long {
    val mod = time % period
    if( mod == 0L ) return time
    return time + (period-mod)
}

private fun part2a(s: String, tStart : Long = 1) {
    // unpack into list of pairs of value to position
    val items = s.split(",")
    val buses = mutableListOf<Pair<Long, Long>>()
    for( i in items.indices) {
        if( items[i] == "x" ) continue
        val id = items[i].toLong()
        var time = i.toLong()
        if( time > id ) time %= id
        buses.add(id to (id-time)%id)
    }
    println(buses)
    val busesToMatch = buses.map{ it.first }.toMutableSet()
    var t = 1
    // find max pair
    val maxPair = buses.maxByOrNull { it.first }!!
    val firstPair = buses.minByOrNull { it.second }!!

    var increment = firstPair.first
    busesToMatch.remove(firstPair.first)

    var current = 0L

    while( busesToMatch.isNotEmpty() ) {

        current += increment
        // See if any match - if so add them to the increment
        for (bus in buses) {
            if( current % bus.first == bus.second) {
                // Found a matching one - increase the increment if we haven't already
                if( busesToMatch.contains(bus.first)) {
                    increment *= bus.first
                    busesToMatch.remove(bus.first)
                    println("Found match for $bus increment is now $increment")
                }
            }
        }
    }
    println("Matched all: $current")
}

private fun cycleLength() {
    // Find the first to lock in, then x * that, look for the next cycle
}

private data class Tester(val buses: List<Pair<Long, Long>>) {
    val maxPair = buses.maxByOrNull { it.first }!!
    val firstPair = buses.first()

    inline fun matches(toTest: Long) : Boolean {
        return buses.none { toTest % it.first != it.second }
    }
}

private val testInput = """
    939
    7,13,x,x,59,x,31,19
""".trimIndent()

private val testInput2 = """    
    7,13,x,x,59,x,31,19
""".trimIndent()

private val input = """
    1005162
    19,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,823,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,29,x,443,x,x,x,x,x,37,x,x,x,x,x,x,13
""".trimIndent()

private val input2 = """    
    19,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,823,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,29,x,443,x,x,x,x,x,37,x,x,x,x,x,x,13
""".trimIndent()