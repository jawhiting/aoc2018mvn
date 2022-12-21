package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day21KtTest {

    private val testData =
        """root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    private val calcs = mutableMapOf<String, String>()
    private val calcCache = mutableMapOf<String, Double>()

    fun parse(data: String) {
        calcs.putAll(data.lines().map { it.split(":") }.map { it[0] to it[1].trim() }.toMap())
    }

    private fun calculate(key: String): Double {
        val cached = calcCache[key]
        if (cached != null) {
            return cached
        } else {
            val calculation = calcs[key]!!
            println("Calculating value for $key from value $calculation")
            if (calculation.first().isDigit()) {
                val result = calculation.toDouble()
                calcCache[key] = result
                return result
            }
            val key1 = calculation.substring(0..3)
            val operation = calculation.substring(5..5)
            val key2 = calculation.substring(7)
            val val1 = calculate(key1)
            val val2 = calculate(key2)
            val result: Double = when (operation) {
                "+" -> {
                    val1 + val2
                }

                "-" -> {
                    val1 - val2
                }

                "*" -> {
                    val1 * val2
                }

                "/" -> {
                    val1 / val2
                }

                else -> {
                    throw RuntimeException("Invalid operation $operation")
                }
            }
            calcCache[key] = result
            return result
        }
    }

    @Test
    fun testPart1() {
        assertEquals(152, part1(testData))
    }

    @Test
    fun testPart1Real() {
        // 4495 too high
        //1536 too low
        assertEquals(268597611536314, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(301, part2(testData))
    }

    @Test
    fun testPart2real() {
        // 2697722344572 too low
        // 3451534022349 too high
        assertEquals(3451534022348, part2(realData))
    }


    fun part1(data: String): Long {
        parse(data)
        return calculate("root").toLong()
    }


    fun part2(data: String): Long {
        parse(data)
        // two sides of root
        val rootParts = calcs["root"]!!.split("+")
        val key1 = rootParts[0].trim()
        val key2 = rootParts[1].trim()

        val result1 = calculate2(key1, 0L.toDouble())
        val result2 = calculate2(key2, 0L.toDouble())
        // Determine which one is static, which one dynamic
        val staticResult: Double
        val dynamicKey: String
        if (result1.second) {
            staticResult = result2.first
            dynamicKey = key1
        } else {
            staticResult = result1.first
            dynamicKey = key2
        }

        return solve(dynamicKey, staticResult)
    }

    private fun solve(key: String, target: Double): Long {
        var hMin = Long.MIN_VALUE
        var hMax = Long.MAX_VALUE
        var hMid: Long
        while (true) {
            hMid = (hMin + hMax) / 2L
            val rMin = calculate2(key, hMin.toDouble()).first
            val rMid = calculate2(key, hMid.toDouble()).first
            val rMax = calculate2(key, hMax.toDouble()).first

            println("Trying $hMin, $hMid, $hMax gives $rMin, $rMid, $rMax Target $target")
            if (target == rMid) {
                println("Found target with $hMid")
                break
            }
            // Which side is target
            if (target in rMin..rMid || target in rMid..rMin) {
                hMax = hMid - 1
            } else {
                hMin = hMid + 1
            }
        }
        return hMid
    }

    fun calculate2(key: String, humanInput: Double): Pair<Double, Boolean> {
        val cached = calcCache[key]
        if (cached != null) {
            return cached to false
        } else if (key == "humn") {
            return humanInput to true
        } else {
            val calculation = calcs[key]!!
            if (calculation.first().isDigit()) {
                val result = calculation.toDouble()
                calcCache[key] = result
                return result to false
            }
            val key1 = calculation.substring(0..3)
            val operation = calculation.substring(5..5)
            val key2 = calculation.substring(7)
            val val1 = calculate2(key1, humanInput)
            val val2 = calculate2(key2, humanInput)
            val result: Double = when (operation) {
                "+" -> {
                    val1.first + val2.first
                }

                "-" -> {
                    val1.first - val2.first
                }

                "*" -> {
                    val1.first * val2.first
                }

                "/" -> {
                    val1.first / val2.first
                }

                else -> {
                    throw RuntimeException("Invalid operation $operation")
                }
            }
            // Are we derived from human
            return if (val1.second || val2.second) {
                // don't cache it
                result to true

            } else {
                // not from human
                calcCache[key] = result
                result to false
            }
        }
    }
}