package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import com.drinkscabinet.chunks
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.min


data class IntOrList(val int: Int?, val list: List<IntOrList>?) : Comparable<IntOrList> {


    val isList: Boolean
        get() = list != null

    val isInt: Boolean
        get() = !isList


    companion object {
        fun parse(line: String): IntOrList {
            val stack = ArrayDeque<Any>()
            var acc = ""
            for (c in line.toCharArray()) {
                when (c) {
                    '[' -> {
                        stack.addFirst("[")
                    }

                    ']' -> {
                        // clear acc
                        if (acc != "") {
                            stack.addFirst(IntOrList(acc.toInt(), null))
                            acc = ""
                        }
                        // Pop items until we get to the start of the list
                        val items = ArrayDeque<IntOrList>()
                        while (stack.first() is IntOrList) {
                            items.addFirst(stack.removeFirst() as IntOrList)
                        }
                        assert(stack.first() == "[")
                        // replace with a list object
                        stack.removeFirst()
                        stack.addFirst(IntOrList(null, items))
                    }

                    ',' -> {
                        if (acc != "") {
                            stack.addFirst(IntOrList(acc.toInt(), null))
                            acc = ""
                        }
                    }

                    else -> {
                        acc += c
                    }
                }
            }

            // Now unpack the stack
            assert(stack.size == 1)
            return stack.first() as IntOrList
        }
    }

    override fun compareTo(other: IntOrList): Int {
        // both integers
        if (this.isInt && other.isInt) {
            return compareValues(this.int, other.int)
        }
        if (this.isList && other.isList) {
            return compareLists(this.list!!, other.list!!)
        }
        return if (this.isInt) {
            compareLists(listOf(this), other.list!!)
        } else {
            compareLists(this.list!!, listOf(other))
        }
    }

    private fun compareLists(a: List<IntOrList>, b: List<IntOrList>): Int {
        for (i in 0 until min(a.size, b.size)) {
            val comparison = a[i].compareTo(b[i])
            if (comparison != 0) {
                return comparison
            }
        }
        // all elements in common subset are equal
        if (a.size > b.size) {
            // first list is longer
            return 1
        }
        if (a.size < b.size) {
            // second list is longer
            return -1
        }
        // equal values, equal length
        return 0
    }

    override fun toString(): String {
        if (isList) {
            return "$list".replace(" ", "")
        }
        return "$int"
    }
}

class Day13KtTest {

    private val testData = """[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testParse() {
        val toTest = listOf<String>("[[1],[2,3,4]]", "[1,[2,[3,[4,[5,6,7]]]],8,9]")
        for (v in toTest) {
            val result = IntOrList.parse(v)
            assertEquals(v, result.toString())
        }
    }

    @Test
    fun testCompare() {
        val a = IntOrList.parse("[1,1,3,1,1]")
        val b = IntOrList.parse("[1,1,5,1,1]")
        assertTrue(a < b)
        assertFalse(a > b)
    }

    @Test
    fun testCompare2() {
        val a = IntOrList.parse("[[1],[2,3,4]]")
        val b = IntOrList.parse("[[1],4]")
        assertTrue(a < b)
        assertFalse(a > b)
    }

    @Test
    fun testPart1() {
        assertEquals(13, this.part1(testData))
        assertEquals(6272, this.part1(realData))

    }

    @Test
    fun testPart2() {
        assertEquals(140, this.part2(testData))
        assertEquals(22288, this.part2(realData))
    }


    private fun part1(data: String): Int {
        val pairs = data.chunks().map(String::lines).map { IntOrList.parse(it[0]) to IntOrList.parse(it[1]) }.toList()
        // Now check the list for which ones are in the right order
        return pairs.mapIndexed { i, p -> i + 1 to p }.filter { it.second.first < it.second.second }.sumOf { it.first }
    }

    private fun part2(data: String): Int {
        val items = data.lines().filter{it.isNotEmpty()}.map( IntOrList::parse).toMutableList()
        val dividers = setOf("[[2]]", "[[6]]")
        dividers.forEach { items.add(IntOrList.parse(it)) }
        items.sort()
        return items.map(IntOrList::toString).foldIndexed(1) { i, r, t ->
            if (dividers.contains(t)) {
                r * (i + 1)
            } else {
                r
            }
        }
    }

}