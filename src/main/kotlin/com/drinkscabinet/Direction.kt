interface Delta {
    val x : Int
    val y : Int
}

enum class Direction(override val x: Int, override val y: Int) : Delta {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0);

    fun rotate(c: Int): Direction {
        var amount = c%4
        if( c < 0 ) amount = 4+amount
        return values()[(ordinal+amount) % values().size]
    }
}

enum class UpDown(override val x: Int, override val y: Int) : Delta{
    U(0, -1),
    R(1, 0),
    D(0, 1),
    L(-1, 0);

    fun rotate(c: Int): UpDown {
        return values()[(ordinal+c) % values().size]
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

    fun rotate(c: Int): Direction8 {
        return values()[(ordinal+c) % values().size]
    }
}

enum class DirectionHex(override val x: Int, override val y: Int) : Delta {

    NE(1, -1),
    E(2, 0),
    SE(1, 1),
    SW(-1, 1),
    W(-2, 0),
    NW(-1, -1);
}