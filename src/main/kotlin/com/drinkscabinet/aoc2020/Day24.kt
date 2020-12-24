package com.drinkscabinet.aoc2020

import DirectionHex
import GridString
import com.drinkscabinet.Coord

private fun main() {
    val grid = part1(input)
    part2(grid)
}

private fun part1(s: String) : GridString {
    val grid = GridString(' ')

    for (line in s.lines()) {
        val pos = getTile(line)
        val color = grid.get(pos)
        if (color == 'b') {
            grid.add(pos, 'w')
        } else {
            grid.add(pos, 'b')
        }
    }
    println(grid)
    // count black
    println("Black count=${grid.getAll('b').count()}")
    return grid
}

private fun part2(startingGrid : GridString) {
    var grid = startingGrid

    for( i in 1..100) {
       grid = mutate(grid)
       println("Day $i: ${grid.getAll('b').count()}")
    }

}

private fun getTile(s: String): Coord {
    val directions = parse(s)
    var pos = Coord(0, 0)
    for (direction in directions) {
        pos = pos.move(direction)
    }
    return pos
}

private fun mutate(g: GridString): GridString {
    val result = GridString(' ')
    val dir6 = DirectionHex.values().toList()
    var changed = false

    val whitesToCheck = mutableSetOf<Coord>()

    val blacksToCheck = g.getAll('b').toSet()

    for (coord in blacksToCheck) {
        // queue up the white neighbours
        dir6.forEach { whitesToCheck.add(coord.move(it)) }

        val occupiedNeighbours = g.neighboursMatch(coord, dir6) { it == 'b' }
        if (occupiedNeighbours == 0 || occupiedNeighbours > 2) {
            result.add(coord, 'w')
        } else {
            result.add(coord, 'b')
        }
    }
    // Filter the neighbours - remove any blacks we already checked
    whitesToCheck.removeAll(blacksToCheck)
    for (coord in whitesToCheck) {
        val occupiedNeighbours = g.neighboursMatch(coord, dir6) { it == 'b' }
        if (occupiedNeighbours == 2) {
            result.add(coord, 'b')
        } else {
            result.add(coord, 'w')
        }
    }

    return result
}

private fun parse(s: String): List<DirectionHex> {
    val dirs = s.replace("\n", "").replace("e", "e,").replace("w", "w,").toUpperCase().split(",").filter { it.isNotEmpty() }
    println(dirs)
    return dirs.map { DirectionHex.valueOf(it) }.toList()
}


private val testInput = """
    nwwswee
""".trimIndent()


private val testInput2 = """
    sesenwnenenewseeswwswswwnenewsewsw
    neeenesenwnwwswnenewnwwsewnenwseswesw
    seswneswswsenwwnwse
    nwnwneseeswswnenewneswwnewseswneseene
    swweswneswnenwsewnwneneseenw
    eesenwseswswnenwswnwnwsewwnwsene
    sewnenenenesenwsewnenwwwse
    wenwwweseeeweswwwnwwe
    wsweesenenewnwwnwsenewsenwwsesesenwne
    neeswseenwwswnwswswnw
    nenwswwsewswnenenewsenwsenwnesesenew
    enewnwewneswsewnwswenweswnenwsenwsw
    sweneswneswneneenwnewenewwneswswnese
    swwesenesewenwneswnwwneseswwne
    enesenwswwswneneswsenwnewswseenwsese
    wnwnesenesenenwwnenwsewesewsesesew
    nenewswnwewswnenesenwnesewesw
    eneswnwswnwsenenwnwnwwseeswneewsenese
    neswnwewnwnwseenwseesewsenwsweewe
    wseweeenwnesenwwwswnew
""".trimIndent()


private val input = """
    nwnwesenenewnwswnwweewswwweswwsew
    wswnewwwwwwwswswseswswswwwwesw
    eeneeeeseswenweeswneneeneeneene
    nwnwnwnwnwnwnwnwnwnwsenwnwnwnwnwnwwenwnw
    swneswnwneseswenwnwnwwseneneswnenenene
    nwnenwnenwnwneenwnwneneneneswnenene
    seneswsesesesesesewnesesesesesewsesese
    neseseesewwwnwswseseseswseneswswseee
    eneneeeneeeneneeenewneneneee
    seseeeneseseeseseseeeeseeewse
    swneswewswwswwnwenwsewwnewwwww
    wswwwwwwnwwwnwsewnew
    neeeneeeeeeeweeeneneseneeenw
    eneewenwnenwswnwneswnwnwswesenwnwne
    eeeeeenewsweeeeseene
    swnwseswseswsenenesenewsesesenwsesesesese
    wswnwnwwwwwwwwwnwwseenwwnwnwnw
    neeweseseeeenweewweesenwnee
    swswnenwsweenwwswswswswswnwswwwswsee
    nwseseewsenwswnenwnwsewneswewnwnwnwenw
    nwnwnenwswnenwnwnenwnenwnenwnwnwnwswnwnwe
    swneenwneneswnenenenenenenenenwnwneneswnene
    senwnwneneneenewnwnwnwenwnenwnenwwnwnw
    swwnwwwwwwwswneswewwwewwswe
    neewneweswnwnweseswnwwneeneswnwnw
    nenenweeeeneneswneswnesweenenenewe
    seeswswwswswswswsweswswseswwswswswswsw
    seseseseneeseseeswsesesesenwsesenwswse
    ewenwsewnwwnwnwwnwwnwnwwwwww
    sewnwwwnwwnwewnwwswwwwnwnwww
    wswnweswswwswwesesweneseswneswwesw
    sewswswneswswwswswwswswswswswwwswsw
    swseswwseswswswseswseseneseseneseseswswse
    seswswesewseswsesenwseseesewneseswswswsw
    neeenwneneswneneneeneswenewnwenesw
    wsewenewwwwwwwnwwwswenwnwswe
    neseswseseswswswsenwnwswwswseswseneesee
    swnenenwenesweeneeeeswnwneenenee
    wwwwwnwwwswwswwwwwseww
    seenwseeseseseeweseseeeeenwsese
    seeswnwsesenenweneseenwswswseswsesese
    swswweneweeeeswseneenwnwneeene
    wnwwswwwwwswwseswswswswwwwsw
    nwwnewewnwewswwswewnwwwnwwswe
    eeewseeneesweeeenwwnwseeeese
    wswswneswswswnwswswwwswwswswswswwesww
    neweneeswnwnwenenenwnenwnwnwwnwsesw
    seswesesenenwewnwneewnewnwnewneew
    swwenewsenenewneneneneneneeswswewe
    nwnwnwseenwwnwnwwnwnwwneswwnwwwnw
    nwnwswswwwnewswneewwnwwwwwnww
    wnwnwwwnwenwnwewwnwswnw
    swswswswswwswswneswswswswswseswwswswnw
    sesewswseseseseeseswsesenwseseseesesw
    eeweneeneeeeesweeneeeenee
    wnwnwnesenenwnwswswwnwnwnenenwneeseswnw
    seswseswswsesesesesesesewseseseseneese
    seseseeseseweseseseeseseeseese
    enweneseseeseeseseseseeeeseew
    eneneneeneenenenenesenwnene
    nenwnenesenenwwnewnwnwenenwe
    wwneseneswswsenewwwwwwseeswwnwe
    seseeeseseseseswnwwseesweenenesewnee
    wwneseswsewseeseseswsesesenwsesesesee
    swswswswswswesewwwswswswnewswswsww
    eesweeswswwswenwenwnwnwsenwnwswe
    nenenewneneneneneneenewneneneneenene
    wwswnenwnwnwenwswnenwswswewnewesesw
    eeseeeseeseeeseseweneeseeeewse
    ewwwnwnwwsenwwwwnwwwwwwwww
    seseneswsesenewseseswseseswsesesesesesesese
    seneseneseswwseesesesesesenewseswesenw
    nwswswnewswswswwwswwwewnewsewse
    swnwswseseswneweseseseseswswenenwnwsw
    eeneenenewweeeeeneeseewnee
    wwnwwwwnwwewnwswwwnwewwne
    swwswswswwnwsweswwswwwswnwseswswsw
    wwswwwwsewewswnenwwsewswswnwswsw
    nwsenwnwswnwnwnwnwnwnwwnwnwnwnwwenwnw
    nenwnwnwnwnwnwnwnwnwnwsenwneswwnwnwnene
    nwseeswesenwseseswsewnesenww
    nenwwnwnwnwsenwnenwwwnwnwnwswnwwnwnw
    swwwswswwwswswswnewsw
    nenenwneneneneswnwsenenwnenenenwnenwnwnw
    wwwnewwswswseswwwwwwsenwwwwwsw
    nwnwewwwnwswnwwnwneeenwswewswsw
    swswswswseswswswswswnweswswswswswswsweswnw
    nenwenewnenweswneneenwnenenenenwseneswne
    seswsenwwnenwseseseswsewsenesesesenw
    neenweneneeeeneeeneeesweneeesw
    nwnwsenwnwnwwnwnwnwnwwnwnwnwsenenwnenwnwnw
    swseswseswwswswseseseneswswseswsesesese
    sesesesesesenwseseeswswseseeseneeese
    wwnenewwwwwsewwnwwswwwwnwse
    nweeeswnweswesenwsenweweenwswe
    swseswswseswwswswswswswswswswswwneeswse
    wwnwnwwnwnewnwnwnwnwsewwewnwnwse
    enewwswneeneswe
    sweneswswswnwwseswswwswewseswnenenesww
    ewwwswwwnesewwsenweneswnwneswesw
    swwwneswswswnwswwswwswwsewnenwswewne
    seeseswseswseswsenwswseseswseseesewse
    senewnesewnwwsewseseneseswneseseenwse
    swwnwwewwwnwwwnwwwwnwnwwnwnw
    wsenwswswswseseswswneseswseseswswsesese
    nenenwnwnenwenwwnwnwneeswnwswnenwwesw
    weeseneeeeeneneeeneeeewenenee
    nenwneeseenenenenenenenenwneswnwnenewwnw
    eeeeeeseeeswneeseenwesenwesew
    nwnwnenenwnwnwneeswenenenwnenenwwnene
    swwnwnwnwnwnwnwnwnweeneenwnwsenwwnw
    nenenenwnwneswnwswsenenwnwneenenenenwnene
    eeeeeeeeeeeeewe
    swnwseswsesenwsenwseswnenesenwewswswse
    neswneneeneneneneseneneneneneenwnewne
    nwwnwsewnwwsenwwnwnwnewwnwnwwnwnwnwnw
    neswsenwnenwnenewwsesesewnwseneenwnwne
    nwnwnwswnewnwnenenwnenwenwnenwnewsenw
    eneewneseseneseswseseeenwsww
    seesenwsweseseseeseseeesesesesesenw
    senwnwnwswwnwwnesewwnwnwnenwenw
    eseswneswseswnwnwsweseenewnenwnwsese
    senwewseenwswneeeeeeeeeneee
    swnwsenwwnwnwneneswnwewwwnwnwnwnwnwwnw
    swwsesenwsesenwseeeseneseeweseseee
    wwwwswwswwswwnwwswsewseneswswswsw
    nenwwsewswnwnwenwnwnwnwenwnwweenwnw
    swseeseeseeeeeeeeeeeeeenw
    eeswweeeeneenweeee
    seseseeeewseenewseeseseseseneesese
    wnwnwnwnwnwnwnwnenwnwnwwsenwnwnwnwnwnw
    wwswwwwswwwwwwewwwwnewww
    seswnwsweweswneswseswswsesewswnwsese
    swwwswswseswwswswswwswswwnwwnwwswe
    swnwwnwwewnwnwwnwnwnwwnwwwwnww
    nenenenwnwswswewsenwnwsenwnwesenwwnwse
    seswneseneseswseseseseswseswwsewsesese
    neswnenenenwnewneneeneenene
    swnwswnenwneenwenwswsenwnwnewnenenene
    swneswswswswwwswswswswswswswswnewswswsw
    enwnwewnwswwwwnwwnwwneswwesww
    senenwnenwnwnwneewnwnwnwnwnwswnenwnwsw
    senwnenwwnwnwneeswnwseswnwnwseenwenwnw
    wswwwwseswswwwwwwwwwneew
    swwswsenwswswswswswswnwswswswswnesweeswsw
    nenenenwnenenwneswneseseneneswnenew
    nwewnwnwneswnwswswseseeeesweewseee
    neswswwwswneswwswswsweswswswswwswsw
    nwnenwnenwswneeneneneneneneswnwenenwnenw
    eseeneeeseweeneseesesweeeee
    neneneenwnenwewsewnenwwnenenwnwew
    sweeswnwnwneeswnwwswnwnwe
    nenwnenenwnenwneswneenenenenwnenesenwne
    eneseswswseswneneswswwwswswsesesewsese
    nwnewwnwwnwnwswwwnwwwnwwenwwsww
    ewswseeeseeneneneenew
    esesesenewnwwseneseseseseswnwse
    nenwnwnwnwnwnwsenwsenwnenwnwnwwnewwsese
    ewwwwnewswwswnewwwww
    ewwwnewwnwewswnwnwwswwwnwwew
    nweswswswswnwswsweswswseswwnwswseswee
    seenwsesenwsewnweneseswseseseseseesese
    senewseswesenwsenw
    swneswswseweswseswseswneswwseswsewsesw
    wnwwwswnwswnewneswseswsesewweew
    nwnwsenwnwnwwnwnwnwnwnwnwnenwnw
    ewenwneenwnenwneseseswneswnenenenwnwsw
    seseseseseenwsweseeeseseesesenewse
    eseseseseseeeeenwneeeseswwesenwee
    wnwesweeswenweeeeeeeeeneee
    newwnwwewswweeewnenwwswnwwsw
    wwwwwwnenwwswswswweewwswswsww
    senwseswsweswswswseswswwswsweswwswswsw
    neneneeweneneweeeeseeenenenenee
    nwswnwnweneneswsesweneneneneswsenewnene
    swswnewwswnwsewwwswswwwswwwwswe
    nweswsewenweeswweewneeeseesese
    eeeswseeeeweeeenenwe
    wswwwwnwnenwwwnwnwwnwswnwwwew
    swwsweeswswwswswwseswswseswseswswe
    sweswnwsweseswnwsenwswseswswwne
    swsesesenwswseswnwswswneseeswseseswweswse
    newwwnwwswnwnesewnwnwenwnwnwnw
    eneswsesesewwenewneswseeseneneseswse
    neswswwwwwswwwsenwsweswswneswww
    sweneeeeneeeswesweneneneeenee
    senweseneseswwswswenw
    wweswwsewneswnewswneswwwsw
    eeseseseesesenewsesewsesesesesesese
    enwenesesesweeseeeeeeseeswesenwnw
    nenenwnenenenwwswneneneneneneneneneenene
    seswnewnenwswnwnwnwnwwnwnwswnenenwwnwsw
    nwnenenwnenenwnwsenenenwneswnwnenenwnenee
    seseswsewsesesweseswwsesesweseseswse
    nesenwnwnenenenenenewneneeneneneswnenenene
    nwswseswswseswswswseeseswswseswswnwswswnwse
    eeeseeseneewnweeseeswseesewsee
    nwnwnwswnwnwnenwnwnw
    eeneeneenweeneneswenenenenweesw
    wswseswswswswswseweneswsenenweswww
    nwnenenwnwnwnwswenwnwsenwwnwnenenenenenwnw
    wnwnenenenenwnwnenwnenwnwsenenw
    seesesesweseeeseseseseseesenwse
    swseseseswseseswwsesesesesesesenwseeswse
    nwwnwwwwwwnwnwwsewnwsenwnwwnee
    nwwnwnwwnwwnwsewwnwnwnw
    eeneneneseeneswneneneeeneweewee
    eneewneneneneneneeseneneswneneswnww
    seswnwsesesesesweseseswswseeseswswsesesew
    neeseneneenenewne
    sesesweewweseseeseeenenesesewew
    wsewswneswwwswswswswseesweswwnesww
    eswswwswnwewneseneswwswnweenwwsw
    swswswwesweswnwnwswswswswsewswsewsw
    ewwswwwwnwsewnwswnewswswswwsww
    eneeenewneeeneseneeneeeswnenenwne
    swwwwwenwwwnwwwewseeswwwne
    enwenweewsweswseeeeeseeeee
    wnwwwwswwwwwswnewnewwsewenesw
    seeseesesesesesewse
    sewnewnwsewsewnwewnenwwwwnwswnewnw
    swswswswswseswswnwswnwswseseswsesw
    nwnwnwwsewnwnwnwnwnewnwnwnwnwnwnwnw
    nwswswseswseswseneseseneeswswseswwswsenwse
    seewneseseeeweseeeeseseseeeswsenw
    eneseeweeneeeeneeeweeneneee
    swswswswswswwweswwswnwseswswswswswnee
    nenesewnenenwnenenene
    ewwnwsesewnenwsewseneweswwnwswnw
    sweneneneneesenenenenewenenenenenene
    neeneneneneneeewneeeswneeneneew
    wnwsenwewnwnwenwnwseswnwneweswnenenw
    swswseseenwswewswswsewswswnwswseeenw
    nwswwswenewewwwswswwwswwwwse
    eeseseeeeeswenweeseeseeenw
    neswnesenwnenenenesenwneenene
    seseneseseseseeseswsesesewswsesenwsesesese
    eseswseeseeseewnwwseesesesesesee
    nwnwnenwnwewswnwnenenenw
    weeseneeseenweneeeneeneeeee
    neneneneeneneneneneneneeenenew
    swwswswswswswswsenwswswswswseswneswesw
    nenwnwnwnenwsewnenwenwenwsenwnwwsenwne
    senwwwwneseewsenewnewwwwswnwwsw
    swswswwsesewwswwwneneseenewswswswsw
    newseeeeeneeseeseeweseeeseww
    neneseeenenenenewnesenenwnenenenenenene
    seseseesesesesesesesesesewseseseswse
    newswswswswswneseswswneswswseswswwwnesw
    eewneseneesenwenwneswsesewesesewse
    seseseseseneseseeeeeswee
    ewnenenenwnwnwnwnwnenwwnenwnwnenwenene
    eweweneeswneeneeneesweenenenene
    seseseseseswseewesewnesenwseseesenese
    neswwseeseeswsenewnenwneswnwsenwneene
    sewnwewwwnwewnwewswnewnwwswww
    nenenesweeneenenwnwenenenesweeneese
    nwneeseesesesesesewnwseswseweesese
    nwnwnwnwnwsenesesenenenenwswnwwnwnenwwnwne
    swnweswsesesesenwseseseseseseseneswnwse
    eseseeseseeseseseseewsesesesewenesee
    eswnwnenenwneenwneswneneneswnwswnwswne
    neseseseeswsewswswswneenwwsww
    eeweeesenwwsenweeeeseeee
    swwswsweswswswswswsewne
    senwwwweneseneweesenwnenenwnwwnw
    seseseseeesesenwseesweeseseesesee
    eeeneseeweeneneeeene
    nwswwwenwnwnwnenwnwswswnenwnwnenenwnwsesw
    nenenenwnwnwnwnenenenenwsenenwwnenenene
    swswsweswswewswswswswswswswnwswnwswsw
    ewswswswwwswwswnewseswwneneswwsww
    swswswswnwswswswswseswsw
    eswswswswwwnwswwe
    esesweeseseeenesenwseeseseeseseese
    swswnewswswwswswwswswswswswwseswsw
    wnwnwwwnwewwwwnwwsenwnwww
    seeweesesesesesenwenwswseeeeswe
    swesenwnwnwnwwnwnwneenwnwswnwnwnenwnwnw
    nwnwnwwwnwnwnwnwnwnwenwwsenwnenwnwnw
    nenenenenenesenewneneneneneneneswenene
    neswswswswnwseswswsewwwswwswsweneesw
    nweseseseeseesweseneeseeseseeswsee
    swswwwwwswwswwneswseewwwwwww
    seeseseseseseeseesesesewsesesese
    enwneswnwnenenenenwsenwswnenenenwnwse
    swseswnwwswswswswswswsweswswswswsesesw
    wnwwnenewneneseswswnesenwswwesewnesew
    neseseseseswseseswswseseseseseswse
    wwswewwneswswwwwswnwwseswwswwse
    seseseseswwneseneneswswnewewenwwne
    sweweseseswsenwneswwseseswsesesese
    eeeeneeneeeeseeeeeswseweese
    nwenwenwnwswnwswnwnwnenwnwnwnwne
    nenenenewneeneesenwewneweeswseesee
    nwnewewwwwwseswswwsewweneswnww
    sewwnwnwneswwwnwwwnwnwwwsewwww
    eeeeneeswneeeeenwnwewseswee
    eswnwwwwswnesweswnesewswnwwwswswsw
    nenwnwneswnenwnewnenesenwnenesenwenese
    seseseseseseswnwsesesesesesenwe
    nweswnwswseesenenenwswseesenww
    eeeeswneweneeeeeenwnwseeesese
    wswnwneswswseenenwswneswwswwnee
    seenwnewneewwsenenenewnwnwseneswswse
    nwswwswnwswneneswsweseswse
    newnenenwnwneswnewnenesesenenwsenwnwnwne
    swswwswwnwswswswneswswswewwswswswsw
    swwswswneswwswnewwswwswswswsewww
    wwwwenewnwsenweseewwseewww
    wwwwseewwwwwwwwwwneww
    seswseneseseseswnesesesesesenwswswsesese
    swswneswswseenwnwseswswnwsesewswnesese
    nwnenenenwneneneseneneenewnenenenenenw
    eeeewesweneneneeweeswnweswnenw
    seeseesesesewsenesese
    nwnwseenweenenenenesweeswneneswnenenene
    wswwwwswwnwwwswnwwwsewwwwwse
    eseswewnwnwesesewswsweneseswnwesw
    swswnwswswswseseswswswswswewswswswnwsw
    ewsenenwneswseneswneswnenw
    swnwnwnwnwnwnwnwnenwnwnwnweewswnwwwnw
    eeeenwsenewwswswwnwwwnwwwnew
    seseesewseseseseseneswweseseseseese
    neswswwewswwswnew
    nenenwneneenwseneneneneseeswneenewne
    seenweseswseeseeese
    wwwnwwwnenwesesenwnwnwnww
    nwsweeswnwneseseneenwswseneswwseseneswse
    sesewswsenwseseseseseswsenese
    enenenenwneenesweneneeeneneeeene
    nwnwneeswneneswnwnwnenenenenwenwnwnwne
    swneesesesewswwnwseseneseswnwnwnesew
    swsesesesenesenesesesesew
    seneewnesweneneeneneneeneneenenene
    ewwswseswwswsenwswwwwwswnwnwswsesw
    eeeswseeneeeeeswseneseesewnewnw
    seseswsewswnwseseseeswseesesewsesese
    nenwswseeswnwnweenwnwneswneeswswsewnwse
    seseesesweneseesese
    eswnwewsweseeseeweseeesenenwse
    eesenweenwswweswnweeeeseswenwse
    nwneneneneneneneneneswnenene
    nwnwwnenewwnwnwnwswnwwwswnwwnwwnw
    seeneeewseeewsenwseesweeeseee
    sewnesewweneeeenwwnewwswswwswnw
    neswswwnwnwnenwnwnwwsenwnwnwnesenwnwnenw
    swnwswswnenwseswneeswnweesenwnw
    neswswnewnenwseeneewenene
    nenwneneseswneneneneneneenenenwswnenwsene
    seswwswwwneseseswswswnwnewwsesenwnesw
    eeeseenwseeneseenwseeeeseweee
    nwnenenesenenewnwnenwnwswenwsenenenew
    nwneneeseneswsenenewnwnwnewne
    wwwswswswwwsewswswwneswwneswnewse
    wnwnewsenenwnwenwnw
    nwwseswsenwwseswswwswwnweenwwwnwsw
    nenwseneswnenwswnwenwnenwnenwnwnwwnwswe
    nenweneeseenweenenwsweneseneenene
    sweswswwnwswswwwwwswswswswsweww
    wwneseswesewsenwwnwnenwnw
    wswswswewwswwwwwsewwwswnwwww
    swswneeswswswswseswseswnwewnwsenwswsesese
    swwnwswsweeswneswsweseswswsenwswnwsw
    wneswneseswseseswswseeseseswwseseswsese
    seswnwnwswswswswsenweseswswwnwswswese
    swneseseeseeseseseseseseesewneseseseese
    swswswswseswswswswswswswseseneseswneswse
    sewwswwswweneswswswwswswswwwswwwsw
    swswswswswswswswswswswnwswswswswswswnese
    wwwswwwwwwswwsenwwwwwwnesww
    seseseeseswwseeneseswsesesesesenwsesesese
    enwnwnenwwnwnenwnwnwnwnenwnwnenwwnee
    swswsewneneswsenwseeswseseneeswsenwsenesw
    sweswswswswswswsenwswswswswswswswsww
    seseseseseswsenesesesewseseswswnwseseswse
    seneenwweenwsesewwneeswnenwwenee
    enwenewseeneeewnweseswnesenenene
    wwseswswswneswswswswswwswwswsw
    swseseswwneswwnenwew
    eseweswswnesenweneneesee
    nwnwnwnesenwnwnwnwnwnwswnwenwnwnenwnwne
    swwwwwseswwwswneswswwwwswwww
    nenwnwswnesweseeswnenenwneenene
    neenwwewneeseneswsenwswswewnese
    nwnwwnwwwenwnwnwnwnwnwswenwnwwnwnwnwnw
    wwnwwwwwwwwwwwwewwww
    newweeneseseenwneseswnwneeesenwnw
    esweeenewneswnenenenwenee
    swswwswwneswswswswwwwswwswswsw
    nenenenenenenenenesenenenenewneneneswne
    seseseswsesesewsesesesesenesese
    ewnwseswwswswwneswswswwneswwwswnw
    newnesenenenenenenenenenwweneswnenene
    swneseneseseneswwsenesesesewseswswswnw
    nwnwnwwnwnwwwnwnwnwnwsewnw
    ewneseeeseeneeeneeewneeeeeswne
    nwwnwswenwswwwnwwnenwnwnwnwwnww
    sewweswwwswwenwwwwwwnwwswesw
    swnwneswwsweenwwwwnwnwwnwwnwnww
    neswnenwwneneswwnenwnwenwsenenesenenwnwnw
    nwnwsenwswnwwwenwnwnewswswnene
    seneseswseswswswswseseswseswseseseswwswne
    nenwnenwnenwnwnenenwnenenwsenenenwswnwswnw
    enwnweneneneneneeswneneneesweenene
    swswswswswnwswswswseseswswswswneswswswsw
    eeeneneenweeeeeneneeeswnwesw
    eneeswsweeneeesee
    seseeeseseseeeswseseseseenwnwesese
    eneneneneenenenenewswneneneneeeene
    enwswneenenweseswneeewswesese
    wseeeswsweswnewnesesesesenwewnwnw
    seseswseswseswswnesesenwseseswswswseswsenw
    nenwneneneneneneewnenwnene
    nwnwwenenwnenwnwseneswnwnwnwenenwnwnenw
    sesewnweseswseseseseseseseseseseseseseenw
    neeneeswneenwnenenenenenwneneneneswnenene
    neneswneswneeswneneneneneswenenenenwnene
    senewnwenweewsesesewneseswneeeswnw
    nwneeewsewseneneenwnwneswenewnenwnw
    sweeenwwneswnewnweeeewnenwnwswswne
    esweeneweneeeeeewenwsweesw
    neswnenwwwwewsenweswwnwseeewnwe
    newwwwwwwwwwwwwwseeswsww
    nweeseeeeesenweeeeeesweee
    wnwwenwwwnwnwnwwnesww
    wewswwseneswwwswnwwnwwwwwnesww
    nenenenenwsweeneneeeweseswnwneene
    eeeeeewsweeesenweeesenweee
    eeeeeneneneneswnenewneneeeneeese
    nwswswnenesenenenenesenwnenenenenenw
    neeeeseseeeseeswneseeeseseeew
    seswseswnwseswseseswsesesesw
    swwwswwwwnwwseswwnewewwwwswsww
    ewwwewsewnwwwwwwwwwwwnww
    swsesesesewsesesenwseseneseseewswsese
    nenenenewneneeneneswneeneneneeenenee
    nwnwwwswnwwnwwenwwnwnw
    senenenweswnweweneneswesenwneneneneene
    enenewneneneeeneeneeseenewnenenene
    sesenwnwseseeseeseeeee
    seewnwswswseswswseeswswswswseswsewesw
    swswswswnwwswseswswwwewwwswwwnwww
    nwnwnwnwnwnwnwnwnwnwnwwnwnenwnwnenwsenw
    eeeeeeeeeneweeesweeeee
    eswswwwwswwswswwswswnwwwwswwsw
    neneneneneenwneneneenewsewweenese
    nenenwnenwneneneneneswnenwswnwnwnwnwenwnwe
    nwnwsenwnwwwenwnwenwwnwsewnwnwnenw
    nwsenenwnwnwnwnwnwnenwnenene
    eeseneseseswnweeenwneswnwsenweseswse
    enwnenesenenewnenwwwnwnenenenwnwene
    nwenwnweswnwwnwnenenenenwsewnwnwnwnene
    seneseseneweeweseseeenesesweee
    nenenesenenwwnwnwnwnwnwsenwnwnwnwnwnwnwnwne
    swswswewenwneswswnweseswnwweswswnw
    nenwsenenenwnwnewnwswnenenwnenenenenenesw
    nweswseenewewnwewwnwsewswnwswnww
    ewseneeneeneneeneneewseneneewnenw
    wwnwnwnwnwwwwnwenenwnwseswwnwwww
    senenenenwnenenwnwnesenenwwswnwnwnenwnwe
    eseseesweseeeeseeesweeesenenwe
    neneswnenwenwsenenwsweeneneneeswsenwe
    neswswsweswswswnwswswneswswswwswseswnesw
    swseseseeswseswnwseseseswseseswnwsesesw
    seswswseseseseswneseseswswseseseswseswnesw
    ewwwwnwwwwwwwwwwwwswww
    seneswwwseseseseseswsenwseneseseswseswse
    wsenwsenwwnweneswseewnwnewneseseswsenw
    seswswneswswneseswswseswseswseswseswsesw
    weseeeenweneeeneeeneeesweeee
    neneneneneswneneneenwenene
    eewseesesweeeenwweeneswewene
    swswswswswwswswwswsweswswnwswswswswswse
    wnenwneswnwnewswnwnwsenwewnwneenenwse
    neneeneneneneneewnenenesenene
    eeeseenwsesweeeeweeeseeenee
    wswswseswneswsweneswswneswswseswswsww
    nwnwwwnwsewnwwwwnewwwnwwwww
    nwnwwnwnenenenwnenesenwnenenwnenenwnwne
    wswswswweswnwswswswwswswswwswswwsw
    newneneneneseneneneew
    eeswsweseseneenenenewseswseenwese
    seenwseeswwnwwseenenenwnewnewnew
    neeneeneenesesweneeneweenenweeee
    swsewwnwswwswswwsenwnewswwwneswe
    nenenewneneeneeeneneneneeneneneneswne
    seeesewseeeeeneeesenweeeswse
    sesenwseswnwswsweseswnwswnweswseswnwswsw
    ewwwwwwsewwwwnwwwwwwnewwnw
    nwswseswnwwwsesenwenwnewnwnwwneswnwe
    swnwswswswswswswswswswswswswsweswswswsw
    sesesewnwseswnwseseswseswswswesweswsw
    eeseeeenwswsweeeeeeneeeeeee
    nenenenenenenewseneeeeseneneenewnenene
    eeeeneneeneswneneeewneneseeene
    nenesenenenenenewnwnenenenenenenenenene
    nwswwnwweswsweswswneneswswswsw
    sewnenenenenweenew
    weeswenwswsenwsenwnwnweeeswesee
    esenwsenenwsesenwneswwseseswsesesesewse
    nwwnwnwnwnwnwswenwneswesesenenwnwenwwsw
    wwnwnwnwnwnwwwnwnwswnewwwnwwnw
    nwswenweswnwnwenwenwsenwnwwnwwnenwnw
    eneneweeeneesenenenwenenewneeswne
    seeseeesesenesesesesewsenweswseswne
    eeneneeswwneneeeneenenenenewnenene
    sesenewwwwewwwwwnewswwwnwnwne
    senweeswnwseseneswnwsewswesene
    sesesewwsenwseneesweseeeneswsesee
    wnwnwnwwwwnwwwewswnw
    swwwwwewwwwwwswesw
    wwnwsewsewwesenwswsenwnewwnwsenww
    nwnwenwnwnenwswnenwnwnwnwnwwnwnwsenwnwse
    swsewswswseseswswwwswneneewswwswne
    eeeeeenweeeswweeeeenwesee
    wwnenwwwnewwswwwnwsewswwwsew
    nwnwnesewsenwnwenwnwnwnwnwnww
    nweswnesenenenesenenewsenewneneneswne
    enwseseneenwwwnwnwnewnwsw
    wwwenwwswwnwwnwnwnwnwnwnwwsenwwnw
    swneswswswswswwswswswswswseswswnewswsw
    seseenwneseweeesweneenwseswseee
    senwswnwneswwwnwseewwnewnwnwnwee
    swswswswnwwwseswneneswswwwswsweswsw
    nwnewsenwsewnwnwsenwwwnwnwwwwnw
    nwwnwswwswswwwwwnwseeswwseswswww
    seweesesenwseseenesesweneewswwse
    seweenewswnwwwwwwsenenewewwsew
    nwnenenenewnenwneneneneneneneswneenwnene
    eeeeeeneswneseeeeeseseeeeeew
    seseesesesewseseeseseseewse
    nweseneseeeseseseeseseesesweeswsenwsw
    seneneswnesenenenenenenenwwnwnwswnenene
    swnwseseseneseswswswsweswswswswsesesewsw
    wseseeneseseeneewenewneenwnenenene
    enwnenesenenewseswnwnwnenenenwnewnene
    eneeeeneeewee
    swwswwwewesewwnesewswswnw
    swwseswseeswnewsewnwnwnwwneeswesw
    weeswseeneeenwsewenenwweeewe
    nwswnwnenwwnwnenewwewwwwseswwenww
    seeseenwnwwewwneswwnewswwwsewsw
    wswswswwswswswswswseswswswwwwnwesw
    newseneeneswnenwwweneneswseeeeene
    seswswseswseeswwswswnwswswswsweswnw
    seseseseseseseseseneseseswseseseseneswsese
    nenenwnwswneeneneenenwnwnwsew
    nwswwnesewswsenwswweenesesew
    wswswwenwswwwsenewswswneweseswsw
    seswseswswswswswnwseseneseseswsw
    wswswswswswswsenweswswswswswneseswsew
    eneeenenweneneneneneneswneeeene
    nwwwwwwwnewwwwwwwsewwwwswe
    eseseseseseseneseseswwnweseseseseese
    eweseeenweeeeseseweeese
    nwnenwwnwwnwnwswnwsenwnwsenwenwnewsw
    nwnenwnenwnenwswnenwswnenwnwnwnenwnenenwe
    eeeeeeeeneweeneeeeeeswe
    esenwnewnwnwnwwsenwnwnewnewwswswne
    eenwseseeeeweseeeeeeeesesese
    seseswswseswswswswseseeneseswswnwswsewswsw
    nwnwnwneneneneneswenenenenenenwnenene
    nwnwwnwnwnwwnwwsenwenwenwnwnwnwneswsw
    sewwnwwnesesesewnewewswewnwwe
    sweswswwnewswswweewswnwwseswsewnwsw
    wsweeeneewenwneswene
    eewseseeeesesenweeseseseseseeene
    wwsweeswwwnwwwwswwewswwenw
    eneeneneeenweeeeeseneeeee
    neseneneswwneneeneeswnewneneseenene
    eeeeeseseseseeewseswsenwwseeeese
    wwswwswnewswwswwswwneswwswwwsw
    wswwwswewswwswswwnwwwwswe
    eseeweeeeeneeeeneeneeweeee
    swswswswswseswnwsweswswswswswswswswswne
    sesesenwwseesenwseseseesesesesesesesese
    seenwnwwneeenwswwsesweeeenwse
    nwwwwwwneseewwwww
    eeeeeseenwsesw
    nwnenwnwnenwnwnenwnwsenenwnenewnwnwnenw
    seseseseseneseeeenwseseeseseeseseew
    swswnwsweseneswwswswswwswswnewswwwsw
    neneeesewneneeseneeneweeeeswnew
    swswswsesesweswweswswswswsweswswwnwsw
    nwswseseseswseswenwseswsesw
    nwneneswnwnenenesenwnwneswne
    sweseseeseseesewseswsesenwseswnwsesw
    swnewsenenweneneneneneesewwnwnenenwnwse
    nwnwsenwnwwnwnwnwnwwnenwwnweenwnwsw
    nwwnwewnwnwnwwwwnwnwwnwnwnwswwnw
    swswswewswseswseseswswswswsesewswsenesesw
    eeweeeeeeeeseeeeeee
    neenwswsenenewneew
    seswneseenwswnwseswswswswswe
    nwnenwnwsesenwnwwnwnwnenwnwnenenwsenwnw
    sesenwsweseeswnwseswsewwseesesesesesw
""".trimIndent()