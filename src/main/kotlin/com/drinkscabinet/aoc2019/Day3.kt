package com.drinkscabinet.aoc2019

import UpDown
import com.drinkscabinet.Coord
import java.lang.Math.abs

class Wire(val path: List<Coord>) {

    companion object {
        fun parse(s: String): Wire {
            val dirs = s.split(",")
            var current = Coord(0, 0)
            val visited = mutableListOf<Coord>()
            dirs.forEach {
                val delta = UpDown.valueOf(it[0].toString())
                val count = (it[1] - '0').toInt()
                for (i in 1..count) {
                    current = current.move(delta)
                    visited.add(current)
                }
            }
            return Wire(visited)
        }
    }

    override fun toString(): String {
        return "Wire(path=$path)"
    }

    fun crossings(w: Wire): Set<Coord> {
        return path.toSet().intersect(w.path.toSet())
    }

}

fun main() {
    val w1 = Wire.parse("R75,D30,R83,U83,L12,D49,R71,U7,L72")
    val w2 = Wire.parse("U62,R66,U55,R34,D71,R55,D58,R83")
    println(w1)
    println(w1.crossings(w2))
    println(w1.crossings(w2).map { abs(it.x) + abs(it.y) }.minOrNull())
}