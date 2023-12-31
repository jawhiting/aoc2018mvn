package com.drinkscabinet.aoc2023

import Delta3
import com.drinkscabinet.Coord3
import com.drinkscabinet.Utils
import com.drinkscabinet.Vector3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day24KtTest {

    val testData = """19, 13, 30 @ -2,  1, -2
18, 19, 22 @ -1, -1, -2
20, 25, 34 @ -2, -2, -4
12, 31, 28 @ -1, -2, -1
20, 19, 15 @  1, -5, -3"""

    val realData = Utils.input(this)

    data class Hailstone(val vector: Vector3) {

        fun gradient(): Double {
            val p1 = vector.pos
            val p2 = vector.pos.move(vector.delta)
            return (p2.y - p1.y).toDouble() / (p2.x - p1.x).toDouble()
        }

        fun intercept(): Double {
            // given two points p1 and p2, find the y intercept
            val p1 = vector.pos
            // x was 0 at x / xDelta
            val xIncrements = p1.x.toDouble() / vector.delta.x.toDouble()
            // value of y at this point is current y - delta.y * x0

            val y0 = p1.y.toDouble() - vector.delta.y.toDouble() * xIncrements
            return y0
        }

        fun printFormula(): String {
            return "y = ${gradient()}x + ${intercept()}"
        }

        fun meet2d(other: Hailstone): Pair<Pair<Double, Double>, Pair<Double, Double>> {
            // what point will they meet
            val x = (other.intercept() - intercept()) / (gradient() - other.gradient())
            val y = gradient() * x + intercept()

            // Now calculate what time
            val xDelta = x - vector.pos.x
            val xDeltaOther = x - other.vector.pos.x
            val time = xDelta / vector.delta.x
            val timeOther = xDeltaOther / other.vector.delta.x
            return (x to y) to (time to timeOther)
        }

        fun rebase(h: Hailstone) : Hailstone {
            return Hailstone(vector.rebase(h.vector))
        }

        fun xAtTime(time: Double) : Double {
            return vector.pos.x + vector.delta.x * time
        }

        fun yAtTime(time: Double) : Double {
            return vector.pos.y + vector.delta.y * time
        }

        fun zAtTime(time: Double) : Double {
            return vector.pos.z + vector.delta.z * time
        }
        fun timeAtX0() : Double {
            return vector.pos.x.toDouble() / vector.delta.x
        }

        fun timeAtY0() : Double {
            return vector.pos.y.toDouble() / vector.delta.y
        }

        fun timeAtZ0() : Double {
            return vector.pos.z.toDouble() / vector.delta.z
        }
    }

    fun parse(line: String): Hailstone {
        val nums = Utils.extractLongs(line)
        return Hailstone(Vector3(Coord3(nums[0], nums[1], nums[2]), Coord3(nums[3], nums[4], nums[5])))
    }

    fun parseAll(data: String) = data.lines().map(::parse)

    fun part1(data: String, range: LongRange): Long {
        val hailstones = parseAll(data)
        hailstones.forEach {
            println(it)
            println(it.printFormula())
        }
//        println(hailstones[0].meet2d(hailstones[1]))
        println(hailstones[1].meet2d(hailstones[2]))
        return countIntercepts(hailstones, range)
    }

    fun countIntercepts(hailstones: List<Hailstone>, range: LongRange): Long {
        var count = 0L
        val min = range.first.toDouble()
        val max = range.last.toDouble()
        for (i in 0..<hailstones.lastIndex) {
            for (j in i + 1..hailstones.lastIndex) {
                println("")
                val meetAt = hailstones[i].meet2d(hailstones[j])
                println("Hailstone A ${hailstones[i]}")
                println("Hailstone B ${hailstones[j]}")
                val meetX = meetAt.first.first
                val meetY = meetAt.first.second
                val timeA = meetAt.second.first
                val timeB = meetAt.second.second
                if (meetX.toLong() < min || meetX.toLong() > max || meetY.toLong() < min || meetY.toLong() > max) {
                    println("Paths cross outside the range")
                    continue
                }
                if (timeA < 0 && timeB < 0) {
                    println("Both times are in the past")
                    continue
                }
                if (timeA < 0) {
                    println("Hailstone A is in the past")
                    continue
                }
                if (timeB < 0) {
                    println("Hailstone B is in the past")
                    continue
                }
                println("${hailstones[i]} meets ${hailstones[j]} at $meetAt")
                count++
            }
        }
        return count
    }

    fun part2(data: String): Long {
        val hailstones = parseAll(data)

        // Find the x dimension first
        // pick 3 hailstones
        val toSolve = hailstones.take(3)
        // normalise them
        val rebased = toSolve.map { it.rebase(toSolve.first()) }

        for(xv in -100..100) {
            val rock = Hailstone(Vector3(Coord3(0, 0, 0), Coord3(xv, 0, 0)))
            var hitAll = true
            for(hailstone in rebased) {
                var matched = false
                for(t in -10000..10000) {
                    if(rock.xAtTime(t.toDouble()) == hailstone.xAtTime(t.toDouble())) {
                        println("Match at xv $xv time $t")
                        matched = true
                        break
                    }
                }
                if(!matched) {
//                    println("No match at xv $xv for hailstone $hailstone")
                    hitAll = false
                    break
                }
            }
            if(hitAll) {
                println("Found it at xv $xv")
            }
        }
        for (hailstone in rebased) {
            println("Hailstone ${hailstone}")
            println("Time at X=0 ${hailstone.timeAtX0()}")
            println("Time at Y=0 ${hailstone.timeAtY0()}")
            println("Time at Z=0 ${hailstone.timeAtZ0()}")
        }
        return 0
    }

    @Test
    fun testPart2() {
        assertEquals(0, part2(testData))
    }

    @Test
    fun testPart1() {
        println(Long.MAX_VALUE)
        assertEquals(2, part1(testData, 7L..27L))
        // 10228 too low
        assertEquals(16779, part1(realData, 200000000000000L..400000000000000L))

    }

}