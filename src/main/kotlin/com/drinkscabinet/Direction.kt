enum class Direction(val x: Int, val y: Int) {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0);

    fun rotate(c: Int): Direction {
        return values()[(ordinal+c) % values().size]
    }
}

enum class UpDown(val x: Int, val y: Int) {
    U(0, -1),
    R(1, 0),
    D(0, 1),
    L(-1, 0);

    fun rotate(c: Int): UpDown {
        return values()[(ordinal+c) % values().size]
    }

}

enum class Direction8(val x: Int, val y: Int) {
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