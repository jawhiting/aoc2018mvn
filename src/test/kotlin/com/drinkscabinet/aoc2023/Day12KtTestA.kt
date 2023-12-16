package com.drinkscabinet.aoc2023


import com.drinkscabinet.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

internal class DefaultMap<K, V>(private val defaultValue: (key: K) -> V) : HashMap<K, V>() {
    override fun get(key: K) = super.get(key) ?: defaultValue(key).also { this[key] = it }
}

val cache = ConcurrentHashMap<String, ULong>()

@Suppress("RegExpSimplifiable")
internal val regexInterning = DefaultMap<Int, Regex> { Regex("""[#?]{$it}(\.|\?|$)""") }

internal fun String.matches(at: Int, erasureCode: Int) =
    regexInterning[erasureCode].matches(slice(at..min(at + erasureCode, lastIndex)))

internal fun String.backTrack(at: Int, codesLeft: List<Int>): ULong = when {
    // Accept the branch (+1) only if there is no '#' remaining in the line ahead; otherwise return 0.
    codesLeft.isEmpty() -> if ('#' in substring(min(at, length))) 0UL else 1UL
    lastIndex < at -> 0UL
    else -> "${this.substring(at)}%${codesLeft.joinToString(",")}".let { cacheKey ->
        cache[cacheKey] ?: codesLeft[0].let { code ->
            (at..length - code)
                // Follow a branch only if this is an acceptable starting point and there is no '#' left from the last covered span.
                .filter { '#' !in substring(at, max(at, it)) && '.' != this[it] && matches(it, code) }
                .sumOf { backTrack(it + code + 1, codesLeft.drop(1)) }
        }.also { cache.putIfAbsent(cacheKey, it) }
    }
}

fun main() {
    val input = ArrayList<Pair<String, List<Int>>>()
    File("src/main/resources/2023/day12.txt").useLines { file ->
        file.mapTo(input) {
            it.split(' ')
                // Part II
                .let { (line, codes) ->
                    Pair(
                        List(5) { _ -> line }.joinToString("?"),
                        List(5) { _ -> codes }.joinToString(",")
                    )
                }
                .let { (line, codes) ->
                    Pair(line, codes.split(',').map(String::toInt))
                }
        }
    }

    var result: ULong
    measureTimeMillis {
        result = runBlocking(Dispatchers.Default) {
            input.indices.map {
                async { input[it].let { (line, codes) -> line.backTrack(0, codes) } }
            }.awaitAll().sum()
        }
    }.let { println("Co [$it]: $result") }

    // Invalidate cache.
    cache.clear()

    measureTimeMillis {
        result = input.sumOf { (line, codes) -> line.backTrack(0, codes) }
    }.let { println("Seq [$it]: $result") }
}