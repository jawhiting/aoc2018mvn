import com.drinkscabinet.Coord

class GridString(val default: Char = '.') {

    private val chars = mutableMapOf<Coord, Char>()

    private val supplemental = mutableMapOf<Long, String>()

    fun copyOf() : GridString {
        val copy = GridString(default)
        copy.chars.putAll(chars)
        copy.supplemental.putAll(supplemental)
        return copy
    }

    operator fun get(coord: Coord): Char {
        return chars.getOrDefault(coord, default)
    }

    operator fun set(coord: Coord, char: Char) {
        chars[coord] = char
    }

    fun add(coord: Coord, char: Char): GridString {
        chars[coord] = char
        return this
    }

    fun getAll(c: Char) : Set<Coord> {
        return chars.filterValues { it == c }.keys
    }

    fun <T> addAll(m: Map<Coord, T>, transformer: (v: T) -> Char ): GridString {
        m.entries.forEach{ chars[it.key] = transformer.invoke(it.value)}
        return this
    }

    fun applyAll(updater: (Coord, Char) -> Char) {
        chars.replaceAll(updater)
    }

    fun neighbours(pos: Coord, directions: Iterable<Delta>) : Iterable<Pair<Coord, Char>> {
        return directions.map{ pos.move(it) }.map{ it to  get(it)}
    }

    fun neighboursMatch(pos: Coord, directions: Iterable<Delta>, filter: (Char) -> Boolean) : Int {
        return directions.map{ pos.move(it) }.filter{ filter(get(it)) }.count()
    }

    fun neighbours4(pos: Coord) : Iterable<Pair<Coord, Char>> {
        return neighbours(pos, Direction.values().asIterable())
    }

    fun nextInDirection(pos: Coord, direction: Delta) : Pair<Coord, Char> {
        // Ignore default char. If we hit the edge, return default char
        var current = pos.move(direction)
        val maxX = getXMax()
        val maxY = getYMax()
        val minX = getXMin()
        val minY = getYMin()
        while( get(current) == default && current.x in minX..maxX && current.y in minY..maxY) {
            current = current.move(direction)
        }
        return current to get(current)
    }

    fun <T> addAllExtra(m: Map<Coord, T>, transformer: (v: T) -> Pair<Char, String?> ): GridString {
        m.entries.forEach{
            val r = transformer.invoke(it.value)
            chars[it.key] = r.first
            if( r.second != null ) {
                supplemental.merge(it.key.y, " ${r.second}") { a, b -> a+b }
            }
        }
        return this
    }

    fun flipH() : GridString {
        val target = chars.map { Coord(-it.key.x, it.key.y) to it.value }.toMap()
        chars.clear()
        chars.putAll(target)
        return this
    }

    fun flipV() : GridString {
        val target = chars.map { Coord(it.key.x, -it.key.y) to it.value }.toMap()
        chars.clear()
        chars.putAll(target)
        return this
    }

    /**
     * Translate all cells in this grid by the specified coordinate
     */
    fun translate(c: Coord) : GridString {
        val target = chars.map { it.key.move(c) to it.value }.toMap()
        chars.clear()
        chars.putAll(target)
        return this
    }

    /**
     * Translate the grid so that the top left corner is 0,0
     */
    fun normalise() : GridString {
        val c = Coord(-getXMin(), -getYMin())
        translate(c)
        return this
    }

    /**
     * Rotate right 90 degrees the specified number of times
     */
    fun rotate90(count: Int) : GridString {
        val target = chars.map { it.key.rotate90(count) to it.value }.toMap()
        chars.clear()
        chars.putAll(target)
        return this
    }

    override fun toString(): String {
        return toString(false)
    }

    fun getXMin() : Long {
        return chars.keys.map(Coord::x).minOrNull()!!
    }

    fun getXMax() : Long {
        return chars.keys.map(Coord::x).maxOrNull()!!
    }

    fun getYMin() : Long {
        return chars.keys.map(Coord::y).minOrNull()!!
    }

    fun getYMax() : Long {
        return chars.keys.map(Coord::y).maxOrNull()!!
    }

    fun getXRange(): LongRange {
        return LongRange(getXMin(), getXMax())
    }

    fun getYRange(): LongRange {
        return LongRange(getYMin(), getYMax())
    }

    fun getCoords() = sequence {
        for( x in getXRange()) {
            for( y in getYRange()) {
                yield(Coord(x, y))
            }
        }
    }

    fun contains(c: Coord): Boolean {
        return c.x in getXRange() && c.y in getYRange()
    }

    fun toString(nums: Boolean): String {
        if( chars.isEmpty() ) return ""
        val xMin = getXMin()
        val xMax = getXMax()
        val yMin = getYMin()
        val yMax = getYMax()


        val result = StringBuilder()

        if( nums ) {
            // print header
            if( xMax > 99 ) {
                result.append("    ")
                for( x in xMin..xMax ) {
                    result.append((x /100) % 10)
                }
                result.append(System.lineSeparator())
            }
            if( xMax > 9 ) {
                result.append("    ")
                for( x in xMin..xMax ) {
                    result.append((x /10) % 10)
                }
                result.append(System.lineSeparator())
            }
            result.append("    ")
            for( x in xMin..xMax ) {
                result.append(x % 10)
            }
            result.append(System.lineSeparator())

        }

        for( y in yMin..yMax ) {
            if( nums ) {
                result.append(" %2d ".format(y))
            }
            for( x in xMin..xMax ) {
                result.append( chars[Coord(x, y)] ?: default)
            }
            if( supplemental.containsKey(y)) {
                result.append(supplemental[y])
            }
            result.append(System.lineSeparator())
        }
        return result.toString()
    }

    companion object {
        fun parse(s: String, default: Char = '.') : GridString {
            return parse(s.lines(), default)
        }

        fun parse(s: List<String>, default: Char = '.') : GridString {
            val grid = GridString(default)
            var x = 0
            var y = 0
            for (line in s) {
                for (char in line.chars()) {
                    grid.add(Coord(x++, y), char.toChar())
                }
                ++y
                x = 0
            }
            return grid
        }
    }
}

fun main() {
    val gs = GridString()

    gs.add(Coord(0, 0), 'x')
    gs.add(Coord(1, 1), 'y')
    gs.add(Coord(3, 3), 'Z')

    println(gs.toString(true))

    for( i in 1 .. 4 ) {
        gs.rotate90(1).normalise()
        println(gs.toString(true))
    }

    gs.flipH()
    println(gs.toString(true))
    gs.normalise()
    println(gs.toString(true))
}