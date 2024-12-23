package com.drinkscabinet.aoc2020

private data class Bag(
    val colour: String,
    val contains: MutableMap<String, Int> = mutableMapOf(),
    val containedBy: MutableMap<String, Int> = mutableMapOf()
) {

    fun addContained(col: String, qty: Int) {
        contains[col] = qty
        // now find that bag
        val containedBag = bags.computeIfAbsent(col) { Bag(it) }
        containedBag.containedBy[this.colour] = qty
    }

    fun bagsContained(): Long {
        if (contains.isEmpty()) return 0
        var acc = 0L
        for ((containedColour, qty) in contains) {
            val containedBag = bags[containedColour]!!
            acc += qty + containedBag.bagsContained() * qty
        }
        return acc
    }
}

private val bags = mutableMapOf<String, Bag>()

private fun main() {

    input.lines().forEach { parse(it) }

    bags.forEach { println(it) }

    part1("shiny gold")
    println("Part2=${bags["shiny gold"]!!.bagsContained()}")
}

private fun part1(colour: String) {
    println(parents(colour))
    println(parents(colour).size)
}

private fun parents(colour: String): Set<String> {
    val parents = mutableSetOf<String>()
    val bag = bags[colour]!!
    val immediateParents = bag.containedBy.keys
    parents.addAll(immediateParents)
    for (immediateParent in immediateParents) {
        parents.addAll(parents(immediateParent))
    }
    return parents
}

val bagReg = "(\\d+) ([a-z ]+) bag".toRegex()
private fun parse(s: String): Bag {
    val colour = s.substringBefore(" bags contain")
    val bag = bags.computeIfAbsent(colour) { Bag(it) }

    val contains = s.substringAfter("contain ")
    for (matchResult in bagReg.findAll(contains)) {
        val qty = matchResult.groupValues[1]
        val col = matchResult.groupValues[2]

        // Add to this bag
        bag.addContained(col, qty.toInt())
    }
    println(bag)
    return bag
}


private val testInput = """
    light red bags contain 1 bright white bag, 2 muted yellow bags.
    dark orange bags contain 3 bright white bags, 4 muted yellow bags.
    bright white bags contain 1 shiny gold bag.
    muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
    shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
    dark olive bags contain 3 faded blue bags, 4 dotted black bags.
    vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
    faded blue bags contain no other bags.
    dotted black bags contain no other bags.
""".trimIndent()

private val testInput2 = """
    shiny gold bags contain 2 dark red bags.
    dark red bags contain 2 dark orange bags.
    dark orange bags contain 2 dark yellow bags.
    dark yellow bags contain 2 dark green bags.
    dark green bags contain 2 dark blue bags.
    dark blue bags contain 2 dark violet bags.
    dark violet bags contain no other bags.
""".trimIndent()

private val input = """
    bright indigo bags contain 4 shiny turquoise bags, 3 wavy yellow bags.
    dotted turquoise bags contain 3 vibrant salmon bags, 2 dotted maroon bags, 1 bright beige bag, 1 drab white bag.
    vibrant fuchsia bags contain 4 dark salmon bags.
    muted cyan bags contain 2 light gold bags, 5 mirrored salmon bags, 4 plaid blue bags.
    dotted tomato bags contain 3 vibrant gold bags, 4 faded blue bags.
    mirrored black bags contain 5 vibrant beige bags, 3 plaid blue bags.
    dim yellow bags contain 1 clear tan bag, 3 dim red bags.
    plaid lavender bags contain 2 dim coral bags, 4 mirrored maroon bags, 5 wavy lavender bags.
    drab magenta bags contain 3 muted yellow bags, 5 bright turquoise bags.
    mirrored silver bags contain 5 faded tan bags, 4 drab salmon bags, 3 clear chartreuse bags.
    drab lavender bags contain 3 plaid white bags, 5 pale salmon bags, 4 dull salmon bags.
    wavy cyan bags contain 4 dim bronze bags, 5 muted olive bags, 5 plaid gold bags, 4 muted red bags.
    bright fuchsia bags contain 4 faded orange bags, 4 posh teal bags.
    muted indigo bags contain 4 vibrant silver bags, 1 wavy tomato bag.
    pale olive bags contain 1 clear bronze bag, 3 posh black bags, 2 dim blue bags.
    dull crimson bags contain 4 dull tomato bags, 5 wavy green bags, 2 vibrant blue bags, 3 pale brown bags.
    dim salmon bags contain 2 bright black bags, 3 drab salmon bags, 5 vibrant beige bags.
    clear orange bags contain 4 dim coral bags, 4 light chartreuse bags, 5 wavy brown bags, 2 drab yellow bags.
    bright silver bags contain 3 dotted plum bags, 4 shiny salmon bags, 2 drab magenta bags.
    light turquoise bags contain 5 striped beige bags, 5 muted black bags, 1 striped maroon bag.
    light lavender bags contain 3 vibrant coral bags, 3 mirrored crimson bags.
    dull magenta bags contain 4 mirrored gray bags, 5 faded lime bags, 2 dotted green bags, 2 striped olive bags.
    shiny maroon bags contain 3 plaid aqua bags, 1 pale plum bag, 5 plaid salmon bags.
    wavy gray bags contain 5 pale cyan bags, 1 pale gold bag, 2 pale salmon bags.
    dark gray bags contain 2 pale gold bags, 3 muted orange bags, 1 dim maroon bag.
    dim beige bags contain 1 dark yellow bag.
    dull yellow bags contain 4 pale chartreuse bags.
    light aqua bags contain 3 plaid indigo bags, 5 dull black bags.
    vibrant silver bags contain 1 posh beige bag, 2 dim cyan bags, 4 light violet bags, 2 dark tan bags.
    striped aqua bags contain 4 dim olive bags, 4 vibrant magenta bags, 5 pale cyan bags.
    wavy beige bags contain 5 dim red bags, 2 dotted crimson bags, 1 muted orange bag.
    drab orange bags contain 3 dotted red bags, 1 drab yellow bag, 4 clear tan bags, 1 vibrant chartreuse bag.
    mirrored turquoise bags contain 4 striped yellow bags, 1 dark yellow bag.
    posh gold bags contain 2 clear maroon bags, 2 drab black bags.
    posh orange bags contain 3 dark red bags, 1 dull brown bag, 1 dark green bag.
    dim lavender bags contain 2 drab gray bags, 2 shiny brown bags, 2 dull tomato bags, 4 light teal bags.
    muted lavender bags contain 1 striped black bag, 1 vibrant brown bag, 1 wavy yellow bag.
    dotted aqua bags contain 2 muted yellow bags.
    pale aqua bags contain 5 striped blue bags.
    muted silver bags contain 3 pale plum bags, 4 mirrored aqua bags.
    pale teal bags contain 2 shiny beige bags.
    dim coral bags contain 2 vibrant gold bags.
    faded plum bags contain 3 vibrant yellow bags, 2 bright teal bags, 5 light magenta bags.
    light coral bags contain 2 vibrant brown bags, 2 light crimson bags, 2 dotted bronze bags.
    bright plum bags contain 3 wavy olive bags.
    vibrant yellow bags contain 4 muted olive bags, 1 dull tomato bag, 3 bright coral bags.
    muted fuchsia bags contain 3 clear maroon bags, 1 striped aqua bag, 1 pale brown bag.
    dull olive bags contain 4 muted tomato bags, 1 clear silver bag.
    wavy brown bags contain 2 dim cyan bags, 3 dim green bags, 3 faded chartreuse bags.
    bright lavender bags contain 5 dim lavender bags, 5 shiny turquoise bags, 4 clear turquoise bags.
    dim maroon bags contain 5 shiny coral bags, 5 pale white bags, 4 dim cyan bags.
    vibrant gold bags contain 2 posh crimson bags, 3 striped olive bags.
    dotted gold bags contain 5 faded teal bags.
    dull red bags contain 5 bright beige bags.
    striped purple bags contain 1 shiny brown bag, 1 light orange bag.
    dotted yellow bags contain 2 striped aqua bags, 2 muted olive bags, 4 shiny orange bags.
    plaid red bags contain 4 clear teal bags, 4 vibrant indigo bags, 2 faded tan bags.
    striped magenta bags contain 2 striped chartreuse bags, 5 drab red bags.
    dim aqua bags contain 1 shiny coral bag, 2 faded teal bags, 2 plaid cyan bags, 1 plaid salmon bag.
    vibrant tan bags contain 3 shiny silver bags, 4 faded tan bags.
    clear red bags contain 4 dotted gold bags.
    faded tan bags contain 4 plaid salmon bags, 4 plaid violet bags.
    faded maroon bags contain 3 mirrored turquoise bags, 1 dim black bag, 5 posh lavender bags.
    striped yellow bags contain 2 plaid bronze bags.
    light teal bags contain 3 clear blue bags, 5 pale maroon bags, 4 plaid white bags, 5 wavy tomato bags.
    dotted indigo bags contain 4 dim aqua bags, 4 light coral bags, 1 posh tan bag, 1 mirrored gold bag.
    clear maroon bags contain 2 drab red bags.
    light yellow bags contain 5 wavy coral bags, 2 light chartreuse bags, 5 dull lime bags.
    faded blue bags contain 4 vibrant yellow bags.
    dim purple bags contain 3 clear plum bags, 2 plaid green bags.
    plaid indigo bags contain 2 faded lime bags, 4 mirrored green bags, 5 dull plum bags.
    posh salmon bags contain 3 vibrant violet bags.
    dim olive bags contain 5 drab green bags.
    wavy violet bags contain 3 light chartreuse bags, 5 muted olive bags.
    dark magenta bags contain 3 wavy red bags.
    posh violet bags contain 5 faded silver bags, 4 wavy tomato bags, 3 mirrored salmon bags.
    posh yellow bags contain 3 plaid bronze bags.
    dotted lavender bags contain 2 plaid gray bags, 5 dull beige bags, 2 vibrant chartreuse bags, 3 muted chartreuse bags.
    mirrored aqua bags contain 1 striped coral bag, 5 plaid violet bags, 2 bright coral bags, 5 pale orange bags.
    clear lavender bags contain 2 posh chartreuse bags.
    vibrant plum bags contain 5 wavy tomato bags, 3 posh tomato bags, 1 striped chartreuse bag, 1 dim cyan bag.
    faded red bags contain 1 bright green bag.
    muted chartreuse bags contain 2 faded tan bags, 3 shiny violet bags.
    dotted lime bags contain 3 light yellow bags, 4 bright coral bags.
    vibrant turquoise bags contain 1 clear black bag.
    dull fuchsia bags contain 1 wavy maroon bag, 1 posh black bag, 5 light magenta bags, 1 dotted tomato bag.
    vibrant tomato bags contain 4 striped chartreuse bags.
    shiny chartreuse bags contain 4 faded gray bags, 4 dark tan bags, 5 posh crimson bags.
    mirrored white bags contain 1 light blue bag, 2 muted gold bags.
    light magenta bags contain no other bags.
    dark fuchsia bags contain 2 shiny coral bags, 3 pale tomato bags.
    mirrored maroon bags contain 1 dim indigo bag.
    plaid black bags contain 3 muted tomato bags.
    dotted magenta bags contain 5 dark aqua bags.
    shiny bronze bags contain 3 dim green bags.
    drab violet bags contain 4 dotted bronze bags.
    dark cyan bags contain no other bags.
    dim violet bags contain 3 mirrored salmon bags, 2 shiny plum bags, 3 plaid salmon bags.
    plaid teal bags contain 5 dull indigo bags.
    dull teal bags contain 4 posh teal bags, 3 plaid plum bags, 3 dim lavender bags.
    plaid turquoise bags contain 4 drab yellow bags, 1 vibrant lavender bag, 2 vibrant yellow bags, 5 light violet bags.
    posh fuchsia bags contain 4 vibrant gold bags, 4 shiny silver bags.
    clear purple bags contain 1 faded tomato bag.
    mirrored coral bags contain 1 striped black bag, 3 plaid chartreuse bags.
    bright beige bags contain 4 faded lavender bags, 1 faded teal bag, 3 dark red bags, 1 pale maroon bag.
    bright yellow bags contain 4 dark orange bags, 2 muted tomato bags.
    dark chartreuse bags contain 4 mirrored gold bags, 4 dark tan bags, 5 posh yellow bags.
    posh green bags contain 4 bright bronze bags, 3 faded aqua bags, 1 shiny lime bag, 2 dotted magenta bags.
    posh brown bags contain 3 drab gray bags.
    pale lavender bags contain 2 pale gold bags, 4 dark orange bags.
    dim bronze bags contain 4 dull white bags.
    bright teal bags contain 1 mirrored gray bag, 4 faded indigo bags, 2 dim cyan bags, 1 posh plum bag.
    striped orange bags contain 4 vibrant plum bags, 4 shiny cyan bags, 5 pale beige bags, 4 dim beige bags.
    vibrant cyan bags contain 3 posh plum bags, 1 bright teal bag.
    drab gold bags contain 1 wavy orange bag.
    shiny salmon bags contain 5 faded indigo bags, 3 bright turquoise bags, 3 pale violet bags, 4 dotted coral bags.
    drab blue bags contain 4 posh fuchsia bags.
    dark gold bags contain 1 clear black bag, 1 dark chartreuse bag, 1 faded lime bag, 2 bright olive bags.
    striped gray bags contain 4 bright coral bags, 4 striped coral bags, 1 muted gold bag.
    bright gold bags contain 2 plaid fuchsia bags, 5 striped olive bags, 2 mirrored tomato bags, 5 muted tomato bags.
    wavy lavender bags contain 1 pale violet bag, 1 dotted gray bag.
    wavy orange bags contain 3 bright fuchsia bags, 4 posh yellow bags, 3 vibrant brown bags, 5 posh beige bags.
    bright bronze bags contain 3 pale blue bags, 2 shiny cyan bags, 2 vibrant tan bags, 5 posh crimson bags.
    dotted teal bags contain 1 plaid indigo bag, 4 posh salmon bags.
    muted salmon bags contain 3 wavy maroon bags, 2 dotted olive bags.
    dotted crimson bags contain 2 dotted bronze bags, 1 bright yellow bag, 2 dark cyan bags, 5 clear salmon bags.
    striped tan bags contain 2 posh violet bags, 5 dark silver bags, 5 light teal bags.
    pale maroon bags contain 1 dark cyan bag, 4 faded indigo bags.
    muted crimson bags contain 2 faded cyan bags.
    dull tomato bags contain 4 muted teal bags, 5 posh plum bags.
    light crimson bags contain 3 light gold bags.
    bright orange bags contain 5 plaid indigo bags, 5 plaid beige bags, 1 light teal bag.
    posh red bags contain 4 shiny plum bags.
    light bronze bags contain 5 dark green bags, 4 shiny silver bags.
    dull violet bags contain 1 mirrored gray bag, 4 shiny crimson bags.
    posh silver bags contain 2 dull maroon bags.
    light salmon bags contain 3 dim plum bags.
    vibrant olive bags contain 2 faded aqua bags, 4 faded blue bags.
    pale orange bags contain 5 bright coral bags, 5 dark cyan bags, 2 dull plum bags, 4 dim cyan bags.
    vibrant green bags contain 5 dark orange bags, 1 drab yellow bag, 3 dotted plum bags.
    dull lavender bags contain 2 dark tomato bags.
    dim tan bags contain 1 muted teal bag, 4 dull white bags, 1 shiny gold bag.
    plaid white bags contain 3 faded silver bags.
    pale lime bags contain 4 plaid bronze bags, 1 posh white bag.
    mirrored crimson bags contain 4 dim tan bags, 3 vibrant turquoise bags, 1 faded lime bag, 5 striped yellow bags.
    muted bronze bags contain 3 plaid green bags, 4 drab yellow bags, 5 plaid purple bags.
    clear black bags contain 4 dark yellow bags.
    drab plum bags contain 5 light cyan bags.
    plaid yellow bags contain 1 bright silver bag, 1 light bronze bag.
    bright green bags contain 3 posh gray bags, 1 mirrored black bag.
    wavy lime bags contain 1 pale white bag, 2 dim orange bags, 4 dull bronze bags.
    striped turquoise bags contain 2 drab lavender bags.
    dotted chartreuse bags contain 1 posh violet bag, 2 mirrored aqua bags.
    faded olive bags contain 1 bright bronze bag, 3 mirrored tan bags, 3 vibrant silver bags.
    dull green bags contain 4 plaid olive bags.
    bright salmon bags contain 5 mirrored salmon bags, 4 muted teal bags, 1 vibrant yellow bag, 3 bright magenta bags.
    light plum bags contain 4 dull turquoise bags, 3 dim bronze bags.
    mirrored red bags contain 2 dull blue bags.
    dark maroon bags contain 4 clear yellow bags.
    pale yellow bags contain 1 dull crimson bag, 1 clear black bag, 5 shiny lime bags.
    striped lime bags contain 1 bright aqua bag, 1 faded lime bag, 2 posh coral bags.
    drab tomato bags contain 3 shiny aqua bags, 2 striped crimson bags, 5 bright coral bags, 3 dull lime bags.
    striped silver bags contain 3 dim beige bags, 2 bright tomato bags.
    posh crimson bags contain 5 plaid bronze bags, 5 muted teal bags, 1 dark cyan bag.
    muted olive bags contain 5 muted gold bags, 1 bright coral bag, 4 muted tomato bags, 2 pale maroon bags.
    drab gray bags contain 2 shiny silver bags, 5 plaid cyan bags.
    dark tomato bags contain 3 bright tomato bags, 3 drab yellow bags.
    pale beige bags contain 5 striped blue bags, 5 plaid tomato bags, 2 mirrored indigo bags.
    striped bronze bags contain 3 wavy tomato bags, 1 dark brown bag, 4 plaid salmon bags, 5 dark magenta bags.
    dotted green bags contain 3 faded orange bags, 1 striped olive bag, 2 dark cyan bags, 2 bright coral bags.
    dull lime bags contain 3 wavy black bags, 4 shiny tan bags, 2 clear crimson bags, 1 dark cyan bag.
    light cyan bags contain 5 light violet bags, 5 posh black bags.
    bright crimson bags contain 1 plaid tomato bag, 5 faded green bags, 2 posh chartreuse bags.
    bright magenta bags contain 3 wavy red bags, 4 bright lime bags.
    shiny orange bags contain 4 light blue bags, 3 dotted green bags, 3 shiny brown bags.
    dim tomato bags contain 4 vibrant green bags.
    drab teal bags contain 4 dull tomato bags, 4 shiny coral bags, 4 pale silver bags.
    mirrored blue bags contain 1 wavy chartreuse bag, 1 dull plum bag, 1 plaid bronze bag.
    shiny gold bags contain 1 pale maroon bag, 3 plaid blue bags, 5 dull tan bags.
    clear bronze bags contain 1 pale coral bag, 1 light yellow bag.
    wavy bronze bags contain 5 posh turquoise bags, 4 mirrored tan bags.
    drab chartreuse bags contain 4 dark lavender bags, 4 clear silver bags, 4 dotted tan bags, 5 posh silver bags.
    vibrant orange bags contain 4 bright black bags.
    shiny indigo bags contain 5 striped coral bags.
    clear beige bags contain 4 striped olive bags, 5 clear indigo bags, 3 dark cyan bags.
    plaid brown bags contain 3 mirrored bronze bags.
    light olive bags contain 5 dark white bags, 1 plaid red bag.
    shiny fuchsia bags contain 4 dark cyan bags, 3 pale chartreuse bags, 5 light fuchsia bags.
    bright black bags contain 5 plaid white bags, 3 plaid cyan bags.
    mirrored beige bags contain 1 dim plum bag.
    light black bags contain 3 bright silver bags, 3 wavy chartreuse bags, 4 bright chartreuse bags.
    vibrant coral bags contain 1 faded tomato bag, 3 striped coral bags.
    muted beige bags contain 4 striped olive bags.
    dotted black bags contain 2 muted crimson bags, 4 plaid olive bags.
    pale chartreuse bags contain 2 muted beige bags.
    wavy maroon bags contain 4 vibrant cyan bags, 5 posh white bags, 2 shiny black bags.
    faded brown bags contain 2 mirrored violet bags, 4 wavy silver bags.
    dotted white bags contain 1 mirrored purple bag.
    striped salmon bags contain 2 posh tomato bags, 1 muted silver bag, 3 dull violet bags, 4 mirrored plum bags.
    striped red bags contain 2 posh bronze bags.
    clear teal bags contain 4 vibrant aqua bags, 1 plaid lime bag, 3 bright lime bags, 1 posh beige bag.
    mirrored cyan bags contain 5 light green bags.
    muted teal bags contain no other bags.
    drab bronze bags contain 4 dim salmon bags, 1 shiny violet bag, 1 dotted white bag, 3 wavy yellow bags.
    pale fuchsia bags contain 4 plaid magenta bags.
    mirrored tomato bags contain 1 shiny gray bag, 5 dull lime bags, 5 shiny turquoise bags, 1 clear crimson bag.
    clear cyan bags contain 4 mirrored tomato bags, 5 bright lime bags.
    clear gray bags contain 4 faded plum bags, 4 posh yellow bags, 2 clear violet bags, 4 plaid red bags.
    dotted brown bags contain 2 dotted maroon bags.
    dotted salmon bags contain 5 striped bronze bags, 1 shiny bronze bag, 5 light olive bags, 2 striped magenta bags.
    dark red bags contain 5 faded orange bags.
    dull brown bags contain 5 dim green bags, 5 drab salmon bags.
    faded white bags contain 2 faded blue bags.
    pale blue bags contain 1 mirrored lime bag.
    striped lavender bags contain 1 posh white bag, 5 faded magenta bags, 5 drab crimson bags.
    shiny olive bags contain 2 posh tomato bags, 1 faded indigo bag.
    vibrant white bags contain 1 dark purple bag, 5 light lime bags.
    light purple bags contain 4 shiny black bags.
    plaid chartreuse bags contain 5 dim gray bags, 3 dull magenta bags.
    faded violet bags contain 2 faded tomato bags, 1 dark aqua bag, 2 pale lavender bags.
    drab maroon bags contain 1 plaid fuchsia bag.
    drab turquoise bags contain 5 dull indigo bags, 1 striped tomato bag, 4 dull cyan bags, 4 vibrant plum bags.
    shiny plum bags contain 3 drab salmon bags, 5 wavy tomato bags.
    faded chartreuse bags contain 5 posh black bags.
    wavy indigo bags contain 4 dim cyan bags, 3 vibrant tan bags.
    pale salmon bags contain 1 wavy olive bag, 4 pale cyan bags, 2 faded tomato bags, 3 vibrant tan bags.
    dull bronze bags contain 3 wavy red bags, 2 plaid red bags, 2 muted purple bags.
    posh plum bags contain no other bags.
    mirrored chartreuse bags contain 1 clear gray bag.
    dull chartreuse bags contain 1 faded teal bag, 2 wavy orange bags, 1 bright indigo bag.
    muted plum bags contain 4 faded silver bags, 5 shiny tan bags.
    striped plum bags contain 3 posh beige bags.
    clear green bags contain 2 dark lime bags, 3 muted purple bags, 2 striped bronze bags.
    dull maroon bags contain 3 faded lavender bags, 3 mirrored white bags, 2 light blue bags, 4 dull tomato bags.
    faded silver bags contain 3 dim plum bags, 2 pale orange bags, 3 plaid blue bags.
    dull white bags contain 1 pale indigo bag, 2 bright turquoise bags.
    mirrored purple bags contain 2 light bronze bags, 1 dark orange bag, 2 dark fuchsia bags, 2 striped violet bags.
    plaid tomato bags contain 5 bright coral bags.
    shiny teal bags contain 4 faded red bags, 5 mirrored green bags, 4 shiny coral bags.
    plaid purple bags contain 3 drab yellow bags, 1 plaid green bag, 4 dim plum bags.
    dark brown bags contain 5 dull plum bags, 5 dotted bronze bags, 2 wavy tomato bags.
    dotted plum bags contain 2 faded indigo bags.
    dim gray bags contain 4 dotted gold bags, 5 mirrored tomato bags.
    vibrant teal bags contain 2 posh black bags.
    dotted violet bags contain 3 mirrored yellow bags, 3 mirrored orange bags.
    wavy white bags contain 1 pale indigo bag, 5 vibrant lavender bags, 2 dim tan bags.
    posh teal bags contain 1 mirrored brown bag, 3 bright coral bags.
    pale indigo bags contain 2 posh crimson bags.
    muted magenta bags contain 1 muted black bag, 4 dull turquoise bags.
    drab brown bags contain 5 striped gold bags.
    posh beige bags contain 3 shiny gold bags, 1 shiny cyan bag, 1 posh crimson bag, 2 wavy yellow bags.
    dark lavender bags contain 5 dim fuchsia bags, 5 mirrored beige bags, 1 dark indigo bag, 3 dull brown bags.
    faded cyan bags contain 4 vibrant aqua bags, 2 dark magenta bags, 1 dark yellow bag, 3 wavy red bags.
    bright brown bags contain 1 wavy orange bag, 3 dim plum bags.
    wavy coral bags contain 4 posh yellow bags, 4 light lime bags.
    bright maroon bags contain 4 clear turquoise bags, 4 posh salmon bags, 1 striped gold bag.
    wavy gold bags contain 5 pale purple bags, 5 plaid maroon bags, 2 light purple bags, 2 faded gray bags.
    dark yellow bags contain 3 plaid green bags, 4 dark teal bags, 4 dark plum bags, 4 vibrant yellow bags.
    plaid aqua bags contain 5 muted teal bags, 4 posh indigo bags.
    bright tomato bags contain 5 posh violet bags, 4 wavy tomato bags.
    drab red bags contain 5 vibrant magenta bags, 2 dark orange bags.
    dull salmon bags contain 5 drab gray bags, 3 light lime bags.
    drab indigo bags contain 1 faded red bag, 5 dull maroon bags.
    shiny white bags contain 3 light blue bags, 5 bright indigo bags, 4 plaid purple bags.
    plaid lime bags contain 5 clear turquoise bags, 2 plaid cyan bags, 3 dotted olive bags.
    wavy fuchsia bags contain 1 light violet bag, 4 dark tomato bags, 2 bright green bags.
    vibrant magenta bags contain 5 mirrored gold bags, 3 dotted red bags.
    light red bags contain 2 posh crimson bags, 3 wavy orange bags, 1 wavy yellow bag.
    dim crimson bags contain 3 striped tan bags, 3 pale blue bags, 2 drab yellow bags.
    dark teal bags contain 1 plaid bronze bag, 1 vibrant aqua bag.
    shiny magenta bags contain 3 light salmon bags, 4 dark bronze bags, 1 shiny plum bag, 4 clear blue bags.
    clear lime bags contain 3 vibrant salmon bags, 5 muted magenta bags, 4 posh black bags.
    dull coral bags contain 1 shiny coral bag, 2 bright black bags.
    shiny black bags contain 1 dotted purple bag, 2 posh teal bags.
    striped maroon bags contain 3 drab red bags, 5 light gold bags.
    vibrant brown bags contain 3 dim green bags, 3 posh white bags, 3 dotted green bags.
    shiny brown bags contain 1 posh bronze bag.
    dark lime bags contain 5 light magenta bags, 5 shiny turquoise bags.
    vibrant crimson bags contain 2 striped bronze bags.
    posh coral bags contain 2 dim silver bags.
    dull orange bags contain 5 clear coral bags, 4 shiny tan bags.
    striped beige bags contain 1 dim olive bag, 3 plaid chartreuse bags, 4 dark chartreuse bags.
    faded indigo bags contain 5 dark cyan bags, 1 light violet bag, 2 bright coral bags.
    bright aqua bags contain 1 clear salmon bag, 1 dark orange bag, 5 faded gold bags.
    plaid orange bags contain 5 vibrant cyan bags, 5 plaid silver bags, 2 wavy olive bags, 2 bright olive bags.
    posh bronze bags contain 1 faded indigo bag.
    plaid cyan bags contain 1 muted teal bag, 5 muted olive bags, 3 dull tomato bags, 5 light magenta bags.
    striped coral bags contain 2 posh black bags.
    light brown bags contain 5 muted lavender bags, 2 muted coral bags, 1 vibrant tan bag, 5 drab lime bags.
    shiny tomato bags contain 4 bright turquoise bags, 1 muted tomato bag, 2 clear fuchsia bags.
    dark green bags contain 4 vibrant magenta bags.
    drab beige bags contain 4 faded lime bags.
    shiny turquoise bags contain 3 dark tan bags, 3 faded lavender bags, 5 faded tomato bags.
    mirrored gold bags contain 4 dull magenta bags, 2 clear turquoise bags, 2 dull white bags, 3 dull tomato bags.
    dotted purple bags contain 2 shiny brown bags, 1 bright lime bag, 4 faded lavender bags, 2 faded indigo bags.
    shiny silver bags contain 1 wavy tomato bag, 1 dull gold bag, 2 striped yellow bags.
    mirrored salmon bags contain 2 posh beige bags.
    pale red bags contain 4 dotted crimson bags, 3 posh teal bags, 4 dull maroon bags.
    vibrant salmon bags contain 3 striped maroon bags.
    dim fuchsia bags contain 5 pale plum bags, 3 light fuchsia bags, 2 bright tomato bags, 2 dark violet bags.
    clear turquoise bags contain 4 bright lime bags, 3 dark magenta bags.
    wavy aqua bags contain 1 vibrant turquoise bag, 5 clear gold bags, 1 muted indigo bag, 4 striped gray bags.
    mirrored fuchsia bags contain 1 posh maroon bag, 2 clear salmon bags.
    bright purple bags contain 2 vibrant tomato bags.
    striped blue bags contain 5 dark white bags, 5 wavy orange bags, 5 dark magenta bags.
    dim plum bags contain 2 pale maroon bags, 1 mirrored blue bag, 5 bright coral bags.
    clear white bags contain 1 muted cyan bag, 3 mirrored gold bags.
    vibrant aqua bags contain 2 dull plum bags, 4 muted tomato bags.
    dark black bags contain 2 posh green bags.
    muted white bags contain 2 faded green bags, 1 dull gray bag, 2 striped coral bags, 4 dim black bags.
    posh tomato bags contain 2 mirrored blue bags, 3 posh red bags, 2 faded tan bags, 3 clear tan bags.
    muted red bags contain 5 plaid crimson bags, 4 plaid turquoise bags, 5 clear gold bags.
    pale silver bags contain 3 bright turquoise bags.
    posh lavender bags contain 4 mirrored violet bags.
    pale tan bags contain 5 mirrored bronze bags.
    drab aqua bags contain 2 posh bronze bags, 1 vibrant orange bag, 1 light magenta bag.
    clear magenta bags contain 4 bright olive bags, 5 dim purple bags.
    light silver bags contain 4 vibrant brown bags, 3 dim olive bags, 3 posh bronze bags.
    dark plum bags contain 5 vibrant gold bags.
    posh tan bags contain 3 posh purple bags.
    mirrored bronze bags contain 1 wavy white bag, 5 bright beige bags, 4 bright turquoise bags, 1 bright yellow bag.
    dotted fuchsia bags contain 2 faded indigo bags.
    pale turquoise bags contain 3 vibrant lime bags, 1 vibrant fuchsia bag, 1 dim black bag.
    light lime bags contain 1 pale orange bag, 3 pale maroon bags, 4 dull maroon bags.
    bright gray bags contain 5 mirrored bronze bags, 4 dotted purple bags, 5 bright beige bags, 5 posh bronze bags.
    dull blue bags contain 1 dull gold bag, 3 shiny coral bags.
    dark salmon bags contain 2 dark teal bags, 3 striped bronze bags, 2 pale plum bags, 5 faded teal bags.
    light indigo bags contain 1 light green bag, 4 faded fuchsia bags.
    light fuchsia bags contain 5 shiny black bags, 2 plaid turquoise bags.
    vibrant gray bags contain 2 faded teal bags, 1 dark brown bag, 1 dark magenta bag, 3 pale white bags.
    faded fuchsia bags contain 5 pale cyan bags, 2 wavy aqua bags.
    wavy purple bags contain 1 posh fuchsia bag, 2 clear turquoise bags.
    dark purple bags contain 5 dotted olive bags, 4 shiny tan bags, 4 clear blue bags.
    vibrant chartreuse bags contain 4 clear teal bags, 1 plaid coral bag.
    plaid silver bags contain 4 wavy beige bags, 5 bright aqua bags, 1 vibrant yellow bag.
    dull turquoise bags contain 1 faded orange bag, 4 vibrant blue bags, 1 vibrant brown bag, 1 clear gold bag.
    bright violet bags contain 4 plaid fuchsia bags, 1 clear blue bag, 4 dull purple bags, 1 dark yellow bag.
    wavy turquoise bags contain 4 plaid violet bags, 4 drab gray bags.
    muted tan bags contain 5 posh white bags, 3 drab salmon bags, 3 light tan bags, 1 dark cyan bag.
    light maroon bags contain 4 dotted turquoise bags, 5 dim tan bags.
    plaid gray bags contain 3 dotted cyan bags, 4 striped blue bags, 3 shiny white bags.
    dotted olive bags contain 5 posh plum bags, 3 mirrored lime bags, 4 clear turquoise bags.
    plaid crimson bags contain 2 faded lavender bags, 3 shiny tan bags.
    clear brown bags contain 5 faded lavender bags.
    shiny lavender bags contain 5 faded gray bags, 5 posh chartreuse bags, 5 dim purple bags.
    dotted cyan bags contain 2 mirrored blue bags, 5 plaid salmon bags, 1 faded orange bag, 4 dull tomato bags.
    posh purple bags contain 4 dark beige bags, 3 dim maroon bags, 5 bright beige bags, 5 drab beige bags.
    vibrant red bags contain 4 dark orange bags.
    dotted red bags contain 3 plaid blue bags, 5 vibrant lavender bags.
    muted green bags contain 2 clear fuchsia bags.
    pale purple bags contain 3 muted tomato bags.
    muted turquoise bags contain 2 light coral bags, 1 mirrored gray bag.
    posh chartreuse bags contain 1 dark orange bag, 3 striped olive bags, 5 faded teal bags.
    bright chartreuse bags contain 3 vibrant maroon bags.
    shiny cyan bags contain 3 muted olive bags, 1 muted tomato bag.
    mirrored teal bags contain 2 pale black bags.
    plaid bronze bags contain 3 muted gold bags, 2 faded indigo bags.
    dim teal bags contain 2 plaid tomato bags.
    clear tan bags contain 2 dim green bags, 1 mirrored gray bag, 3 dotted cyan bags.
    faded crimson bags contain 3 vibrant lavender bags.
    wavy olive bags contain 4 mirrored lime bags.
    pale plum bags contain 2 plaid turquoise bags, 5 shiny indigo bags, 3 faded blue bags.
    dark white bags contain 5 shiny black bags.
    drab green bags contain 5 muted gold bags.
    vibrant bronze bags contain 5 bright olive bags, 1 dull gray bag, 3 dark purple bags.
    pale green bags contain 4 muted teal bags.
    clear plum bags contain 4 posh violet bags, 1 light blue bag.
    clear violet bags contain 4 dark aqua bags, 5 muted black bags.
    posh black bags contain no other bags.
    shiny gray bags contain 3 vibrant lavender bags.
    dark crimson bags contain 4 dull tan bags, 1 shiny cyan bag, 5 vibrant indigo bags.
    dim brown bags contain 3 faded silver bags, 3 dark purple bags.
    dotted tan bags contain 1 dim indigo bag, 2 vibrant teal bags, 4 bright beige bags, 4 clear silver bags.
    light gray bags contain 1 faded indigo bag, 1 light tan bag, 1 mirrored lime bag.
    bright lime bags contain 5 drab yellow bags, 3 plaid cyan bags, 5 faded orange bags.
    posh maroon bags contain 3 drab teal bags, 2 dotted beige bags.
    shiny beige bags contain 5 vibrant olive bags, 1 dull blue bag.
    striped fuchsia bags contain 5 wavy aqua bags.
    dim indigo bags contain 3 clear aqua bags, 3 clear crimson bags.
    dull silver bags contain 3 muted lavender bags.
    wavy teal bags contain 1 clear gray bag, 2 shiny tan bags, 4 shiny brown bags.
    dull tan bags contain 1 clear silver bag.
    vibrant maroon bags contain 1 faded lavender bag, 4 bright coral bags, 5 vibrant coral bags.
    faded magenta bags contain 5 shiny black bags, 3 light crimson bags.
    dim magenta bags contain 2 shiny tan bags.
    pale coral bags contain 1 striped black bag, 2 posh violet bags.
    clear salmon bags contain 5 wavy tomato bags, 4 dull gold bags, 3 dotted olive bags, 2 bright turquoise bags.
    clear silver bags contain 1 bright coral bag, 3 light magenta bags, 4 muted teal bags, 4 light violet bags.
    mirrored lavender bags contain 4 faded cyan bags, 4 dotted fuchsia bags, 4 mirrored salmon bags, 5 muted beige bags.
    muted lime bags contain 5 vibrant olive bags, 1 light chartreuse bag, 5 faded yellow bags, 5 drab plum bags.
    plaid plum bags contain 2 faded tomato bags.
    posh cyan bags contain 4 drab green bags, 3 posh plum bags, 4 vibrant gold bags, 5 vibrant aqua bags.
    faded orange bags contain no other bags.
    plaid gold bags contain 3 vibrant teal bags.
    light chartreuse bags contain 5 vibrant cyan bags, 2 drab green bags, 3 shiny plum bags.
    plaid olive bags contain 1 dim lime bag.
    wavy yellow bags contain 2 posh bronze bags, 3 plaid blue bags, 2 posh crimson bags, 3 muted gold bags.
    striped tomato bags contain 3 dim lime bags, 5 plaid crimson bags, 4 pale lime bags.
    clear aqua bags contain 3 muted tomato bags, 2 striped olive bags.
    light tan bags contain 1 pale gold bag.
    mirrored yellow bags contain 2 dim maroon bags, 1 posh violet bag, 3 drab salmon bags.
    clear crimson bags contain 3 pale cyan bags.
    dull plum bags contain 4 posh black bags, 4 dark cyan bags, 4 dull olive bags, 5 light violet bags.
    wavy red bags contain 1 pale maroon bag.
    faded yellow bags contain 4 bright chartreuse bags, 3 striped gold bags.
    faded purple bags contain 2 posh yellow bags, 2 shiny black bags, 1 dim magenta bag, 5 vibrant blue bags.
    vibrant lime bags contain 5 drab tan bags, 5 pale beige bags, 1 faded turquoise bag, 4 dull gold bags.
    mirrored violet bags contain 1 mirrored bronze bag.
    drab crimson bags contain 2 dim indigo bags.
    dim gold bags contain 5 plaid green bags.
    shiny yellow bags contain 2 faded black bags, 1 posh crimson bag, 4 plaid turquoise bags, 3 pale chartreuse bags.
    mirrored tan bags contain 3 posh red bags.
    drab salmon bags contain 3 clear turquoise bags, 2 striped plum bags, 2 plaid turquoise bags.
    vibrant beige bags contain 1 plaid violet bag.
    striped crimson bags contain 3 striped bronze bags.
    light tomato bags contain 5 faded orange bags, 5 mirrored bronze bags, 1 pale orange bag.
    wavy magenta bags contain 5 drab magenta bags, 2 vibrant tan bags, 2 striped indigo bags.
    dotted beige bags contain 2 posh bronze bags, 1 faded silver bag.
    faded gold bags contain 5 vibrant teal bags, 4 dim plum bags, 2 vibrant yellow bags.
    dark blue bags contain 4 faded plum bags, 3 vibrant crimson bags, 1 vibrant maroon bag.
    striped olive bags contain 3 posh beige bags, 4 dull white bags.
    striped black bags contain 2 plaid bronze bags, 4 posh bronze bags.
    plaid green bags contain 2 dull magenta bags, 2 vibrant indigo bags, 1 dim silver bag.
    dark silver bags contain 2 wavy brown bags.
    wavy plum bags contain 4 plaid violet bags.
    plaid violet bags contain 5 posh crimson bags, 2 dark tan bags.
    plaid salmon bags contain 4 faded lavender bags, 1 dull olive bag, 4 posh crimson bags, 2 posh plum bags.
    mirrored indigo bags contain 4 shiny orange bags, 4 dim green bags.
    striped violet bags contain 1 bright teal bag, 1 bright black bag.
    shiny coral bags contain 3 bright coral bags.
    pale bronze bags contain 4 vibrant gray bags, 1 striped maroon bag, 4 dark magenta bags.
    drab olive bags contain 4 plaid beige bags.
    dull beige bags contain 4 posh orange bags.
    muted yellow bags contain 2 muted teal bags, 5 bright coral bags, 4 mirrored beige bags, 1 wavy red bag.
    drab tan bags contain 4 bright aqua bags, 4 dark crimson bags, 4 muted coral bags.
    mirrored brown bags contain 1 muted gold bag.
    striped gold bags contain 1 clear olive bag, 5 muted purple bags.
    dark orange bags contain 1 mirrored aqua bag, 5 pale brown bags, 3 shiny turquoise bags, 5 dim plum bags.
    dim green bags contain 1 posh crimson bag, 5 wavy yellow bags, 3 mirrored blue bags, 3 mirrored brown bags.
    light green bags contain 1 dull cyan bag, 1 striped gold bag, 3 dull maroon bags.
    faded tomato bags contain 5 posh bronze bags, 3 wavy tomato bags, 2 mirrored gray bags.
    pale crimson bags contain 3 wavy indigo bags, 5 drab white bags.
    plaid coral bags contain 2 bright maroon bags, 2 pale chartreuse bags, 5 bright beige bags.
    dark beige bags contain 3 plaid plum bags, 4 light blue bags, 1 vibrant plum bag.
    bright coral bags contain no other bags.
    wavy blue bags contain 5 mirrored plum bags, 5 plaid yellow bags, 1 bright aqua bag.
    shiny purple bags contain 1 wavy fuchsia bag, 4 wavy bronze bags.
    light violet bags contain no other bags.
    muted gray bags contain 4 dull white bags.
    faded salmon bags contain 2 dotted bronze bags.
    mirrored green bags contain 4 dark cyan bags, 1 faded silver bag.
    faded teal bags contain 5 clear silver bags, 3 muted olive bags, 4 light magenta bags, 3 dark cyan bags.
    muted blue bags contain 2 bright teal bags, 1 vibrant tan bag.
    dim blue bags contain 4 dark orange bags, 3 bright lime bags, 5 clear salmon bags, 1 striped blue bag.
    plaid beige bags contain 4 posh fuchsia bags, 1 posh violet bag, 1 drab gray bag, 4 pale white bags.
    wavy tomato bags contain 2 dark cyan bags, 5 clear silver bags.
    plaid fuchsia bags contain 4 wavy turquoise bags, 2 clear salmon bags, 1 bright turquoise bag, 3 plaid blue bags.
    bright tan bags contain 4 dotted green bags, 1 dull orange bag, 1 mirrored violet bag, 4 dim green bags.
    drab white bags contain 2 dim tan bags.
    light white bags contain 1 pale gold bag, 4 posh magenta bags.
    clear olive bags contain 4 plaid cyan bags.
    light gold bags contain 1 muted gold bag.
    shiny green bags contain 4 bright maroon bags.
    drab silver bags contain 5 posh gold bags, 5 drab salmon bags.
    drab yellow bags contain 1 light violet bag, 2 pale maroon bags, 2 faded orange bags, 2 posh black bags.
    dark violet bags contain 4 light cyan bags.
    plaid blue bags contain 4 clear silver bags, 5 plaid bronze bags, 4 shiny tan bags, 2 mirrored gray bags.
    pale white bags contain 5 posh plum bags, 3 pale maroon bags, 3 muted gold bags, 1 dull tan bag.
    dull black bags contain 5 pale lavender bags, 5 wavy coral bags.
    dark indigo bags contain 3 pale orange bags, 5 mirrored lime bags, 5 drab red bags, 4 shiny black bags.
    drab lime bags contain 4 plaid gray bags.
    wavy silver bags contain 5 bright magenta bags, 5 dotted beige bags, 1 dim indigo bag.
    dark turquoise bags contain 2 dim beige bags.
    vibrant purple bags contain 3 pale orange bags, 3 striped olive bags, 5 clear gold bags, 3 wavy orange bags.
    wavy green bags contain 3 dull magenta bags, 4 posh bronze bags, 2 plaid tomato bags.
    faded green bags contain 4 dim silver bags, 3 pale chartreuse bags.
    light blue bags contain 4 mirrored blue bags, 3 dark cyan bags.
    dim turquoise bags contain 1 dull maroon bag, 5 light crimson bags, 4 light gold bags.
    posh turquoise bags contain 3 wavy white bags.
    dull purple bags contain 5 wavy olive bags, 2 dim crimson bags, 2 dotted plum bags.
    dark bronze bags contain 5 muted beige bags, 5 mirrored brown bags.
    pale cyan bags contain 5 muted teal bags, 4 dim plum bags, 3 light gold bags, 5 dark cyan bags.
    plaid maroon bags contain 4 faded blue bags.
    faded black bags contain 1 drab lavender bag, 5 posh purple bags.
    posh indigo bags contain 4 faded tan bags, 1 faded teal bag, 4 pale chartreuse bags, 5 pale gray bags.
    dull gray bags contain 1 light lime bag.
    vibrant blue bags contain 1 dotted olive bag, 5 clear fuchsia bags.
    pale brown bags contain 4 plaid salmon bags, 1 posh yellow bag, 1 faded indigo bag, 2 muted gold bags.
    vibrant indigo bags contain 5 drab yellow bags, 2 dark cyan bags, 1 muted teal bag, 1 striped coral bag.
    wavy chartreuse bags contain 3 muted olive bags, 2 faded teal bags.
    dim chartreuse bags contain 1 wavy white bag, 5 mirrored violet bags, 4 dull fuchsia bags.
    clear chartreuse bags contain 4 drab white bags, 1 muted plum bag.
    drab fuchsia bags contain 4 muted teal bags, 1 shiny lime bag, 2 dotted gold bags.
    striped green bags contain 5 dim tan bags, 5 dark teal bags.
    drab coral bags contain 4 clear tan bags.
    dotted silver bags contain 5 wavy coral bags, 3 bright yellow bags, 4 plaid maroon bags.
    pale black bags contain 3 dim violet bags, 2 mirrored plum bags, 4 dotted maroon bags.
    clear fuchsia bags contain 1 posh black bag, 5 dim magenta bags.
    striped teal bags contain 1 pale red bag, 2 mirrored lime bags, 1 pale blue bag.
    muted maroon bags contain 1 plaid violet bag, 1 faded teal bag.
    faded gray bags contain 3 dim tan bags, 2 drab teal bags.
    dim lime bags contain 5 mirrored gold bags.
    light orange bags contain 3 bright maroon bags.
    bright red bags contain 5 bright brown bags, 1 mirrored tan bag, 5 muted coral bags, 4 striped chartreuse bags.
    shiny tan bags contain 4 posh plum bags, 1 pale maroon bag, 4 faded indigo bags, 3 posh black bags.
    posh blue bags contain 3 clear maroon bags, 3 shiny maroon bags.
    bright blue bags contain 3 light bronze bags, 5 dim silver bags.
    dull indigo bags contain 3 plaid salmon bags, 5 shiny indigo bags, 3 dotted crimson bags, 4 clear tan bags.
    clear blue bags contain 5 mirrored gold bags, 4 plaid bronze bags, 2 dull gold bags, 3 clear crimson bags.
    striped chartreuse bags contain 2 bright magenta bags, 4 bright lime bags.
    muted orange bags contain 1 dull plum bag, 1 posh red bag.
    muted aqua bags contain 2 dotted olive bags, 2 dim violet bags.
    dull gold bags contain 1 posh plum bag.
    striped brown bags contain 4 vibrant coral bags, 3 dull lavender bags.
    clear tomato bags contain 4 light gold bags.
    dull cyan bags contain 3 clear salmon bags, 2 dark lime bags.
    muted coral bags contain 4 striped plum bags, 1 posh green bag, 2 plaid violet bags.
    drab cyan bags contain 3 wavy lime bags.
    vibrant violet bags contain 2 dark orange bags, 1 drab salmon bag, 2 posh beige bags.
    pale tomato bags contain 3 drab beige bags, 1 wavy coral bag.
    light beige bags contain 4 plaid lime bags, 5 dark lavender bags, 4 plaid red bags, 4 drab white bags.
    dim cyan bags contain 5 plaid cyan bags, 5 faded orange bags, 5 posh plum bags, 3 clear silver bags.
    wavy tan bags contain 5 striped cyan bags, 5 plaid violet bags.
    pale gray bags contain 4 clear gold bags, 5 posh turquoise bags.
    plaid magenta bags contain 2 dark green bags, 1 vibrant crimson bag, 3 dotted gray bags.
    faded beige bags contain 3 faded violet bags, 2 dotted green bags, 5 mirrored cyan bags.
    shiny aqua bags contain 4 dotted coral bags, 3 vibrant violet bags, 3 bright maroon bags, 1 shiny cyan bag.
    dim red bags contain 4 plaid blue bags, 2 plaid lime bags, 1 dark red bag.
    pale violet bags contain 3 mirrored white bags, 2 posh white bags.
    faded aqua bags contain 1 faded silver bag, 5 clear fuchsia bags.
    dim silver bags contain 1 muted gold bag, 1 dim tan bag, 3 striped yellow bags, 5 faded indigo bags.
    dotted maroon bags contain 3 bright teal bags, 1 clear white bag.
    dotted blue bags contain 3 dotted plum bags, 2 faded crimson bags, 3 bright coral bags, 2 light fuchsia bags.
    mirrored orange bags contain 2 plaid yellow bags, 2 faded tan bags.
    faded lime bags contain 2 plaid crimson bags, 5 bright coral bags, 2 vibrant gold bags, 2 vibrant aqua bags.
    vibrant black bags contain 4 dim bronze bags, 4 dull tomato bags.
    mirrored gray bags contain 3 dull olive bags, 3 clear silver bags, 3 wavy chartreuse bags.
    clear yellow bags contain 1 shiny green bag.
    bright turquoise bags contain 3 wavy chartreuse bags.
    muted black bags contain 1 muted gold bag.
    faded coral bags contain 1 posh plum bag.
    striped white bags contain 3 clear coral bags, 2 plaid blue bags, 4 plaid aqua bags, 5 drab brown bags.
    bright cyan bags contain 1 faded teal bag, 4 dim coral bags, 5 shiny red bags.
    drab purple bags contain 5 shiny beige bags, 2 shiny indigo bags, 5 vibrant aqua bags, 3 pale purple bags.
    striped cyan bags contain 5 dotted blue bags.
    posh magenta bags contain 5 pale red bags, 1 drab white bag, 5 pale white bags, 5 dotted plum bags.
    clear coral bags contain 3 dark chartreuse bags.
    dull aqua bags contain 4 clear blue bags, 1 dotted green bag.
    muted brown bags contain 2 shiny brown bags, 2 clear fuchsia bags, 5 pale yellow bags, 1 dotted tan bag.
    vibrant lavender bags contain 1 posh crimson bag.
    posh gray bags contain 5 vibrant beige bags, 4 dark red bags.
    wavy salmon bags contain 3 faded coral bags.
    dotted orange bags contain 3 clear cyan bags, 5 shiny silver bags, 2 muted gold bags, 2 dim tomato bags.
    dotted gray bags contain 3 posh bronze bags, 4 shiny tan bags.
    dark tan bags contain 1 dull plum bag, 2 muted teal bags, 3 mirrored brown bags, 3 faded teal bags.
    muted tomato bags contain no other bags.
    muted violet bags contain 1 drab green bag, 5 wavy green bags.
    dotted bronze bags contain 5 dim tan bags.
    shiny violet bags contain 4 posh beige bags, 4 pale lime bags, 1 dim aqua bag.
    clear indigo bags contain 1 wavy olive bag, 5 dim purple bags, 5 striped blue bags.
    clear gold bags contain 5 posh teal bags, 2 dull tomato bags.
    mirrored magenta bags contain 5 clear green bags, 4 vibrant black bags, 2 drab lavender bags.
    wavy black bags contain 3 clear salmon bags, 4 light teal bags, 1 clear fuchsia bag, 5 dull tan bags.
    shiny crimson bags contain 3 plaid blue bags, 2 wavy black bags, 3 mirrored black bags.
    muted gold bags contain no other bags.
    posh aqua bags contain 5 shiny plum bags.
    dim white bags contain 4 shiny white bags, 4 dark tan bags, 2 striped olive bags, 5 clear yellow bags.
    dim black bags contain 3 faded blue bags, 1 dark white bag.
    mirrored olive bags contain 3 mirrored beige bags, 1 striped tomato bag, 3 wavy olive bags.
    bright white bags contain 5 posh teal bags, 2 dark purple bags, 2 bright tan bags.
    mirrored lime bags contain 1 muted tomato bag, 2 dark red bags, 5 plaid bronze bags.
    dark coral bags contain 1 striped chartreuse bag, 5 bright gray bags.
    dotted coral bags contain 3 posh crimson bags, 5 posh fuchsia bags.
    dim orange bags contain 1 dim magenta bag, 3 clear turquoise bags, 4 pale lime bags, 3 faded teal bags.
    mirrored plum bags contain 3 striped teal bags.
    posh olive bags contain 2 plaid lavender bags, 5 shiny red bags.
    shiny blue bags contain 4 plaid turquoise bags, 2 dim red bags.
    faded lavender bags contain 3 dim cyan bags, 5 plaid bronze bags.
    posh white bags contain 1 vibrant aqua bag.
    drab black bags contain 3 vibrant yellow bags, 2 vibrant teal bags, 5 dark teal bags.
    shiny red bags contain 4 dotted tan bags, 1 faded plum bag, 4 drab magenta bags.
    pale gold bags contain 3 dull magenta bags.
    faded turquoise bags contain 1 mirrored maroon bag, 3 vibrant purple bags.
    muted purple bags contain 3 drab red bags, 2 wavy tomato bags, 3 wavy chartreuse bags, 2 dark teal bags.
    posh lime bags contain 5 wavy crimson bags, 4 dull silver bags, 5 plaid magenta bags.
    bright olive bags contain 5 shiny indigo bags.
    faded bronze bags contain 2 dark crimson bags, 2 clear orange bags, 4 striped beige bags.
    dark olive bags contain 4 vibrant chartreuse bags, 4 light coral bags, 1 faded purple bag.
    dark aqua bags contain 4 plaid salmon bags, 4 dim plum bags, 2 dim orange bags.
    pale magenta bags contain 3 dull turquoise bags, 2 mirrored purple bags, 5 clear olive bags, 5 dotted red bags.
    plaid tan bags contain 2 light salmon bags, 3 dim lime bags, 2 dim maroon bags, 4 wavy gray bags.
    shiny lime bags contain 4 striped olive bags, 3 dim coral bags.
    striped indigo bags contain 3 wavy red bags, 5 posh white bags, 5 light tan bags, 1 plaid bronze bag.
    wavy crimson bags contain 2 dull fuchsia bags, 5 striped tomato bags.
""".trimIndent()