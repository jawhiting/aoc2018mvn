package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day20KtTest {

    private val testData = """broadcaster -> a, b, c
%a -> b
%b -> c
%c -> inv
&inv -> a"""

    private val testData2 = """broadcaster -> a
%a -> inv, con
&inv -> b
%b -> con
&con -> output"""

    private val realData = Utils.Companion.input(this)

    enum class Pulse {
        HIGH, LOW
    }

    abstract class Module(val id: String, val outputs: List<String>) {
        abstract fun apply(signal: Signal): List<Signal>
    }

    data class Signal(val from: String, val to: String, val value: Pulse) {
        override fun toString() : String {
            return "$from -${value.toString().lowercase()}-> $to"
        }
    }
    class Broadcaster(outputs: List<String>) : Module("broadcaster", outputs) {
        override fun apply(signal: Signal): List<Signal> {
            return outputs.map { Signal(this.id, it, signal.value) }
        }
    }

    class FlipFlop(id: String, outputs: List<String>) : Module(id, outputs) {

        private var on = false
        override fun apply(signal: Signal): List<Signal> {
            if (signal.value == Pulse.LOW) {
                val outputPulse = if (on) Pulse.LOW else Pulse.HIGH
                // Flip state
                on = !on
                // Send pulse to all outputs
                return outputs.map { Signal(this.id, it, outputPulse) }
            }
            // Ignore high pulse
            return emptyList()
        }
    }

    class Conjunction(id: String, outputs: List<String>) : Module(id, outputs) {

        private val inputs = mutableMapOf<String, Pulse>()

        fun initialize(inputs: Iterable<String>) {
            for (i in inputs) {
                this.inputs[i] = Pulse.LOW
            }
        }

        override fun apply(signal: Signal): List<Signal> {
            inputs[signal.from] = signal.value
            val outputPulse = if (signal.value == Pulse.HIGH && inputs.all { it.value == Pulse.HIGH }) {
                Pulse.LOW
            } else {
                Pulse.HIGH
            }
            return outputs.map { Signal(this.id, it, outputPulse) }
        }
    }

    class Button(val id: String = "button") {
        fun press(): Signal {
            return Signal(this.id, "broadcaster", Pulse.LOW)
        }
    }

    class System(val modules: Map<String, Module>, val button: Button) {

        fun press(part2: Boolean = false) : Pair<Long, Long> {
            var lowCount = 0L
            var highCount = 0L
            val signals = mutableListOf(button.press())
            while(signals.isNotEmpty()) {
                val toProcess = signals.removeFirst()
                if(toProcess.value == Pulse.LOW) {
                    lowCount++
                }
                else {
                    highCount++
                }
//                println("$toProcess")
                if(part2 && toProcess.to == "lx" /*&& toProcess.value == Pulse.HIGH*/) {
                    println("Got pulse to lx from ${toProcess.from}")
                }
                if(part2 && toProcess.to == "rx" && toProcess.value == Pulse.LOW) {
                    return -1L to -1L
                }
                // Get the module if it exists
                val module = modules[toProcess.to]
                if (module != null) {
                    val newSignals = module.apply(toProcess)
                    signals.addAll(newSignals)
                }
            }
            return lowCount to highCount
        }

        fun countSubgraph(input: String, output: String, value: Pulse) : Long {
            // Send presses to just this node until we get the expected output
            var pressCount = 0L
            while(true) {
                // send a low pulse to the specified input
                val signals = mutableListOf(Signal("broadcaster", input, Pulse.LOW))
                ++pressCount
                while(signals.isNotEmpty()) {
                    val toProcess = signals.removeFirst()
                    if(toProcess.to == output && toProcess.value == value) {
                        return pressCount
                    }
                    val module = modules[toProcess.to]
                    if (module != null) {
                        val newSignals = module.apply(toProcess)
                        signals.addAll(newSignals)
                    }
                }
                if(pressCount % 1000000L == 0L) {
                    println("$pressCount")
                }
            }
        }

        companion object {
            fun parse(input: String): System {
                val modules = mutableMapOf<String, Module>()
                val moduleInputs = mutableMapOf<String, Set<String>>()

                for (line in input.lines()) {
                    val parts = line.split("->")
                    val moduleDefinition = parts[0].trim()
                    val outputs = parts[1].trim().split(",").map { it.trim() }
                    val module = when {
                        moduleDefinition.startsWith("%") -> {
                            val id = moduleDefinition.substring(1)
                            val module = FlipFlop(id, outputs)
                            modules[id] = module
                            module
                        }

                        moduleDefinition.startsWith("&") -> {
                            val id = moduleDefinition.substring(1)
                            val module = Conjunction(id, outputs)
                            modules[id] = module
                            module
                        }

                        else -> {
                            val module = Broadcaster(outputs)
                            modules["broadcaster"] = module
                            module
                        }
                    }
                    // Now map the outputs
                    for (o in module.outputs) {
                        moduleInputs[o] = moduleInputs.getOrPut(o) { mutableSetOf() }.plus(module.id)
                    }
                }

                // Now find all the conjunctions and init the inputs
                for(m in modules.values) {
                    if(m is Conjunction) {
                        m.initialize(moduleInputs[m.id]!!)
                    }
                }
                return System(modules, button = Button())
            }
        }
    }

    fun part1(data: String, times: Int) : Long {
        val system = System.parse(data)
        var lowCount = 0L
        var highCount = 0L
        for(i in 1..times) {
            val (l, h) = system.press()
//            println("Press $i low=$l high=$h")
            lowCount += l
            highCount += h
        }
        return lowCount * highCount
    }

    fun part2(data: String) : Long {

        val system = System.parse(data)
        val subgraphEntryPoints = system.modules["broadcaster"]!!.outputs
        val subgraphExitPoint = system.modules.values.first { "rx" in it.outputs }.id
        println("Exit point is $subgraphExitPoint")
        println("Entry points are $subgraphEntryPoints")
        val cycles = mutableListOf<Long>()
        for(e in subgraphEntryPoints) {
            val s = System.parse(data)
            val r = s.countSubgraph(e, subgraphExitPoint, Pulse.HIGH)
            println("Result for $e is $r")
            cycles.add(r)
        }
        val lcm = Utils.lcmList(cycles)
        println("LCM is $lcm")
        return lcm
    }

    @Test
    fun testPart1() {
        assertEquals(32000000L, part1(testData, 1000))
        assertEquals(11687500L, part1(testData2, 1000))
        assertEquals(794930686L, part1(realData, 1000))
    }

    @Test
    fun testPart2() {
        assertEquals(244465191362269L, part2(realData))
    }
}