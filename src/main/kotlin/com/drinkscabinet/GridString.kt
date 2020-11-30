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

    fun get(coord: Coord): Char {
        return chars.getOrDefault(coord, default)
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

    fun neighbours4(pos: Coord) : Iterable<Pair<Coord, Char>> {
        return neighbours(pos, Direction.values().asIterable())
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

    override fun toString(): String {
        return toString(false)
    }

    fun toString(nums: Boolean): String {
        if( chars.isEmpty() ) return ""
        var xMin = chars.keys.map(Coord::x).minOrNull()!!
        var xMax = chars.keys.map(Coord::x).maxOrNull()!!
        var yMin = chars.keys.map(Coord::y).minOrNull()!!
        var yMax = chars.keys.map(Coord::y).maxOrNull()!!


        val result = StringBuilder()

        if( nums ) {
            // print header
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
            val grid = GridString(default)
            var x = 0
            var y = 0
            for (line in s.lines()) {
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
    gs.add(Coord(10, 14), 'Z')
    gs.add(Coord(5, 3), 'Z')

    println(gs)
    println(gs.toString(true))
}