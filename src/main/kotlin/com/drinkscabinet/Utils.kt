package com.drinkscabinet

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
    }
}

fun String.chunks() : List<String> {
    return if (this.contains("\n\n"))
        this.split("\n\n")
    else
        this.split("\r\n\r\n")
}

fun IntRange.contains(other: IntRange) : Boolean {
    return (this.first <= other.first) && (this.last >= other.last)
}

fun IntRange.overlaps(other: IntRange) : Boolean {
    return this.contains(other) or other.contains(this) or this.contains(other.first) or this.contains(other.last)

}