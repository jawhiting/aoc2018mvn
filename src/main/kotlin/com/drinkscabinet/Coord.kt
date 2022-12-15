package com.drinkscabinet

import Delta
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

    fun move(c: Coord): Coord {
        return Coord(this.x + c.x, this.y + c.y)
    }

    fun distance(c: Coord): Long {
        return abs(x - c.x) + abs(y - c.y)
    }

    inline fun distance(x: Long, y: Long): Long {
        return abs(this.x - x) + abs(this.y - y)
    }

    fun rotate90(c: Int) : Coord {
        var amount = c%4
        if( amount < 0 ) amount = 4+amount
        var result = this
        for( i in 1..amount ) {
            result = result.rotate90()
        }
        return result
    }

    // Rotate 90 degrees clockwise
    fun rotate90() : Coord {
        return Coord(-y, x)
    }
}

data class Coord3(val x: Long, val y: Long, val z: Long) : Comparable<Coord3> {

    override fun compareTo(other: Coord3): Int {
        var xs = z.compareTo(other.z)
        if( xs == 0 ) xs = y.compareTo(other.y)
        if( xs == 0 ) return x.compareTo(other.x)
        return xs
    }

    fun distance(other: Coord3): Long {
        return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    }

    fun neighbours26() : Set<Coord3> {
        val result = mutableSetOf<Coord3>()
        for( x in -1..1) {
            for( y in -1..1) {
                for ( z in -1..1) {
                    if( x == 0 && y == 0 && z == 0 ) continue   // dont add self
                    result.add(Coord3(this.x + x, this.y + y, this.z + z))
                }
            }
        }
        return result
    }
}

data class Coord4(val x: Long, val y: Long, val z: Long, val w: Long) : Comparable<Coord4> {

    override fun compareTo(other: Coord4): Int {
        var xs = w.compareTo(other.w)
        if( xs == 0 ) xs = z.compareTo(other.z)
        if( xs == 0 ) xs = y.compareTo(other.y)
        if( xs == 0 ) return x.compareTo(other.x)
        return xs
    }

    fun distance(other: Coord4): Long {
        return abs(x - other.x) + abs(y - other.y) + abs(z - other.z) + abs(w - other.w)
    }

    fun neighbours80() : Set<Coord4> {
        val result = mutableSetOf<Coord4>()
        for( x in -1..1) {
            for( y in -1..1) {
                for ( z in -1..1) {
                    for( w in -1..1 ) {
                        if (x == 0 && y == 0 && z == 0 && w == 0) continue   // dont add self
                        result.add(Coord4(this.x + x, this.y + y, this.z + z, this.w + w))
                    }
                }
            }
        }
        return result
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