package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day25KtTest {

    private val testData = """jqt: rhn xhk nvd
rsh: frs pzl lsr
xhk: hfx
cmg: qnr nvd lhk bvb
rhn: xhk bvb hfx
bvb: xhk hfx
pzl: lsr hfx nvd
qnr: nvd
ntq: jqt hfx bvb xhk
nvd: lhk
lsr: lhk
rzs: qnr cmg lsr rsh
frs: qnr lhk lsr"""

    private val realData = Utils.input(this)

    data class Component(val id: String, val connections: MutableSet<String>) {
        fun toGraphviz(): String {
            return connections.joinToString("\n") { "$id -- $it [label=\"${connectionLabel(id, it)}\"];" }
        }

        fun connectionLabel(c1: String, c2: String) : String {

            return listOf(c1, c2).sorted().joinToString("_")
        }
    }

    fun parse(data: String) : Map<String, Component> {
        val components = mutableMapOf<String, Component>()
        for(line in data.lines()) {
            val (id, connections) = line.split(": ")
            components[id] = Component(id, connections.split(" ").toMutableSet())
        }
        return components
    }

    fun draw(components: Map<String, Component>) {
        println("@startdot")
        println("strict graph G {")
        println(components.values.joinToString("\n") { it.toGraphviz() })
        println("}")
        println("@enddot")
    }

    fun sever(components: MutableMap<String, Component>, id1: String, id2: String) : MutableMap<String, Component> {
        components[id1]?.connections?.remove(id2)
        components[id2]?.connections?.remove(id1)
        return components
    }
    @Test
    fun testPart1() {
        assertEquals(1, part1())
    }

    fun part1() : Int {
        val components = parse(realData).toMutableMap()
        doubleLink(components)
        sever(components, "pzv", "xft")
        sever(components, "hbr", "sds")
        sever(components, "dqf", "cbx")
        draw(components)
        // pzv to xft
        // hbr to sbs
        // dqf to cbx

        return countConnected("pzv", components) * countConnected("xft", components)
    }

    fun doubleLink(components: MutableMap<String, Component>) {
        for(c in components.values.toList()) {
            for(connection in c.connections) {
                // Ensure a component exists
                components.getOrPut(connection) { Component(connection, mutableSetOf()) }.connections.add(c.id)
            }
        }
    }

    fun countConnected(id: String, components: Map<String, Component>): Int {
        val toVisit = mutableListOf(id)
        val visited = mutableSetOf<String>()

        while(toVisit.isNotEmpty()) {
            val next = toVisit.removeFirst()
            visited.add(next)
            toVisit.addAll(components[next]?.connections?.filter { !visited.contains(it) } ?: listOf())
        }
        return visited.size
    }
}