package com.drinkscabinet

import com.google.common.math.LongMath

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

        /**
         * Given a range, and a checker function, return the pair where the checker transitions from false to true
         * So, the first element of the pair is the last element that returns false, and the second is the first element that returns true
         */
        fun binarySearch(start: Long, end: Long, checker: (Long) -> Boolean): Pair<Long, Long> {
            assert(start < end) { "Start $start must be less than end $end" }
            assert(checker(start) == false) { "Checker for Start $start must be false" }
            assert(checker(end) == true) { "Checker for end $end must be true" }
            var lower = start
            var upper = end

            var mid = (lower + upper) / 2

            while (mid != lower && mid != upper) {
                val mr = checker(mid)
                if (mr == true) {
                    lower = mid
                } else {
                    upper = mid
                }
                mid = (lower + upper) / 2
            }
            return lower to upper
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