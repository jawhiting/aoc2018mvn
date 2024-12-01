package com.drinkscabinet

import Delta
import Delta3
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Coord(val x: Long, val y: Long) : Comparable<Coord> {
    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())

    override fun compareTo(other: Coord): Int {
        val xs = y.compareTo(other.y)
        if (xs == 0) return x.compareTo(other.x)
        return xs
    }

    fun move(d: Delta, times: Long = 1): Coord {
        return Coord(this.x + d.x * times, this.y + d.y * times)
    }

    fun move(c: Coord, times: Long = 1): Coord {
        return Coord(this.x + c.x * times, this.y + c.y * times)
    }

    operator fun plus(d: Delta): Coord {
        return move(d)
    }

    operator fun plus(c: Coord): Coord {
        return move(c)
    }

    fun distance(c: Coord): Long {
        return abs(x - c.x) + abs(y - c.y)
    }

    fun wrap(xRange: LongRange, yRange: LongRange): Coord {
        return Coord(wrapValue(x, xRange), wrapValue(y, yRange))
    }

    /**
     * All the coords of a line between these two points. Horizontal or vertical only
     */
    fun to(other: Coord) = sequence {
        if (x == other.x) {
            for (ly in if (y <= other.y) y..other.y else y downTo other.y) {
                yield(Coord(x, ly))
            }
        } else if (y == other.y) {
            for (lx in if (x <= other.x) x..other.x else x downTo other.x) {
                yield(Coord(lx, y))
            }
        } else {
            throw IllegalArgumentException("One of x or y must be the same between both coords: $this vs $other")
        }
    }

    private fun wrapValue(v: Long, range: LongRange): Long {
        return if (v in range) {
            v
        } else if (v < range.first) {
            range.last
        } else {
            range.first
        }
    }

    fun distance(x: Long, y: Long): Long {
        return abs(this.x - x) + abs(this.y - y)
    }

    fun rotate90(c: Int): Coord {
        var amount = c % 4
        if (amount < 0) amount += 4
        var result = this
        for (i in 1..amount) {
            result = result.rotate90()
        }
        return result
    }

    // Rotate 90 degrees clockwise
    fun rotate90(): Coord {
        return Coord(-y, x)
    }

    operator fun unaryMinus(): Coord {
        return Coord(-x, -y)
    }
}

data class Vector(val pos: Coord, val delta: Delta) {
    fun next(): Vector = Vector(pos.move(delta), delta)
    fun rotate(c: Int): Vector = Vector(pos, delta.rotate(c))
}

data class Vector3(val pos: Coord3, val delta: Delta3) {
    fun next(): Vector3 = Vector3(pos.move(delta), delta)
    fun rebase(vector: Vector3): Vector3 {
        return Vector3(pos - vector.pos, delta)
    }

}

data class Coord3(override val x: Long, override val y: Long, override val z: Long) : Delta3, Comparable<Coord3> {

    constructor(x: Int, y: Int, z: Int) : this(x.toLong(), y.toLong(), z.toLong())

    val up: Coord3 get() = Coord3(x, y, z + 1)
    val down: Coord3 get() = Coord3(x, y, z - 1)
    val left: Coord3 get() = Coord3(x - 1, y, z)
    val right: Coord3 get() = Coord3(x + 1, y, z)
    val forward: Coord3 get() = Coord3(x, y + 1, z)
    val backward: Coord3 get() = Coord3(x, y - 1, z)

    operator fun plus(d: Delta3): Coord3 {
        return Coord3(x + d.x, y + d.y, z + d.z)
    }

    operator fun minus(d: Delta3): Coord3 {
        return Coord3(x - d.x, y - d.y, z - d.z)
    }

    override fun compareTo(other: Coord3): Int {
        var xs = z.compareTo(other.z)
        if (xs == 0) xs = y.compareTo(other.y)
        if (xs == 0) return x.compareTo(other.x)
        return xs
    }

    fun distance(other: Coord3): Long {
        return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    }

    fun move(d: Delta3, times: Long = 1): Coord3 {
        return Coord3(x + d.x * times, y + d.y * times, z + d.z * times)
    }

    fun elements() = sequence {
        yield(x)
        yield(y)
        yield(z)
    }

    fun neighbours26(): Set<Coord3> {
        val result = mutableSetOf<Coord3>()
        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    if (x == 0 && y == 0 && z == 0) continue   // dont add self
                    result.add(Coord3(this.x + x, this.y + y, this.z + z))
                }
            }
        }
        return result
    }

    fun neighbours6(): Set<Coord3> {
        val result = mutableSetOf<Coord3>()
        for (delta in listOf(1, -1)) {
            result.add(this.copy(x = x + delta))
            result.add(this.copy(y = y + delta))
            result.add(this.copy(z = z + delta))
        }
        return result
    }

    companion object {
        fun of(data: String): Coord3 {
            val nums = Utils.extractLongs(data)
            return Coord3(nums[0], nums[1], nums[2])
        }
    }
}

data class Coord4(val x: Long, val y: Long, val z: Long, val w: Long) : Comparable<Coord4> {

    override fun compareTo(other: Coord4): Int {
        var xs = w.compareTo(other.w)
        if (xs == 0) xs = z.compareTo(other.z)
        if (xs == 0) xs = y.compareTo(other.y)
        if (xs == 0) return x.compareTo(other.x)
        return xs
    }

    fun distance(other: Coord4): Long {
        return abs(x - other.x) + abs(y - other.y) + abs(z - other.z) + abs(w - other.w)
    }

    fun neighbours80(): Set<Coord4> {
        val result = mutableSetOf<Coord4>()
        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    for (w in -1..1) {
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

    constructor(topLeft: Coord, width: Long, height: Long) : this(
        topLeft,
        Coord(topLeft.x + width, topLeft.y + height)
    )

    val area: Long get() = abs((bottomRight.x - topLeft.x) * (bottomRight.y - topLeft.y))

    fun overlap(other: Rect): Boolean {
        return inside(other.topLeft) || inside(other.bottomRight)
    }

    fun inside(c: Coord): Boolean {
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