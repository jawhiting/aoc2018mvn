package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import java.util.TreeMap


class Day9KtTest {

    private val testData = """2333133121414131402"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(1928L, this.part1(testData))
        assertEquals(6323641412437L, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(2858, this.part2(testData))
        // Too high 8505770332595
        assertEquals(6351801932670, this.part2(realData))
    }

    private fun part1(data: String): Long {
        val expanded = expand(data)
        println(expanded.joinToString(""))
        compact(expanded)
        println(expanded.joinToString(""))
        return score(expanded)
    }

    private fun part2(data: String): Long {
        val fe = expand2(data)
        val files = fe.first
        val empties = fe.second

        val filesById = files.associateBy { it.id }
        val emptyByPos = TreeMap(empties.associateBy { it.pos })

        // Now starting from the last file
        val fileIds = filesById.keys.toList().sortedDescending()
        for(id in fileIds) {
            val f = filesById[id]!!
            // Find the first empty block with enough space
            val targetEmpty = emptyByPos.values.firstOrNull { it.pos < f.pos && it.length >= f.length }
            if(targetEmpty != null) {
                // Now move the file to the target
                f.pos = targetEmpty.pos
                // This will remove/decrease the existing empty and create a new empty where the
                // original file was, which we may need to merge (actually can ignore, since we're iterating the files reverse
                // Adjust the gap
                emptyByPos.remove(targetEmpty.pos)
                val remaining = targetEmpty.length - f.length
                if (remaining > 0) {
                    // Create a new empty
                    val remainingEmpty = Empty(targetEmpty.pos + f.length, remaining)
                    emptyByPos.put(remainingEmpty.pos, remainingEmpty)
                }
            }
        }
//        log(filesById.values.toList())
//        println("here")
        return filesById.values.sumOf {it.score()}
    }

    private fun log(files: List<File>) {
        val sorted = files.sortedBy { it.pos }
        val output = arrayOfNulls<Char?>(sorted.last().pos+sorted.last().length)
        for(f in sorted) {
            val pos = f.pos
            for(i in 0..f.length-1) {
                output[pos+i] = f.id.digitToChar()
            }
        }
        for(i in output.indices) {
            if(output[i] == null) {
                output[i] = '.'
            }
        }
        println("00992111777.44.333....5555.6666.....8888..")
        println(output.joinToString(""))
    }

    private fun expand(data: String): ArrayList<Int?> {
        val result = arrayListOf<Int?>()
        var fileId = 0
        data.map { it.digitToInt() }.chunked(2).forEach {
            val blocks = it[0]
            val free = if(it.size > 1) it[1] else 0
            for(i in 0..blocks-1) {
                try {
                    result.add(fileId)
                } catch (e: IllegalArgumentException) {
                    println("")
                }
            }
            for(i in 0..free-1) {
                result.add(null)
            }
            ++fileId
        }
        // 00...111...2...333.44.5555.6666.777.888899
        return result
    }

    data class File(val id: Int, val length: Int, var pos: Int) {
        fun score() : Long {
            var result = 0L
            for(i in 0..length-1) {
                result += id * (pos+i)
            }
            return result
        }
    }
    data class Empty(val pos: Int, val length: Int)

    private fun expand2(data: String) : Pair<List<File>, List<Empty>> {
        val files = arrayListOf<File>()
        val empties = arrayListOf<Empty>()
        var fileId = 0
        var pos = 0
        data.map { it.digitToInt() }.chunked(2).forEach {
            val blocks = it[0]
            files.add( File(fileId++, blocks, pos))
            pos += blocks
            if(it.size > 1 && it[1] > 0) {
                empties.add(Empty(pos, it[1]))
                pos += it[1]
            }
        }
        return files to empties
    }

    @Test
    fun testExpand() {
        val expected = "00...111...2...333.44.5555.6666.777.888899"
        val input = testData
        val r = expand(input)
        assertEquals(expected, r.joinToString(""))

        // Now compact it
        compact(r)
        val expectedCompact = "0099811188827773336446555566.............."
        assertEquals(expectedCompact, r.joinToString(""))
    }

    fun compact(data: ArrayList<Int?>) {
        var i = 0
        var j = data.lastIndex
        while(i < j) {
            if(data[i] != null) {
                ++i
            }
            else if(data[j] == null) {
                --j
            }
            else {
                data[i] = data[j]
                data[j] = null
            }
        }
    }

    fun score(data: ArrayList<Int?>) : Long {
        return data.withIndex().sumOf {
            if (it.value == null) {
                0L
            } else {
                it.value!! * it.index.toLong()
            }
        }
    }
}