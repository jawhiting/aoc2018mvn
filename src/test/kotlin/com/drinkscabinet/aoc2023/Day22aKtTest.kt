package com.drinkscabinet.aoc2023

import com.drinkscabinet.Coord3
import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.max
import kotlin.math.min

class Day22aKtTest {
    private val testData = """1,0,1~1,2,1
0,0,2~2,0,2
0,2,3~2,2,3
0,0,4~0,2,4
2,0,5~2,2,5
0,1,6~2,1,6
1,1,8~1,1,9"""

    private val realData = Utils.input(this)

    @Test
    fun testPart1() {
        assertEquals(5, solvePart1(testData))
        // 451 too high, 450 too high
        assertEquals(434, solvePart1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(7, solvePart2(testData))
        assertEquals(61209, solvePart2(realData))
    }
    fun solvePart1(input: String): Any {
        val bricks = settleBricks(parseBricks(input))
        val (supports, supportedBy) = calculateSupports(bricks)
        return bricks.indices.count { i -> supports[i]?.all { j -> (supportedBy[j]?.size ?: 3) >= 2 } ?: true }
    }

    fun solvePart2(input: String): Any {
        val bricks = settleBricks(parseBricks(input))
        val (supports, supportedBy) = calculateSupports(bricks)
        return bricks.indices.sumOf { i -> findFalling(supports, supportedBy, i).size - 1 }
    }

    private fun parseBricks(input: String): List<Pair<Coord3, Coord3>> =
        input.lines().map {
            val (a, b) = it.split("~")
            Coord3.of(a) to Coord3.of(b)
        }.sortedBy { it.first.z }

    private fun settleBricks(bricks: List<Pair<Coord3, Coord3>>): List<Pair<Coord3, Coord3>> {
        val settledBricks = bricks.toMutableList()
        var changed: Boolean
        do {
            changed = false
            settledBricks.indices.forEach {
                val brick = settledBricks[it]
                if (settledBricks.none { max(brick.first.x, it.first.x) <= min(brick.second.x, it.second.x) && max(brick.first.y, it.first.y) <= min(brick.second.y, it.second.y) && brick.first.z == it.second.z + 1 } && brick.first.z != 1L) {
                    settledBricks[it] = brick.first.down to brick.second.down
                    changed = true
                }
            }
        } while (changed)
        return settledBricks
    }

    private fun calculateSupports(bricks: List<Pair<Coord3, Coord3>>): Pair<Map<Int, Set<Int>>, Map<Int, Set<Int>>> {
        val supports = hashMapOf<Int, HashSet<Int>>()
        val supportedBy = hashMapOf<Int, HashSet<Int>>()
        bricks.indices.forEach { j ->
            bricks.take(j).indices.forEach { i ->
                if ((bricks[i].first.x..bricks[i].second.x).intersect(bricks[j].first.x..bricks[j].second.x).isNotEmpty() &&
                    (bricks[i].first.y..bricks[i].second.y).intersect(bricks[j].first.y..bricks[j].second.y).isNotEmpty() &&
                    bricks[j].first.z == bricks[i].second.z + 1) {
                    supports.computeIfAbsent(i) { hashSetOf() }.add(j)
                    supportedBy.computeIfAbsent(j) { hashSetOf() }.add(i)
                }
            }
        }
        return supports to supportedBy
    }

    private fun findFalling(supports: Map<Int, Set<Int>>, supportedBy: Map<Int, Set<Int>>, index: Int): Set<Int> {
        val queue = ArrayDeque(supports[index]?.filter { j -> supportedBy[j]?.size == 1 } ?: listOf())
        val falling = mutableSetOf(index).apply { addAll(queue) }
        while (queue.isNotEmpty()) {
            val j = queue.removeFirst()
            supports[j]?.filterNot { it in falling }?.forEach { k ->
                if (supportedBy[k]?.all { it in falling } == true) {
                    queue.addLast(k)
                    falling.add(k)
                }
            }
        }
        return falling
    }
}