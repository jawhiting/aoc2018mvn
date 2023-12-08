package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.coroutines.coroutineContext


class Day5KtTest {

    data class RangeMap(val destination: Long, val start: Long, val length: Long) {

        operator fun contains(value: Long): Boolean {
            return value in start..<start + length
        }
        fun map(value: Long): Long {
            if (value in start..<start + length) {
                return value + (destination - start)
            }
            return destination
        }
    }

    data class FromToMap(val id: String, val ranges: List<RangeMap>) {


        fun map(seed: Long) : Long {
            val r = ranges.firstOrNull { seed in it }
            if(r != null) {
                return r.map(seed)
            }
            return seed
        }
        companion object {

            fun seeds(input: String): LongArray {
                return Utils.extractLongs(input.lines().first { it.startsWith("seeds:") })
            }

            fun parse(input: String): List<FromToMap> {
                var mapId = ""
                var rangeMaps = mutableListOf<RangeMap>()
                val maps = mutableListOf<FromToMap>()
                for (line in input.lines()) {
                    if (line.isBlank() || line.startsWith("seeds:")) {
                        continue
                    }
                    if (line.contains("map")) {
                        // if we're already doing one
                        if (mapId.isNotBlank()) {
                            maps.add(FromToMap(mapId, rangeMaps))
                            rangeMaps = mutableListOf()
                        }
                        mapId = line.split(" ")[0]
                        continue
                    }

                    val numbers = Utils.extractLongs(line)
                    rangeMaps.add(RangeMap(numbers[0], numbers[1], numbers[2]))
                }
                // add last one
                maps.add(FromToMap(mapId, rangeMaps))

                return maps
            }
        }
    }

    private val testData = """seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testSeeds() {
        assertEquals(79, FromToMap.seeds(testData)[0])
        assertEquals(14, FromToMap.seeds(testData)[1])
        assertEquals(55, FromToMap.seeds(testData)[2])
        assertEquals(13, FromToMap.seeds(testData)[3])
    }

    @Test
    fun testParse() {
        assertEquals(7, FromToMap.parse(testData).size)
    }

    @Test
    fun testPart1() {
        assertEquals(35, part1(testData))
        assertEquals(382895070, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(46, part2(testData))
        assertEquals(17729182, part2(realData))
    }

    private fun part2(data: String): Long {
        val seeds = FromToMap.seeds(data)
        val maps = FromToMap.parse(data)

        var result = Long.MAX_VALUE
        for(s in seeds.indices step 2) {
            result = minOf(result, convertRange(seeds[s], seeds[s+1], maps))
        }

        return result
    }

    private fun part1(data: String): Long {
        val seeds = FromToMap.seeds(data)
        val maps = FromToMap.parse(data)

        return seeds.map{convert(it, maps)}.min()
    }

    private fun convertRange(start: Long, length: Long, maps: List<FromToMap>) : Long {
        println("Start: $start length: $length")
        val range = start..<start+length
        // use coroutines to convert each value in the range in parallel then find the min
        return range.minOfOrNull { convert(it, maps) }!!
    }
    private fun convert(seed: Long, maps: List<FromToMap>) : Long {
        var result = seed
        for (map in maps) {
            result = map.map(result)
        }
        return result
    }

}