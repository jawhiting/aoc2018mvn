package com.drinkscabinet.aoc2024

import com.drinkscabinet.Utils
import com.google.common.math.LongMath.checkedPow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day17KtTest {

    private val testData = """Register A: 729
Register B: 0
Register C: 0

Program: 0,1,5,4,3,0"""

    private val testData2 = """Register A: 2024
Register B: 0
Register C: 0

Program: 0,3,5,4,3,0"""

    // Get the year and day from the class
    private val realData = Utils.input(this)


    @Test
    fun testPart1() {
        assertEquals("4,6,3,5,6,3,5,2,1,0", this.part1(testData))
        println("Finished test part 1")
        assertEquals("1,5,0,1,7,4,1,0,3", this.part1(realData))
    }

    @Test
    fun testPart2() {
//        assertEquals(117440L, this.part2(testData2))
//        assertEquals("1289", this.part2(realData))
        assertEquals(47910079998866, this.part2c(realData))
    }

    private fun part1(data: String): String {
        val c = parse(data)
        var cont = true
        while(cont) {
            cont = c.executeNext()
        }
        return c.output.joinToString(",")
    }

    private fun part2c(data: String) : Long {
        // 47906343365312 too low
        val c = parse(data)
        val targets = c.instructions
        val result = find(0L, targets.reversed(), c.instructions)!!
        println("Result: $result")
        println(exec2(result, c.instructions, false).joinToString(","))
        println(exec2(result*8, c.instructions, false).joinToString(","))
        return result
    }

    /**
     * target is reversed list
     */
    private fun find(base: Long, target: List<Int>, instructions: IntArray) : Long? {
        if(target.isEmpty()) {
            // Check if it matches
            val r = exec2(base/8, instructions, false)
            if(r == instructions.toList()) {
                return base/8
            }
            else {
                println("Rejected ${base/8} with ${r.toString()} vs ${instructions.toList()}")
                return null
            }
        }
        for(i in 0..8) {
            val c = exec2(base + i, instructions, false)
            if(c.first() == target.first()) {
                val child = find((base+i)*8, target.subList(1, target.size), instructions)
                if(child != null) return child
            }
        }
        return null
    }

    private fun exec2(register: Long, instructions: IntArray, log: Boolean) : List<Int> {
        val c = Computer(longArrayOf(register, 0, 0), instructions, log)
        var cont = true
        while(cont) {
            cont = c.executeNext()
        }
        return c.output
    }

    private fun parse(data: String) : Computer {
        val ints = Utils.extractLongs(data)
        // first 3 are the registers
        val reg = ints.toList().subList(0, 3).toLongArray()
        val instructions = ints.toList().subList(3, ints.size).map { it.toInt() }.toIntArray()
        return Computer(reg, instructions)
    }

    private class Computer(val reg: LongArray, val instructions: IntArray, val log: Boolean = true) {
        var ip = 0
        val output = mutableListOf<Int>()

        /**
         * Run next, returns true if it can continue
         */
        fun executeNext() : Boolean {
            val opCode = instructions[ip]
            val literal = instructions[ip+1].toLong()
            if(opCode < 0 || opCode > 7) throw RuntimeException("Invalid opcode $opCode at $ip")
            when(opCode) {
                0 -> dv(0, getCombo(instructions[ip+1]))
                1 -> bxl(literal)
                2 -> bst(getCombo(instructions[ip+1]))
                3 -> jnz(literal)
                4 -> bxc(literal)
                5 -> out(getCombo(instructions[ip+1]))
                6 -> dv(1, getCombo(instructions[ip+1]))
                7 -> dv(2, getCombo(instructions[ip+1]))
            }
            // Continue if the ip is in range
//            println("Ended with ip $ip")
            return ip in instructions.indices
        }

        fun describe() {
                for(i in 0..instructions.lastIndex step 2) {
                val opCode = instructions[i]
                val literal = instructions[i+1]
                val d = when(opCode) {
                    0 -> "A = A / 2^" + getComboDesc(instructions[i+1])
                    1 -> "B xor $literal"
                    2 -> "B = " + getComboDesc(instructions[i+1])
                    3 -> "If A != 0 jump to $literal"
                    4 -> "B = B xor C"
                    5 -> "Print " + getComboDesc(instructions[i+1])
                    6 -> "B = A / 2^" + getComboDesc(instructions[i+1])
                    7 -> "C = A / 2^" + getComboDesc(instructions[i+1])
                    else -> ""
                }
                println("$i: $d")
            }
        }

        fun lr() : String {
            return reg.joinToString(",")
        }

        fun out(operand: Long) {
            if(log) print("$ip print $operand ${lr()} -> ")
            output.addLast((operand % 8).toInt())
            if(log) println(output.joinToString(","))
            ip += 2
        }

        fun bxc(operand: Long) {
            if(log) print("$ip B = B xor C ${lr()} -> ")
            reg[1] = reg[1] xor reg[2]
            if(log) println("${lr()}")
            ip += 2
        }

        fun bxl(operand: Long) {
            if(log) print("$ip B = B xor $operand ${lr()} -> ")
            reg[1] = reg[1] xor operand
            if(log) println("${lr()}")
            ip += 2
        }

        fun bst(operand: Long) {
            if(log) print("$ip B = $operand % 8 ${lr()} -> ")
            reg[1] = operand % 8
            if(log) println("${lr()}")
            ip += 2
        }

        fun jnz(operand: Long) {
            if(log) println("$ip if A != 0 goto $operand ${lr()}")
            if(reg[0] != 0L) {
                ip = operand.toInt()
            }
            else {
                ip += 2
            }
        }

        fun dv(writeReg: Int, operand: Long) {
            // always read from a, write to reg
            val numerator = reg[0]
            val denominator = checkedPow(2,operand.toInt())
            val div = (numerator/denominator).toLong()
            if(log) println("$ip $writeReg = $numerator / 2^$operand ($denominator) -> $div")
            reg[writeReg] = div
            ip += 2
        }

        fun getCombo(op: Int) : Long {
            if(op < 0 || op > 6) throw RuntimeException("Invalid operand $op")
            if(op < 4) return op.toLong()
            return reg[op.toInt()-4]
        }

        fun getComboDesc(op: Int) : String {
            if(op < 0 || op > 6) throw RuntimeException("Invalid operand $op")
            if(op < 4) return "Lit $op"
            val rs = arrayOf("A","B","C")
            return "Reg " + (rs[op.toInt()-4])
        }
    }
}