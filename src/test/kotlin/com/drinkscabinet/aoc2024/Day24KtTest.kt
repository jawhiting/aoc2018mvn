package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day24KtTest {

    private val testData = """x00: 1
x01: 1
x02: 1
y00: 0
y01: 1
y02: 0

x00 AND y00 -> z00
x01 XOR y01 -> z01
x02 OR y02 -> z02"""

    private val testData2 = """x00: 1
x01: 0
x02: 1
x03: 1
x04: 0
y00: 1
y01: 1
y02: 1
y03: 1
y04: 1

ntg XOR fgs -> mjb
y02 OR x01 -> tnw
kwq OR kpj -> z05
x00 OR x03 -> fst
tgd XOR rvg -> z01
vdt OR tnw -> bfw
bfw AND frj -> z10
ffh OR nrd -> bqk
y00 AND y03 -> djm
y03 OR y00 -> psh
bqk OR frj -> z08
tnw OR fst -> frj
gnj AND tgd -> z11
bfw XOR mjb -> z00
x03 OR x00 -> vdt
gnj AND wpb -> z02
x04 AND y00 -> kjc
djm OR pbm -> qhw
nrd AND vdt -> hwm
kjc AND fst -> rvg
y04 OR y02 -> fgs
y01 AND x02 -> pbm
ntg OR kjc -> kwq
psh XOR fgs -> tgd
qhw XOR tgd -> z09
pbm OR djm -> kpj
x03 XOR y03 -> ffh
x00 XOR y04 -> ntg
bfw OR bqk -> z06
nrd XOR fgs -> wpb
frj XOR qhw -> z04
bqk OR frj -> z07
y03 OR x01 -> nrd
hwm AND bqk -> z03
tgd XOR rvg -> z12
tnw OR pbm -> gnj"""

    private val testData3 = """x00: 0
x01: 1
x02: 0
x03: 1
x04: 0
x05: 1
y00: 0
y01: 0
y02: 1
y03: 1
y04: 0
y05: 1

x00 AND y00 -> z05
x01 AND y01 -> z02
x02 AND y02 -> z01
x03 AND y03 -> z03
x04 AND y04 -> z04
x05 AND y05 -> z00"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals(4, this.part1(testData))
        assertEquals(2024, this.part1(testData2))
        assertEquals(638, this.part1(realData))
    }

    @Test
    fun testPart2() {
        assertEquals(81, this.part2(realData))
        // dhg and z06

        assertEquals(1289, this.part2(realData))
    }

    private fun part1(data: String): Long {
        parse(data)
        wires.forEach { println(it) }
        outputToOperation.forEach { println(it) }

        // Now get each of the z outputs
        val zs = outputToOperation.keys.filter { it.startsWith("z") }.sorted()
        var sum = 0L
        // 1547124518 too low
        for (z in zs.reversed()) {
            sum = sum * 2 + getWireValue(z)
            println("$z = ${getWireValue(z)}")
        }
        return sum
    }

    private fun swapOutput(data: String, out1: String, out2: String) : String {
        return data.replace("-> $out1", "-> xxx").replace("-> $out2", "-> $out1").replace("-> xxx", "-> $out2")
    }

    private fun part2(data: String): Int {
        var d2 = data
        d2 = swapOutput(d2, "dhg", "z06")
        d2 = swapOutput(d2, "brk", "dpd")
        d2 = swapOutput(d2, "bhd", "z23")
        d2 = swapOutput(d2, "nbf", "z38")
        parse(d2)
        // bhd,brk,dpd,frf,nbf,z06,z23,z38  WRONG
        // bhd,brk,dhg,dpd,nbf,z06,z23,z38  RIGHT - by inspection
        // 6    dhg and z06
        // 11   brk and dpd
        // 23   z23 and bhd
        // 38   z38 and nbf
//        wires.forEach { println(it) }
//        outputToOperation.forEach { println(it) }
        val xNames = wires.keys.filter { it.startsWith("x") }.sorted()
        val yNames = wires.keys.filter { it.startsWith("y") }.sorted()

        for(i in 1..xNames.lastIndex) {
            val adder = Adder(xNames[i], yNames[i])
            println("Adder for ${xNames[i]} and ${yNames[i]}")
            try {
                adder.valid()
            } catch (e: Exception) {
                println("Adder is invalid: $i")
            }
        }


//        println(outputGraphvizDot())
        return 0
    }

    private fun outputGraphvizDot(): String {
        // generate a graphviz dot file
        val sb = StringBuilder()
        sb.append("@startdot\n")
        sb.append("digraph Day24 {\n")
//        for (wire in wires.values.filter{it.name.startsWith("x") || it.name.startsWith("y")}.sortedBy { it.name }) {
//            sb.append("  \"${wire.name}\" [label=\"${wire.name}: ${wire.value}\"]\n")
//        }

        // Connect the x and y groups in order
        // get the x names in order into a list
        val xNames = wires.keys.filter { it.startsWith("x") }.sorted()
        val yNames = wires.keys.filter { it.startsWith("y") }.sorted()

        // Draw a subgraph for each xy pair
        // Also connect the x and y together
        for(i in xNames.indices) {
            sb.append("  subgraph \"cluster_${xNames[i]}_${yNames[i]}\" {\n")
            sb.append("    label=\"${xNames[i]}-${yNames[i]}\"\n")
            sb.append("    rank=same\n")
            sb.append("    \"${xNames[i]}\"\n")
            sb.append("    \"${yNames[i]}\"\n")
            // Find direct children
            val children = outputToOperation.values.filter { it.wire1 == xNames[i] || it.wire2 == xNames[i] || it.wire1 == yNames[i] || it.wire2 == yNames[i] }.toList()
//            sb.append("    \"${xNames[i]}\" -> \"${yNames[i]}\"\n")
            children.forEach {
                sb.append("    \"${it.outWire}\"\n")
            }
            sb.append("  }\n")
        }

//        for(i in 1..xNames.lastIndex) {
//            sb.append("  \"${xNames[i-1]}\" -> \"${xNames[i]}\"\n")
//        }
//        for(i in 1..yNames.lastIndex) {
//            sb.append("  \"${yNames[i-1]}\" -> \"${yNames[i]}\"\n")
//        }


        // Now output the operations
        for (op in outputToOperation.values) {
            // First the node
            val opNodeName = "${op.outWire}"
            sb.append("  \"${opNodeName}\" [label=\"${opNodeName}-${op.op}\"]\n")
        }

        for (op in outputToOperation.values) {
            // Now the connections
            val opNodeName = "${op.outWire}"

            sb.append("  \"${op.wire1}\" -> \"${opNodeName}\" [label=\"${op.wire1}\"]\n")
            sb.append("  \"${op.wire2}\" -> \"${opNodeName}\" [label=\"${op.wire2}\"]\n")
//            sb.append("  \"${opNodeName}\" -> \"${op.outWire}\" [label=\"${op.outWire}\"]\n")

        }
        sb.append("}\n")
        sb.append("@enddot\n")
        return sb.toString()
    }

    private fun parse(data: String) {
        wires.clear()
        outputToOperation.clear()

        val s = data.split("\n\n")
        wires.putAll(s[0].lines().map { it.trim().split(": ") }.map { Wire(it[0], it[1].toIntOrNull()) }
            .associateBy { it.name })
        outputToOperation.putAll(s[1].lines().map { it.trim().split(" ") }.map { Operation(it[0], it[1], it[2], it[4]) }
            .associateBy { it.outWire })
    }

    private val wires = mutableMapOf<String, Wire>()

    // Output wire to operation
    private val outputToOperation = mutableMapOf<String, Operation>()

    protected fun getWireValue(wire: String): Int {
        val w = wires.getOrPut(wire) { Wire(wire, null) }
        if (w.value != null) {
            return w.value!!
        } else {
            val op = outputToOperation[wire]!!
            val value = when (op.op) {
                "AND" -> getWireValue(op.wire1) and getWireValue(op.wire2)
                "OR" -> getWireValue(op.wire1) or getWireValue(op.wire2)
                else -> getWireValue(op.wire1) xor getWireValue(op.wire2)
            }
            w.value = value
            return value
        }
    }

    inner class Wire(val name: String, var value: Int?) {
        fun getValue(): Int {
            return if (value != null) value!!
            else getWireValue(name)
        }

        override fun toString(): String {
            return "Wire(name='$name', value=$value)"
        }

        fun children() : List<Operation> {
            return outputToOperation.values.filter { it.wire1 == name || it.wire2 == name }.toList()
        }
    }

    inner class Operation(val wire1: String, val op: String, val wire2: String, val outWire: String) {
        fun outputValue(): Int {
            return getWireValue(outWire)
        }

        override fun toString(): String {
            return "Operation(wire1='$wire1', op='$op', wire2='$wire2', outWire='$outWire')"
        }

        fun children() : List<Operation> {
            return outputToOperation.values.filter { it.wire1 == outWire || it.wire2 == outWire }.toList()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Operation

            if (wire1 != other.wire1) return false
            if (op != other.op) return false
            if (wire2 != other.wire2) return false
            if (outWire != other.outWire) return false

            return true
        }

        override fun hashCode(): Int {
            var result = wire1.hashCode()
            result = 31 * result + op.hashCode()
            result = 31 * result + wire2.hashCode()
            result = 31 * result + outWire.hashCode()
            return result
        }


    }

    inner class Adder(val xin: String, val yin: String) {
        fun valid(): Boolean {
            // get all the nodes
            // Get the direct children

            val xChildren = wires[xin]!!.children()
            val yChildren = wires[yin]!!.children()
            val children = (xChildren + yChildren).toSet()
            // One child should be an xor, the other should be an and
            val xor1 = children.find { it.op == "XOR" }!!
            val and1 = children.find{ it.op == "AND" }!!
            var valid = true
            // xor should have 2 children
            if(xor1.children().size != 2) {
                println("xor should have 2 children: ${xor1.outWire}")
                valid = false
            }
            if(and1.children().size != 1) {
                println("and should have 1 child: ${and1.outWire}")
                valid = false
            }

            // Now, we expect the XOR to go to a z xor and a second AND
            val xorZ = xor1.children().find { it.op == "XOR" && it.outWire.startsWith("z") }
            if(xorZ == null) {
                println("xor should go to a z xor: ${xor1.outWire}")
                valid = false

            }
            val and2 = xor1.children().find{it.op == "AND"}
            if(and2 == null) {
                println("xor1 should go to an and: ${xor1.outWire}")
                valid = false
            }

            // Finally and1 should go to one OR, which is also connected to and2
            val or2 = and1.children().find { it.op == "OR" }
            if(or2 == null) {
                println("and1 should go to an or: ${and1.outWire}")
                valid = false
            }
            if(and2 != null) {
                if(and2.children() != listOf(or2)) {
                    println("and2 child should be connected to or2: ${and2.outWire}")
                    valid = false
                }
            }

            return valid
        }
    }
}