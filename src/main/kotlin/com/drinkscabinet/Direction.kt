
import Direction8.values
import DirectionHex.values
import UpDown.entries

interface Delta {
    val x: Int
    val y: Int

    fun rotate(c: Int): Delta
}

class DeltaImpl(override val x: Int, override val y: Int) : Delta {

    override fun rotate(c: Int): DeltaImpl {
        var amount = c % 4
        if (amount < 0) amount += 4
        var result = this
        for (i in 1..amount) {
            result = result.rotate90()
        }
        return result
    }

    // Rotate 90 degrees clockwise
    fun rotate90(): DeltaImpl {
        return DeltaImpl(-y, x)
    }
}

interface Delta3 {

    val x: Long
    val y: Long
    val z: Long
}

enum class Direction(override val x: Int, override val y: Int) : Delta {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0);

    override fun rotate(c: Int): Direction {
        var amount = c % 4
        if (c < 0) amount += 4
        return Direction.entries[(ordinal + amount) % Direction.entries.size]
    }

    fun opposite(): Direction {
        return Direction.entries[(ordinal + 2) % Direction.entries.size]
    }

    companion object {
        fun from(c: Char) : Direction {
            return when(c) {
                '^' -> N
                '>' -> E
                'v' -> S
                '<' -> W
                else -> throw RuntimeException("Invalid direction char $c")
            }
        }
    }
}

enum class UpDown(override val x: Int, override val y: Int, val c: Char) : Delta {
    U(0, -1, '^'),
    R(1, 0, '>'),
    D(0, 1,'v'),
    L(-1, 0, '<');

    override fun rotate(c: Int): UpDown {
        var amount = c % 4
        if (c < 0) amount += 4
        return entries[(ordinal + amount) % entries.size]
    }

    companion object {
        fun from(c: Char) : UpDown {
            return when(c) {
                '^' -> U
                '>' -> R
                'v' -> D
                '<' -> L
                else -> throw RuntimeException("Invalid UpDown char $c")
            }
        }
    }

}

enum class Direction8(override val x: Int, override val y: Int) : Delta {
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1);

    override fun rotate(c: Int): Direction8 {
        var amount = c % values().size
        if (c < 0) amount += values().size
        return values()[(ordinal + amount) % values().size]
    }
}

enum class DirectionHex(override val x: Int, override val y: Int) : Delta {

    NE(1, -1),
    E(2, 0),
    SE(1, 1),
    SW(-1, 1),
    W(-2, 0),
    NW(-1, -1);

    override fun rotate(c: Int): DirectionHex {
        return values()[(ordinal + c) % values().size]
    }
}