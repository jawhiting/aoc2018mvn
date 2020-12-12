import com.drinkscabinet.Coord
import java.util.*
import kotlin.collections.HashMap

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

    fun toString(nums: Boolean): String {
        if( chars.isEmpty() ) return ""
        var xMin = getXMin()
        var xMax = getXMax()
        var yMin = getYMin()
        var yMax = getYMax()


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