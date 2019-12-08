package com.drinkscabinet.aoc2019

class IntCode(val orig: IntArray) {

    private var ip = 0
    private var program = orig.copyOf()
    private val instructions : Array<Operator> = parse(orig)

    private fun parse(orig: IntArray): Array<Operator> {
        val insts = mutableListOf<Operator>()
        var i = 0
        while( i < orig.size ) {
            val op =
            when(i) {
                1 -> Add(i)
                2 -> Mul(i)
                99 -> Halt(i)
                else -> Halt(i)
            }
            insts.add(op)
            i += op.size
        }
        return insts.toTypedArray()
    }

    fun part1() {
        while(program[ip] != 99 ) {
            if( !apply(ip)) {
                println(program.contentToString())
                return
            }
        }
    }

    fun part2(n: Int, v: Int): Int {
        program = orig.copyOf()
        ip = 0
        program[1] = n
        program[2] = v
        part1()
        return program[0]
    }

    fun apply(pos: Int) : Boolean {
        val inst = program[ip]
        if( inst == 1) {
            program[program[ip+3]] = program[program[ip+1]] + program[program[ip+2]]
        }
        else if( inst == 2) {
            program[program[ip+3]] = program[program[ip+1]] * program[program[ip+2]]
        }
        else {
            return false
        }
//        println(""+ ip + ":" + program.contentToString())
        ip += 4
        return true
    }

    private fun readValue(pos: Int) : Int{
        return program[program[pos]]
    }

    fun writeValue(pos: Int, v: Int) {
        program[program[pos]] = v
    }

    abstract class Operator(val opCode: Int, val name: String, val size: Int) {
        abstract fun run(): Boolean // return True to continue
    }

    inner class Add(val i: Int): Operator(1, "add", 4){
        override fun run() : Boolean{
            writeValue(i+3, readValue(i+1) + readValue(i+2))
            return true
        }
    }

    inner class Mul(val i: Int): Operator(2, "mul", 4){
        override fun run() : Boolean{
            writeValue(i+3, readValue(i+1) * readValue(i+2))
            return true
        }
    }

    inner class Halt(val i: Int): Operator(99, "halt", 1){
        override fun run() : Boolean{
            return false
        }
    }
}

