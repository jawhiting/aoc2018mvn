package com.drinkscabinet

class Utils {

    companion object {
        fun extractInts(s: String): IntArray {
            return "(-?\\d+)".toRegex().findAll(s).asIterable().map { it.value.toInt() }.toIntArray()
        }

        fun extractLongs(s: String): LongArray {
            return "(-?\\d+)".toRegex().findAll(s).asIterable().map { it.value.toLong() }.toLongArray()
        }

        fun chunks(s: String): List<String> {
            return s.split("\n\n")
        }
    }
}

fun String.chunks() : List<String> {
    return this.split("\n\n")
}