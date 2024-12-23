package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.jgrapht.Graph
import org.jgrapht.alg.clique.PivotBronKerboschCliqueFinder
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day23KtTest {

    private val testData = """kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn"""


    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(7, this.part1(testData))
        // 2462 too high
        assertEquals(1485, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals("co,de,ka,ta", this.part2(testData))
        assertEquals("cc,dz,ea,hj,if,it,kf,qo,sk,ug,ut,uv,wh", this.part2(realData))
    }

    private fun part1(data: String): Int {
        val connections = data.lines().map { it.split("-") }.map { it[0] to it[1] }
        val graph = mutableMapOf<String, MutableSet<String>>()
        for (c in connections) {
            graph.computeIfAbsent(c.first) { mutableSetOf() }.add(c.second)
            graph.computeIfAbsent(c.second) { mutableSetOf() }.add(c.first)
        }

        val visited = mutableSetOf<String>()
        var sets = mutableSetOf<Set<String>>()
        // Now find all the entries for a computer with t in
        for (c in graph.entries) {
            val conn3 = is3connected(c.key, c.value, graph)
            sets.addAll(conn3)
        }

//        sets.forEach { println(it) }
//        println("${sets.size}---")
        // Filter the sets to just ones with a t computer in
        val s2 = sets.filter { it.any { it.startsWith('t') } }
//        s2.forEach { println(it) }
//        println("${s2.size}---")

        return s2.size
    }

    private fun part2(data: String): String {
        val graph = buildGraph(data)

        val cliques = PivotBronKerboschCliqueFinder(graph).toList()

        val maxFound = cliques.maxBy { it.size }
        println("Max found: $maxFound")
        for (c in cliques) {
            println(c)
        }
        return maxFound.sorted().joinToString(",")
    }

    private fun buildGraph(data: String): Graph<String, DefaultEdge> {
        val connections = data.lines().map { it.split("-") }.map { it[0] to it[1] }
        val vertices = connections.flatMap { listOf(it.first, it.second) }.toSet()
        val graph = SimpleGraph<String, DefaultEdge>(DefaultEdge::class.java)
        for (v in vertices) {
            graph.addVertex(v)
        }
        for (c in connections) {
            graph.addEdge(c.first, c.second)
            graph.addEdge(c.second, c.first)
        }
        return graph
    }

    private fun is3connected(key: String, connections: Set<String>, graph: Map<String, Set<String>>) =
        sequence<Set<String>> {
            // does one of these have a common connection with one of the others
            val clist = connections.toList()
            for (i in clist.indices) {
                for (j in i + 1..clist.lastIndex) {
                    if (graph[clist[j]]!!.contains(clist[i])) {
                        yield(sortedSetOf(key, clist[i], clist[j]))
                    }
                }
            }
        }
}