package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import com.drinkscabinet.chunks
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


data class Item(var worryLevel: Long)

data class Monkey(
    val id: Int,
    val items: ArrayDeque<Item>,
    val opAdd: Boolean,
    val opParam: Long?,
    val divisible: Long,
    val trueTarget: Int,
    val falseTarget: Int,
    val worryDivide: Long
) {

    var inspected: Long = 0

    fun turn(modulo: Long) {
        for (item in items) {
            ++inspected
            // Inspect the item
            inspect(item)
            // decrease worry
            if (worryDivide == 3L) {
                item.worryLevel /= worryDivide
            } else {
                item.worryLevel %= modulo
            }
            // throw item
            if (item.worryLevel % divisible == 0L) {
                // Throw to true target
                monkeys[trueTarget]!!.items.addLast(item)
            } else {
                monkeys[falseTarget]!!.items.addLast(item)
            }
        }
        // clear the items
        items.clear()
    }

    fun inspect(item: Item) {
        if (opAdd) {
            item.worryLevel = item.worryLevel + (opParam ?: item.worryLevel)
        } else {
            item.worryLevel = item.worryLevel * (opParam ?: item.worryLevel)
        }
        if (item.worryLevel < 0) {
            throw RuntimeException("Overflow")
        }
    }


    companion object {

        val monkeys: MutableMap<Int, Monkey> = mutableMapOf()

        private fun parse(data: String, worryDivide: Long): Monkey {
            val lines = data.lines()
            assert(lines[0].startsWith("Monkey"))
            assert(lines.size >= 6)
            val id = Utils.extractInts(lines[0])[0]
            val items = ArrayDeque(Utils.extractLongs(lines[1]).map { Item(it) }.toList())
            val opAdd = lines[2].contains("+")
            val opParam = Utils.extractLongs(lines[2]).getOrNull(0)
            val divisible = Utils.extractLongs(lines[3])[0]
            val trueTarget = Utils.extractInts(lines[4])[0]
            val falseTarget = Utils.extractInts(lines[5])[0]
            val result = Monkey(id, items, opAdd, opParam, divisible, trueTarget, falseTarget, worryDivide)
            monkeys[id] = result
            return result
        }

        private fun getModulo(): Long {
            return monkeys.values.fold(1) { acc, monkey -> acc * monkey.divisible }
        }

        private fun round(modulo: Long) {
            for (i in 0 until monkeys.size) {
                val monkey = monkeys[i]!!
                monkey.turn(modulo)
            }
        }

        private fun logMonkeys(round: Int) {
            println("== After round $round ==")
            for (i in 0 until monkeys.size) {
                println("Monkey $i inspected items ${monkeys[i]!!.inspected}")
            }
        }

        private fun getResult(): Long {
            val top = monkeys.values.map { it.inspected }.sortedDescending().take(2).toList()
            return top[0] * top[1]
        }

        fun run(data: String, worryDivide: Long, numRounds: Int): Long {
            for (monkeyData in data.chunks()) {
                val monkey = parse(monkeyData, worryDivide)
                print(monkey)
            }
            val modulo = getModulo()

            for (round in 1..numRounds) {
                round(modulo)
                if (round == 1 || round == 20 || round % 1000 == 0) {
                    logMonkeys(round)
                }
            }
            return getResult()
        }
    }
}

class Day11KtTest {

    private val testData = """Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(10605, this.part1(testData))
        assertEquals(95472, this.part1(realData))

    }

    @Test
    fun testPart2() {
        assertEquals(2713310158, this.part2(testData))
        assertEquals(17926061332, this.part2(realData))
    }


    private fun part1(data: String): Long {
        return Monkey.run(data, 3, 20)
    }

    private fun part2(data: String): Long {
        return Monkey.run(data, 1, 10000)
    }

}