package com.drinkscabinet

import Delta
import Direction
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Coord(val x: Long, val y: Long) : Comparable<Coord> {
    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())

    override fun compareTo(other: Coord): Int {
        val xs = y.compareTo(other.y)
        if( xs == 0 ) return x.compareTo(other.x)
        return xs
    }

    fun move(d: Delta): Coord {
        return Coord(this.x + d.x, this.y + d.y)
    }

    fun distance(c: Coord): Long {
        return abs(x - c.x) + abs(y - c.y)
    }
}

private data class Coord3(val x: Long, val y: Long, val z: Long) : Comparable<Coord3> {

    override fun compareTo(other: Coord3): Int {
        var xs = z.compareTo(other.z)
        if( xs == 0 ) xs = y.compareTo(other.y)
        if( xs == 0 ) return x.compareTo(other.x)
        return xs
    }

    fun distance(other: Coord3): Long {
        return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    }
}

class Rect(val c1: Coord, val c2: Coord) {

    val topLeft: Coord =
        Coord(min(c1.x, c2.x), min(c1.y, c2.y))
    val bottomRight: Coord =
        Coord(max(c1.x, c2.x), max(c1.y, c2.y))

    constructor(topLeft: Coord, width: Long, height: Long) : this(topLeft,
        Coord(topLeft.x + width, topLeft.y + height)
    )

    val area: Long get() = abs((bottomRight.x-topLeft.x) * (bottomRight.y-topLeft.y))

    fun overlap(other: Rect) : Boolean {
        return inside(other.topLeft) || inside(other.bottomRight)
    }

    fun inside(c: Coord) : Boolean {
        return c.x >= topLeft.x && c.x <= bottomRight.x && c.y >= topLeft.y && c.y <= bottomRight.y
    }
}


fun main() {
    val l = sortedSetOf(
        Coord(9, 0),
        Coord(1, 1),
        Coord(5, 0)
    )
    println(l)
}