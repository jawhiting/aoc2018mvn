package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day7KtTest {

    private val testData = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""

    private val testData2 = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""

    private val testData3 = """2345A 1
Q2KJJ 13
Q2Q2Q 19
T3T3J 17
T3Q33 11
2345J 3
J345A 2
32T3K 5
T55J5 29
KK677 7
KTJJT 34
QQQJA 31
JJJJJ 37
JAAAA 43
AAAAJ 59
AAAAA 61
2AAAA 23
2JJJJ 53
JJJJ2 41"""

    private val realData = Utils.input(this)

    enum class Card {
        A, K, Q, J, T, C9, C8, C7, C6, C5, C4, C3, C2, j
    }

    enum class Type {
        FIVE, FOUR, FULL, THREE, TWO, ONE, HIGH
    }

    data class Cards(val cards: List<Card>) : Comparable<Cards> {
        override fun compareTo(other: Cards): Int {
            val pair = cards.zip(other.cards).firstOrNull { it.first != it.second }
            if (pair != null) {
                return pair.first compareTo pair.second
            }
            return 0
        }

        fun getType(): Type {
            val indexed = cards.groupingBy { it }.eachCount().toMutableMap()
            val jokers = indexed.getOrDefault(Card.j, 0)
            // now remove jokers from the count
            indexed[Card.j] = 0
            val ofAKind = indexed.values.groupingBy { it }.eachCount()

            if (ofAKind.containsKey(5)) {
                return Type.FIVE
            }
            if (ofAKind.containsKey(4)) {
                if (jokers == 1)
                    return Type.FIVE
                return Type.FOUR
            }
            if (ofAKind.containsKey(3) && ofAKind.containsKey(2)) {
                return Type.FULL
            }
            if (ofAKind.containsKey(3)) {
                if (jokers == 2)
                    return Type.FIVE
                if (jokers == 1)
                    return Type.FOUR
                return Type.THREE
            }
            if (ofAKind.getOrDefault(2, 0) == 2) {
                if (jokers == 1)
                    return Type.FULL
                return Type.TWO
            }
            if (ofAKind.getOrDefault(2, 0) == 1) {
                if (jokers == 3)
                    return Type.FIVE
                if (jokers == 2)
                    return Type.FOUR
                if (jokers == 1)
                    return Type.THREE
                return Type.ONE
            }
            if (jokers == 5)
                return Type.FIVE
            if (jokers == 4)
                return Type.FIVE
            if (jokers == 3)
                return Type.FOUR
            if (jokers == 2)
                return Type.THREE
            if (jokers == 1)
                return Type.ONE
            return Type.HIGH
        }
    }

    data class Hand(val type: Type, val cards: Cards, val bid: Long) : Comparable<Hand> {

        override fun compareTo(other: Hand): Int {
            if (type != other.type)
                return type compareTo other.type
            return cards compareTo other.cards
        }

        companion object {
            fun parse(data: String): Hand {
                val parts = data.split(" ")
                val bid = parts[1].toLong()
                val cards = parseCards(parts[0])
                return Hand(cards.getType(), cards, bid)
            }

            fun parseCards(s: String, part2: Boolean = false): Cards {
                var s2 = s
                if (part2)
                    s2 = s.replace('J', 'j')
                return Cards(s2.toCharArray().map { parseCard(it) }.toList())
            }

            fun parseCard(c: Char): Card {
                if (c in '0'..'9') {
                    return Card.valueOf("C$c")
                } else return Card.valueOf("$c")
            }
        }


    }

    @Test
    fun testParseCard() {
        assertEquals(Hand.parseCard('A'), Card.A)
        assertEquals(Hand.parseCard('9'), Card.C9)
    }

    @Test
    fun testGetType() {
        assertEquals(Type.FIVE, Hand.parseCards("AAAAA").getType())
        assertEquals(Type.FULL, Hand.parseCards("AAA22").getType())
        assertEquals(Type.HIGH, Hand.parseCards("23456").getType())
    }

    @Test
    fun testCardSort() {
        val unsorted = listOf(Hand.parse("2AAAA 1"), Hand.parse("AAAAA 1"), Hand.parse("33332 1"))
        val sorted = listOf(Hand.parse("AAAAA 1"), Hand.parse("33332 1"), Hand.parse("2AAAA 1"))
        assertEquals(sorted, unsorted.sorted())
    }

    @Test
    fun testJokerType() {
        assertEquals(Type.FOUR, Hand.parseCards("QJJQ2", true).getType())
        assertEquals(Type.FIVE, Hand.parseCards("JJJJJ", true).getType())
        assertEquals(Type.THREE, Hand.parseCards("Q2KJJ", true).getType())

    }

    @Test
    fun testPart1() {
        assertEquals(6440L, part1(testData))
        assertEquals(253638586L, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(5905L, part2(testData))
        assertEquals(6839L, part2(testData3))
        // 253231670 too low
        assertEquals(253253225L, part2(realData))

    }

    fun part1(data: String): Long {
        val hands = data.lines().map { Hand.parse(it) }.toList()
        val sorted = hands.sortedDescending()
        sorted.forEach { println(it) }
        return sorted.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()
    }

    fun part2(data: String): Long {
        val hands = data.lines().map { Hand.parse(it.replace('J', 'j')) }.toList()
        val sorted = hands.sortedDescending()
        sorted.forEach { println(it) }
        return sorted.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()
    }
}