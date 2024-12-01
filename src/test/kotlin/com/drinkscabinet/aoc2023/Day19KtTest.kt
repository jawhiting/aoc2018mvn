package com.drinkscabinet.aoc2023

import com.drinkscabinet.Utils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day19KtTest {

    private val testData = """px{a<2006:qkq,m>2090:A,rfg}
pv{a>1716:R,A}
lnx{m>1548:A,A}
rfg{s<537:gd,x>2440:R,A}
qs{s>3448:A,lnx}
qkq{x<1416:A,crn}
crn{x>2662:A,R}
in{s<1351:px,qqz}
qqz{s>2770:qs,m<1801:hdj,R}
gd{a>3333:R,R}
hdj{m>838:A,pv}

{x=787,m=2655,a=1222,s=2876}
{x=1679,m=44,a=2067,s=496}
{x=2036,m=264,a=79,s=2244}
{x=2461,m=1339,a=466,s=291}
{x=2127,m=1623,a=2188,s=1013}"""

    private val realData = Utils.input(this)

    data class PartRange(
        val x: IntRange = 1..4000,
        val m: IntRange = 1..4000,
        val a: IntRange = 1..4000,
        val s: IntRange = 1..4000
    ) {
        fun combinations(): Long {
            return (x.last - x.first + 1).toLong() * (m.last - m.first + 1) * (a.last - a.first + 1) * (s.last - s.first + 1)
        }
    }

    data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {

        fun score(): Int {
            return x + m + a + s
        }

        companion object {
            fun parse(s: String): Part {
                val parts = Utils.extractInts(s)
                return Part(parts[0], parts[1], parts[2], parts[3])
            }
        }
    }

    enum class Variable {
        x, m, a, s
    }

    enum class Operator {
        GT, LT
    }

    data class Rule(val v: Variable, val operator: Operator, val param: Int, val next: String) {

        fun matches(part: Part): Boolean {
            val toTest = when (v) {
                Variable.x -> part.x
                Variable.m -> part.m
                Variable.a -> part.a
                Variable.s -> part.s
            }
            return when (operator) {
                Operator.GT -> toTest > param
                Operator.LT -> toTest < param
            }
        }

        fun matches(part: PartRange): Boolean {
            val toTest = when (v) {
                Variable.x -> part.x
                Variable.m -> part.m
                Variable.a -> part.a
                Variable.s -> part.s
            }
            return when (operator) {
                Operator.GT -> toTest.last > param
                Operator.LT -> toTest.first < param
            }
        }

        fun canFail(part: PartRange): Boolean {
            val toTest = when (v) {
                Variable.x -> part.x
                Variable.m -> part.m
                Variable.a -> part.a
                Variable.s -> part.s
            }
            return if(param in toTest) {
                true
            } else {
                when (operator) {
                    Operator.GT -> toTest.last <= param
                    Operator.LT -> toTest.first >= param
                }
            }
        }

        fun reduceToMatch(r: PartRange): PartRange {
            return when (v) {
                Variable.x -> r.copy(x = reduceRangeToMatch(r.x))
                Variable.m -> r.copy(m = reduceRangeToMatch(r.m))
                Variable.a -> r.copy(a = reduceRangeToMatch(r.a))
                Variable.s -> r.copy(s = reduceRangeToMatch(r.s))
            }
        }

        fun reduceToFail(r: PartRange): PartRange {
            return when (v) {
                Variable.x -> r.copy(x = reduceRangeToFail(r.x))
                Variable.m -> r.copy(m = reduceRangeToFail(r.m))
                Variable.a -> r.copy(a = reduceRangeToFail(r.a))
                Variable.s -> r.copy(s = reduceRangeToFail(r.s))
            }
        }

        fun reduceRangeToMatch(r: IntRange): IntRange {
            if (param in r) {
                return when (operator) {
                    Operator.GT -> param + 1..r.last
                    Operator.LT -> r.first..<param
                }
            }
            // whole range must match with no change
            return when (operator) {
                Operator.GT -> if (r.first > param) r else throw RuntimeException("Range doesn't reduce $this $r")
                Operator.LT -> if (r.last < param) r else throw RuntimeException("Range doesn't reduce $this $r")
            }
        }

        fun reduceRangeToFail(r: IntRange): IntRange {
            if (param in r) {
                return when (operator) {
                    Operator.GT -> r.first..param
                    Operator.LT -> param..r.last
                }
            }
            // whole range must fail with no change
            return when (operator) {
                Operator.GT -> if (r.last <= param) r else throw RuntimeException("Range fail doesn't reduce $this $r")
                Operator.LT -> if (r.first >= param) r else throw RuntimeException("Range fail doesn't reduce $this $r")
            }
        }

        companion object {
            fun parse(s: String): Rule {
                val parts = s.split(":")
                val next = parts[1].trim()
                val v = Variable.valueOf(parts[0].first().toString())
                val op = if (parts[0][1] == '>') Operator.GT else Operator.LT
                val param = Utils.extractInts(parts[0]).first()
                return Rule(v, op, param, next)
            }
        }
    }

    @Test
    fun testReduceRange() {
        val r = PartRange(1..10, 1..10, 1..10, 1..10)
        val rule = Rule(Variable.x, Operator.GT, 5, "A")
        println(rule.reduceToMatch(r))
        assertTrue(rule.matches(rule.reduceToMatch(r)))
        println(rule.reduceToFail(r))
        assertFalse(rule.matches(rule.reduceToFail(r)))
    }

    data class Workflow(val id: String, val rules: List<Rule>, val defResult: String) {

        fun apply(part: Part): String {
            val r = rules.find { it.matches(part) }
            return r?.next ?: defResult
        }

        fun outputs(pr: PartRange): Map<String, Set<PartRange>> {
            // When this workflow is called with this range, what are the next workflows and ranges
            val result = mutableMapOf<String, MutableSet<PartRange>>()
            var currentRange = pr
            for(rule in rules) {
                // Can we pass this rule?
                if (rule.matches(currentRange)) {
                    result.computeIfAbsent(rule.next) { mutableSetOf() }.add(rule.reduceToMatch(currentRange))
//                    result[rule.next] = rule.reduceToMatch(currentRange)
                }
                // Can we fail this rule?
                if (rule.canFail(currentRange)) {
                    currentRange = rule.reduceToFail(currentRange)
                }
                else {
                    // if we can never fail, then we are done for this workflow
                    return result
                }
            }
            // if we get to the end, record the remaining range against default
//            result[defResult] = currentRange
            result.computeIfAbsent(defResult) { mutableSetOf() }.add(currentRange)
            return result
        }

        companion object {
            fun parse(s: String): Workflow {
                val id = s.substringBefore("{")
                val rules = s.substringAfter("{").substringBefore("}").split(",").toMutableList()
                // last one is special
                val defResult = rules.removeLast()
                return Workflow(id, rules.map { Rule.parse(it) }, defResult)
            }
        }
    }

    data class System(val workflows: Map<String, Workflow>) {

        private val inFlow = workflows["in"]!!
        fun apply(part: Part): String {
            var nextWorkflow = "in"
            do {
                val w = workflows[nextWorkflow]!!
                nextWorkflow = w.apply(part)
            } while (nextWorkflow !in arrayOf("A", "R"))
            return nextWorkflow
        }

        fun combinations() : Long {
            val toCheck = mutableSetOf("in" to PartRange())
            val acceptedRanges = mutableSetOf<PartRange>()
            val rejectedRanges = mutableSetOf<PartRange>()
            do {
                val (workflow, range) = toCheck.first()
//                println("Checking $workflow $range ${range.combinations()}")
                toCheck.remove(workflow to range)
                val outputs = workflows[workflow]!!.outputs(range)
                val outputSum = outputs.values.flatten().sumOf { it.combinations() }
//                for((w, r) in outputs) {
//                    for(r2 in r) {
//                        println("  Output of $workflow is $w $r2 ${r2.combinations()}")
//                    }
//                }
//                println(" Output $outputSum vs input ${range.combinations()}")
                for((w, r) in outputs) {
                    if(w == "A") {
                        acceptedRanges.addAll(r)
//                        println("Accepted $r ${r.sumOf { it.combinations()}}")
                    }
                    else if(w == "R") {
                        rejectedRanges.addAll(r)
//                        println("Rejected $r ${r.sumOf { it.combinations()}}")
                    }
                    else if (w in workflows) {
                        r.forEach {
                            toCheck.add(w to it)
                        }
                    }
                }
            } while(toCheck.isNotEmpty())
//            for (acceptedRange in acceptedRanges) {
//                println("$acceptedRange score ${acceptedRange.combinations()}")
//            }
            println(PartRange().combinations())
            return acceptedRanges.sumOf { it.combinations() }
        }

        companion object {
            fun parse(s: String): System {
                val workflows = s.lines().map { Workflow.parse(it) }.associateBy { it.id }
                return System(workflows)
            }
        }
    }

    fun parse(data: String): Pair<System, List<Part>> {
        val p = data.split("\n\n")
        val system = System.parse(p[0])
        val parts = p[1].lines().map { Part.parse(it) }
        return system to parts
    }

    @Test
    fun testWorkflowOutputs() {
        val r = PartRange()
        val w = Workflow.parse("x{a<2006:qkq,m>2090:A,rfg}")
        println(w.outputs(r))
    }
    @Test
    fun testParseWorkflow() {
        val w = Workflow.parse("x{a<2006:qkq,m>2090:A,rfg}")
        println(w)
        println(w.apply(Part(787, 2655, 1222, 2876)))
        println(w.apply(Part(787, 2655, 3000, 2876)))
    }

    @Test
    fun testPart1() {
        assertEquals(19114, part1(testData))
        assertEquals(19114, part1(realData))
    }

    fun part1(input: String): Int {
        val (system, parts) = parse(input)
        return parts.filter { system.apply(it) == "A" }.sumOf { it.score() }
    }

    @Test
    fun testPart2() {
        assertEquals(167409079868000, part2(testData))
        assertEquals(123972546935551, part2(realData))
    }
    fun part2(input: String): Long {
        val (system, parts) = parse(input)
        return system.combinations()
    }}