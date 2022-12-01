package com.drinkscabinet

class Utils {

    companion object {
        fun extractInts(s: String): IntArray {
            return "(-?\\d+)".toRegex().findAll(s).asIterable().map { it.value.toInt() }.toIntArray()
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