package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day22KtTest {

    private val testData = """1
10
100
2024"""
    private val testData2 = """1
2
3
2024"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(37327623L, this.part1(testData))
        assertEquals(13461553007L, this.part1(realData))
    }

    @Test
    fun testPart2() {
//        assertEquals(23L, this.part2(testData2))
        // SLOOW 50 secs
        assertEquals(1499, this.part2(realData))
    }

    @Test
    fun testGetSecrets() {
        assertEquals(getSecrets(123L, 2000L)[1999], secretN(123L, 2000L))
        // 1: 8685429
        //10: 4700978
        //100: 15273692
        //2024: 8667524
        assertEquals(8685429, getSecrets(1, 2000L)[1999])
        assertEquals(4700978, getSecrets(10, 2000L)[1999])
        assertEquals(15273692, getSecrets(100, 2000L)[1999])
        assertEquals(8667524, getSecrets(2024, 2000L)[1999])
    }

    private fun part1(data: String): Long {
        return data.lines().map { it.toLong() }.sumOf { secretN(it, 2000L) }
    }

    private val comboScore = mutableMapOf<List<Long>, Long>()

    private fun part2(data: String): Long {
        comboScore.clear()
        val buyers = data.lines().map { Buyer(it.toLong(), getSecrets(it.toLong(), 2000L), comboScore) }
        // Now for each possible combination, find the highest sum
//        val combos = buyers.map { it.deltaPrice.keys }.flatten().toSet()
        println("There are")
        return comboScore.values.max()
    }


    private fun nextSecret(secret: Long) : Long {
        var next = secret.mix(secret*64).prune()
        next = next.mix(next/32).prune()
        next = next.mix(next*2048).prune()
        return next
    }

    private fun secretN(secret: Long, n: Long) : Long {
        var next = secret
        for(i in 1..n) {
            next = nextSecret(next)
        }
        return next
    }

    private fun getSecrets(secret: Long, n: Long) : List<Long> {
        val result = mutableListOf<Long>()
        var s = secret
        for(i in 1..n) {
            s = nextSecret(s)
            result.add(s)
        }
        return result
    }


    @Test
    fun testBuyer() {
        val b = Buyer(123L, getSecrets(123L, 10L), comboScore)
        b.deltaPrice.forEach {
            println("${it.key} -> ${it.value}")
        }
    }

    class Buyer(val start : Long, val secrets: List<Long>, val comboScore: MutableMap<List<Long>, Long>) {
        val prices = toPrice(secrets)
        val deltas = toDeltas(prices)
        val deltaPrice = mutableMapOf<List<Long>, Long>()

        init {
            buildPrices()
        }

        fun buildPrices()  {
            for(i in 3 until deltas.size) {
                val ds = deltas.subList(i-3, i+1)
                if(!deltaPrice.containsKey(ds)) {
                    deltaPrice[ds] = prices[i]
                    comboScore[ds] = comboScore.getOrDefault(ds, 0) + prices[i]
                }
            }
        }

        private fun toPrice(secrets: List<Long>) : List<Long> {
            return secrets.map { it % 10 }
        }

        private fun toDeltas(deltas: List<Long>) : List<Long> {
            val result = mutableListOf<Long>()
            result.add(deltas[0] -(start%10) )
            for(i in 1 until deltas.size) {
                result.add(deltas[i] - deltas[i-1])
            }
            return result
        }
    }

    @Test
    fun testNextSecret() {
        assertEquals(15887950L, nextSecret(123L))
        assertEquals(16495136L, secretN(123L, 2))
        assertEquals(5908254L, secretN(123L, 10))
    }

    private fun Long.mix(other: Long) : Long {
        return this xor other
    }

    private fun Long.prune() : Long {
        return this % 16777216L
    }

    @Test
    fun testMixPrune() {
        assertEquals(16113920L, 100000000L.prune())
        assertEquals(37L, 42L.mix(15L))
    }
}