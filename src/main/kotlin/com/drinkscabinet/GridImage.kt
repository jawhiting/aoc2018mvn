import com.drinkscabinet.Coord
import com.drinkscabinet.GifSequenceWriter
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageOutputStream


class GridImage(val cellSize: Int = 1, val defaultColor: Color = Color.WHITE) {

    val data = mutableMapOf<Coord, Color>()

    fun add(coord: Coord, color: Color) {
        data.put(coord, color)
    }

    fun <T> addAll(m: Map<Coord, T>, transformer: (v: T) -> Color) {
        m.entries.forEach { data[it.key] = transformer.invoke(it.value) }
    }

    fun toImage(): BufferedImage {
        if (data.isEmpty()) throw IllegalStateException("No data")
        var xMin = data.keys.map(Coord::x).minOrNull()!!
        var xMax = data.keys.map(Coord::x).maxOrNull()!!
        var yMin = data.keys.map(Coord::y).minOrNull()!!
        var yMax = data.keys.map(Coord::y).maxOrNull()!!
        val cellSize3d = cellSize + 1

        val image = BufferedImage(
            ((xMax - xMin + 1) * cellSize3d).toInt(),
            ((yMax - yMin + 1) * cellSize3d).toInt(),
            BufferedImage.TYPE_INT_RGB
        )

        val graphics = image.createGraphics()
        if (graphics != null) {
            graphics.background = defaultColor
            graphics.clearRect(0, 0, image.width * cellSize3d, image.height * cellSize3d)

            data.forEach { coord, color ->
                graphics.color = color
                graphics.fill3DRect(
                    (coord.x * cellSize3d).toInt(),
                    (coord.y * cellSize3d).toInt(), cellSize3d, cellSize3d, true
                )
//                    graphics.draw3DRect(coord.x*cellSize3d, coord.y*cellSize3d, cellSize3d, cellSize3d, true)
            }
        }
        return image
    }

    fun write(filename: String) {
        ImageIO.write(toImage(), "gif", File(filename))
    }

    companion object {
        fun animate(frames: List<BufferedImage>, filename: String, rateMillis: Int = 100) {
            // create a new BufferedOutputStream with the last argument
            val output = FileImageOutputStream(File(filename))

            val writer = GifSequenceWriter(output, frames.first().type, rateMillis, true)
            frames.forEach {
                writer.writeToSequence(it)
            }
            writer.close()
            output.close()
        }
    }
}

fun main() {
    val frames = mutableListOf<BufferedImage>()
    for (i in 0..10) {
        val g = GridImage(20)
        for (x in 0L..3) {
            for (y in 0L..3) {
                g.add(Coord(x, y), if ((x + y + i) % 2 == 0L) Color.BLUE else Color.YELLOW)
            }
        }
        frames.add(g.toImage())
    }
    GridImage.animate(frames, "/Users/jwhiting/IdeaProjects/aoc2018mvn/src/test.gif", 500)
}