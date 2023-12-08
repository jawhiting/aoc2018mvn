package com.drinkscabinet

import com.github.jsonldjava.shaded.com.google.common.math.LongMath

class Utils {

    companion object {
        fun extractInts(s: String): IntArray {
            return "(-?\\d+)".toRegex().findAll(s).asIterable().map { it.value.toInt() }.toIntArray()
        }

        fun extractUInts(s: String): IntArray {
            return "(\\d+)".toRegex().findAll(s).asIterable().map { it.value.toInt() }.toIntArray()
        }

        fun extractLongs(s: String): LongArray {
            return "(-?\\d+)".toRegex().findAll(s).asIterable().map { it.value.toLong() }.toLongArray()
        }

        fun input(year: Int, day: Int): String {
            return {}.javaClass.getResource("/$year/day$day.txt")?.readText()!!
        }

        fun input(aoc: Any): String {
            return input(getYear(aoc), getDay(aoc))
        }

        fun getYear(aoc: Any): Int {
            return extractUInts(aoc.javaClass.packageName)[0]
        }

        fun getDay(aoc: Any): Int {
            return extractUInts(aoc.javaClass.simpleName)[0]
        }

        fun lcm(a: Long, b: Long): Long {
            var x = a
            while (x % b != 0L) {
                x += a
            }
            return x
        }

        fun lcmList(numbers: List<Long>): Long {
            var lcm = numbers[0]
            for (i in 1 until numbers.size) {
                lcm = lcm * numbers[i] / LongMath.gcd(lcm, numbers[i])
            }
            return lcm
        }
    }
}

fun String.chunks(): List<String> {
    return if (this.contains("\n\n")) this.split("\n\n")
    else this.split("\r\n\r\n")
}

fun IntRange.contains(other: IntRange): Boolean {
    return (this.first <= other.first) && (this.last >= other.last)
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return this.contains(other) or other.contains(this) or this.contains(other.first) or this.contains(other.last)
}