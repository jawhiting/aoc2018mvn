package com.drinkscabinet.aoc2019

import com.drinkscabinet.Utils

enum class State {
    RUNNING,
    WAITING_INPUT,
    HALTED
}

class IntCode(val orig: LongArray, val name: String = "IntCode") {

    private var ip = 0
    private var relativeBase = 0L
    private var program = orig.copyOf()
    private var memory = mutableMapOf<Long, Long>()
    private var inputs = mutableListOf<Long>()
    private var outputs = mutableListOf<Long>()
    var state = State.RUNNING

    fun clone(): IntCode {
        val copy = IntCode(orig, name)
        copy.ip = ip
        copy.relativeBase = relativeBase
        copy.program = program.copyOf()
        if (memory.isNotEmpty()) copy.memory = memory.toMutableMap()
        if (inputs.isNotEmpty()) copy.inputs = inputs.toMutableList()
        if (outputs.isNotEmpty()) copy.outputs = outputs.toMutableList()
        copy.state = state
        return copy
    }

    fun executeFromStart(inputs: List<Long>): List<Long> {
        reset()
        return continueExecution(inputs)
    }

    fun continueExecution(inputs: List<Long>): List<Long> {
        if (state == State.HALTED) return executeFromStart(inputs)
        this.inputs.addAll(inputs)
        execute(this::getInput, this::output)
        // reset output
        val result = outputs.toMutableList()
        outputs.clear()
        return result
    }

    fun reset() {
        program = orig.copyOf()
        ip = 0
        relativeBase = 0
        inputs = mutableListOf()
        outputs = mutableListOf()
        memory = mutableMapOf()
        state = State.RUNNING
    }

    fun execute(inputProvider: () -> Long?, outputProvider: (Long) -> Unit): State {
        if (state == State.HALTED) {
            reset()
        }
        var cont = true
        while (cont) {
            cont = executeNext(inputProvider, outputProvider)
        }
        return state
    }

    fun executeNext(inputProvider: () -> Long?, outputProvider: (Long) -> Unit): Boolean {
        val inst = program[ip].toInt() % 100
        val modes = program[ip].toInt() / 100
        val mode1 = modes % 10
        val mode2 = (modes / 10) % 10
        val mode3 = (modes / 100) % 10
        val param1 = ip + 1
        val param2 = ip + 2
        val param3 = ip + 3
        var moveIp = true
        when (inst) {
            1 -> write(param3, mode3, read(param1, mode1) + read(param2, mode2))    // add
            2 -> write(param3, mode3, read(param1, mode1) * read(param2, mode2))    // add
            3 -> {
                val i = inputProvider.invoke()
                if (i == null) {
                    state = State.WAITING_INPUT
                    return false
                } else {
                    write(param1, mode1, i)
                }
            }

            4 -> outputProvider.invoke(read(param1, mode1))
            5 -> if (read(param1, mode1) != 0L) {
                ip = read(param2, mode2).toInt()
                moveIp = false
            }

            6 -> if (read(param1, mode1) == 0L) {
                ip = read(param2, mode2).toInt()
                moveIp = false
            }

            7 -> write(param3, mode3, if (read(param1, mode1) < read(param2, mode2)) 1 else 0)
            8 -> write(param3, mode3, if (read(param1, mode1) == read(param2, mode2)) 1 else 0)
            9 -> relativeBase += read(param1, mode1)
            else -> {
                state = State.HALTED
                return false
            }
        }
        if (moveIp) ip += lengths[inst] ?: 4
        return true
    }

    companion object {
        fun parse(s: String, name: String = "IntCode"): IntCode {
            return IntCode(Utils.extractLongs(s), name)
        }
    }

    private fun getInput(): Long? {
        if (inputs.isEmpty()) return null
        return inputs.removeAt(0)
    }

    private fun output(o: Long) {
        outputs.add(o)
    }

    private fun read(pos: Int, mode: Int): Long {
        val posToRead = dereferencePos(pos, mode)
        if (posToRead in program.indices) {
            return program[posToRead.toInt()]
        } else {
            return memory.getOrDefault(posToRead, 0)
        }
    }

    private fun write(pos: Int, mode: Int, value: Long) {
        val posToWrite = dereferencePos(pos, mode)
        if (posToWrite in program.indices) {
            program[posToWrite.toInt()] = value
        } else {
            memory[posToWrite] = value
        }
    }

    private fun dereferencePos(pos: Int, mode: Int): Long {
        return when (mode) {
            0 -> program[pos]
            1 -> pos.toLong()
            2 -> relativeBase + program[pos]
            else -> throw RuntimeException("Invalid parameter mode $mode for pos access $pos")
        }
    }

    private val lengths = mapOf(
        1 to 4,
        2 to 4,
        3 to 2,
        4 to 2,
        5 to 3,
        6 to 3,
        7 to 4,
        8 to 4,
        9 to 2,
        99 to 1
    )
}

