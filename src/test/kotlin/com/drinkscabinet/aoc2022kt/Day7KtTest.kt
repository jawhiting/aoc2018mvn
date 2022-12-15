package com.drinkscabinet.aoc2022kt

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


data class File(val name: String, val size: Int) {
    companion object {
        fun create(line: String): File {
            val parts = line.split(" ")
            assert(parts.size == 2)
            return File(parts[1], parts[0].toInt())
        }
    }
}

data class Dir(
    val name: String,
    val parent: Dir?,
    val dirs: MutableMap<String, Dir> = mutableMapOf(),
    val files: MutableSet<File> = mutableSetOf()
) {
    val size: Int
        get() = dirs.values.sumOf { it.size } + files.sumOf { it.size }

    val path: String
        get() = if (parent == null) {
            name
        } else {
            parent.path + "/" + name
        }

    override fun toString(): String {
        return "Dir(name='$name', parent=${parent?.name}, dirs=$dirs, files=$files, size=$size)"
    }

    fun dirSizes(): Map<String, Int> {
        val result = mutableMapOf<String, Int>()
        result[path] = this.size
        for (d in dirs.values) {
            result.putAll(d.dirSizes())
        }
        return result
    }
}

class Day7KtTest {

    private val testData = """${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k"""

    // Get the year and day from the class
    private val realData = Utils.input(this)

    @Test
    fun testFile() {
        assertEquals(File("test", 1234), File.create("1234 test"))
    }

    @Test
    fun testPart1() {
        assertEquals(95437, part1(testData))
        assertEquals(1432936, part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(24933642, part2(testData))
        assertEquals(272298, part2(realData))

    }

    private fun part1(data: String): Int {
        val root = parse(data)
        println(root)
        root.dirSizes().forEach(::println)
        return root.dirSizes().values.filter { it <= 100000 }.sum()
    }

    private fun part2(data: String): Int {
        val root = parse(data)
        println(root)
        root.dirSizes().forEach(::println)
        val freeSpace = 70000000 - root.size
        print("Free space $freeSpace")
        val target = 30000000 - freeSpace
        print("Target $target")

        return root.dirSizes().values.filter { it >= target }.min()
    }

    private fun parse(data: String): Dir {
        val root = Dir("root", null)
        var current = root
        for (line in data.lines()) {
            if (line.startsWith("$ cd /")) {
                current = root
            } else if (line.startsWith("$ cd ..")) {
                current = current.parent!!
            } else if (line.startsWith("$ cd ")) {
                // change dir
                val target = line.substring(5)
                current = current.dirs[target] ?: throw IllegalStateException("Dir $target not found in $current")
            } else if (line.startsWith("$ ls")) {
                // ignore?
            } else if (line.startsWith("dir ")) {
                val dirname = line.substring(4)
                if (current.dirs.contains(dirname)) {
                    // already exists do nothing
                    println("Dir $dirname already exists in $current")
                } else {
                    current.dirs[dirname] = Dir(dirname, current)
                }
            } else {
                val file = File.create(line)
                if (current.files.contains(file)) {
                    println("File $file already exists in $current")
                } else {
                    current.files.add(file)
                }
            }
        }
        return root
    }
}