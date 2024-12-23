package com.drinkscabinet.aoc2020

import com.drinkscabinet.Utils

private fun main() {
    val console = Console()
    console.load(input.lines())
    println("Part1=${console.run()}")

    val start = System.currentTimeMillis()
    // Part2 - just keep changing one line
    val toRun = input.lines().toMutableList()
    for (i in input.lines().indices) {
        val instruction = toRun[i].substring(0..2)
        if (instruction == "acc") continue
        val oldInstruction = toRun[i]
        if (instruction == "jmp") {
            val newInstruction = toRun[i].replace("jmp", "nop")
            toRun[i] = newInstruction
        } else if (instruction == "nop") {
            val newInstruction = toRun[i].replace("nop", "jmp")
            toRun[i] = newInstruction
        }
        // See if it runs
        console.load(toRun)
        if (console.run()) {
            println("Terminated correctly after changing $i acc=${console.acc}")
            break
        } else {
            println("Detected repeat after changing $i")
            toRun[i] = oldInstruction
        }
    }
    println("Part2 took ${System.currentTimeMillis() - start}")
}

class Console {
    private var code = arrayOf<String>()
    private var ip = 0
    var acc = 0L

    fun load(p: Collection<String>) {
        code = p.toTypedArray().clone()
        ip = 0
        acc = 0
    }

    /**
     * Run until we encounter an instruction twice or terminate
     * True -> Terminated, False -> dup instruction
     */
    fun run(): Boolean {
        val visited = mutableSetOf<Int>()
        var count = 0
        while (!visited.contains(ip) && ip < code.size) {
            if (count % 100 == 0) println("Instruction count: $count")
            visited.add(ip)
            executeInstruction()
            ++count
        }
        if (visited.contains(ip)) {
            println("Revisited instruction $ip acc=$acc")
            return false
        } else {
            println("Program terminated normally acc=$acc")
            return true
        }
    }

    // Returns next instruction pointer to execute
    private fun executeInstruction(): Int {
        val line = code[ip]
        val inst = line.substring(0..2)
        val value = Utils.extractInts(line)[0]
//        println("Executing $ip")
        when (inst) {
            "nop" -> {
                // do nothing
                ++ip
            }

            "jmp" -> {
                ip += value
            }

            "acc" -> {
                acc += value
                ++ip
            }

            else -> {
                throw RuntimeException("No such instruction $inst $value at $ip")
            }
        }
        return ip
    }
}

private val testInput = """
    nop +0
    acc +1
    jmp +4
    acc +3
    jmp -3
    acc -99
    acc +1
    jmp -4
    acc +6
""".trimIndent()

private val input = """
    nop +116
    acc +12
    acc -8
    acc +34
    jmp +485
    acc +42
    jmp +388
    acc +36
    nop +605
    acc +17
    jmp +411
    acc +49
    jmp +1
    acc -9
    jmp +289
    jmp +288
    jmp +74
    acc +4
    acc +42
    jmp +258
    acc +14
    acc -13
    nop +106
    jmp +280
    jmp +534
    acc +41
    acc +40
    jmp +224
    acc +43
    acc +10
    nop +240
    jmp +211
    acc +7
    acc -3
    acc +7
    jmp +1
    jmp +559
    jmp +415
    jmp +528
    acc -16
    jmp +568
    jmp +442
    nop +113
    jmp +464
    acc +42
    jmp +336
    acc -2
    acc +39
    jmp +251
    acc -4
    acc +42
    jmp +528
    acc +5
    acc +30
    nop +429
    acc +49
    jmp +86
    acc +15
    nop +145
    acc -8
    jmp +1
    jmp +404
    acc +26
    acc +50
    jmp +251
    acc +47
    jmp +1
    acc +45
    acc -5
    jmp +357
    acc +31
    jmp +62
    acc +25
    nop +540
    acc -13
    acc +0
    jmp +72
    acc +28
    acc +36
    nop +475
    acc -17
    jmp +166
    acc +4
    acc +20
    acc +30
    acc +43
    jmp +464
    acc +4
    jmp +94
    jmp +44
    nop +446
    acc -16
    nop +267
    acc +30
    jmp +519
    acc +45
    acc +47
    jmp +62
    acc +28
    acc -13
    acc +45
    jmp +239
    acc +12
    jmp +1
    nop +153
    jmp +245
    jmp +244
    acc -12
    jmp +308
    jmp +190
    jmp -86
    acc +45
    acc +1
    acc +15
    acc +30
    jmp +350
    acc +30
    jmp +42
    jmp +214
    jmp +447
    acc +24
    jmp +453
    acc +29
    acc +42
    jmp +302
    acc -4
    acc +33
    jmp +447
    acc -18
    acc +15
    acc -2
    jmp -24
    jmp -4
    jmp +35
    acc +0
    jmp -83
    acc -13
    nop +437
    acc -15
    jmp +95
    nop +289
    jmp +348
    acc +17
    acc +23
    acc +45
    jmp +359
    acc +18
    jmp +352
    acc +0
    acc +13
    acc +25
    acc +11
    jmp +331
    acc -2
    jmp +19
    jmp -103
    acc +34
    acc +48
    jmp +141
    acc +44
    jmp +1
    acc +42
    jmp +374
    acc +45
    acc +35
    nop -37
    acc -2
    jmp +244
    jmp +151
    acc +36
    acc +4
    nop -64
    jmp +231
    nop +321
    nop +291
    acc +16
    jmp -161
    acc +17
    nop +412
    nop -89
    nop +179
    jmp -8
    nop -167
    acc +44
    acc +4
    jmp +42
    acc +22
    acc +28
    acc +22
    jmp +192
    acc -18
    acc -7
    jmp -70
    acc +27
    acc +25
    jmp +312
    acc +50
    acc -16
    jmp -121
    acc +14
    acc +43
    nop -111
    jmp -54
    nop +39
    acc -4
    acc +41
    jmp +236
    acc -11
    jmp -118
    jmp +150
    acc -15
    jmp -141
    acc +14
    jmp +1
    acc -8
    jmp -96
    acc +11
    nop -95
    jmp +1
    acc +47
    jmp -113
    nop +257
    jmp +35
    acc +45
    acc +25
    acc -6
    jmp +31
    jmp +1
    nop +153
    nop -39
    jmp +25
    acc +0
    acc +50
    jmp +362
    acc -15
    acc +0
    acc +31
    acc +22
    jmp +69
    acc -18
    acc +24
    jmp -38
    acc +39
    acc -10
    acc +40
    jmp +6
    jmp +143
    jmp -44
    acc +32
    acc -8
    jmp +358
    jmp +248
    nop +343
    nop -11
    jmp +116
    jmp +74
    jmp +120
    acc +37
    acc -19
    acc +36
    jmp +341
    acc +49
    jmp -164
    acc +14
    acc +13
    acc +0
    acc +50
    jmp +291
    jmp +1
    jmp -79
    acc +19
    jmp +243
    acc +25
    acc -13
    acc -12
    acc -7
    jmp +228
    jmp -81
    acc +18
    nop -163
    acc +0
    acc +8
    jmp +212
    acc +38
    acc -12
    jmp +6
    acc +24
    acc +42
    acc +21
    acc +12
    jmp +136
    acc -12
    acc -2
    acc +46
    acc +35
    jmp +290
    acc +6
    acc +36
    jmp -182
    acc +14
    acc +7
    jmp +228
    jmp -19
    acc +48
    acc +25
    jmp +106
    jmp +70
    acc +24
    jmp +1
    acc +24
    acc +29
    jmp -156
    nop +296
    acc +34
    jmp +115
    acc -12
    acc +41
    jmp +28
    jmp +165
    acc +0
    acc +24
    acc +42
    acc +27
    jmp +106
    acc +24
    acc -11
    acc +4
    acc -6
    jmp -180
    acc -2
    jmp +2
    jmp -314
    acc -9
    acc +1
    jmp -327
    acc -8
    acc +7
    acc -6
    acc +32
    jmp -157
    acc +10
    acc +10
    acc -16
    jmp +278
    jmp +6
    acc +0
    nop +178
    acc +26
    jmp +231
    jmp +175
    acc +29
    acc +36
    acc +7
    jmp -255
    acc +46
    acc +45
    acc +7
    nop -7
    jmp -101
    jmp +3
    acc -13
    jmp -140
    nop -115
    jmp +1
    jmp -336
    acc +9
    acc +9
    nop -68
    acc -3
    jmp -37
    acc -13
    nop +128
    jmp +1
    jmp -90
    acc +49
    jmp -124
    acc +16
    acc +9
    jmp +212
    acc -18
    jmp -303
    acc +33
    acc +23
    acc +26
    jmp +140
    acc +25
    nop -123
    acc +22
    jmp +148
    acc +1
    acc +44
    jmp -352
    acc -11
    jmp +33
    acc +16
    nop -199
    acc +15
    jmp -351
    jmp +5
    jmp -357
    nop -284
    acc +32
    jmp -43
    acc +5
    acc +23
    acc +3
    jmp +59
    acc -10
    nop -266
    nop +43
    jmp +79
    acc +21
    jmp -42
    acc +35
    acc +5
    jmp +68
    acc +24
    acc -4
    jmp -155
    acc +45
    jmp +154
    jmp -311
    acc +10
    acc +17
    acc +39
    jmp -297
    jmp -175
    acc +49
    jmp -151
    acc -4
    acc -9
    jmp -219
    acc +48
    acc -17
    acc +30
    jmp -9
    acc +10
    jmp -61
    nop -396
    acc +11
    acc +37
    jmp -331
    acc +14
    acc +22
    acc +30
    acc +2
    jmp -43
    nop -265
    acc +5
    acc +40
    acc -15
    jmp -35
    acc -3
    acc +24
    jmp -415
    acc +0
    jmp +98
    acc +17
    acc +25
    nop -48
    acc -17
    jmp -302
    acc +11
    acc +11
    jmp -181
    acc +46
    acc +19
    jmp -331
    nop +90
    acc +45
    acc +8
    jmp -237
    acc -11
    nop -421
    jmp -145
    acc -16
    acc +47
    jmp -387
    acc +50
    jmp -375
    acc +38
    jmp +1
    jmp -225
    acc +47
    acc +39
    jmp +69
    acc +46
    acc +41
    jmp -89
    acc +19
    jmp -453
    nop +63
    acc +18
    jmp -386
    nop -243
    acc +48
    jmp +70
    acc +25
    jmp -191
    acc +48
    acc +31
    jmp +40
    acc -10
    jmp -46
    acc +45
    jmp -48
    jmp -12
    acc +16
    acc -16
    jmp -120
    acc -10
    jmp +1
    acc -10
    jmp -124
    acc +48
    acc +15
    acc +8
    acc -15
    jmp -66
    nop -130
    acc +16
    acc +10
    acc +31
    jmp -375
    acc +9
    acc +20
    jmp -37
    acc +14
    jmp -134
    acc -9
    acc -6
    jmp -120
    acc +24
    acc +17
    acc +49
    jmp -332
    acc +7
    acc +35
    nop -149
    jmp -103
    jmp -277
    acc -1
    acc +28
    nop -211
    jmp -371
    nop -129
    acc -15
    acc +6
    acc +19
    jmp -120
    acc -6
    jmp -79
    acc +0
    jmp -64
    acc +33
    acc +33
    jmp -440
    jmp -85
    acc +37
    nop -183
    acc +24
    acc +42
    jmp -545
    acc +50
    acc +6
    jmp -7
    nop +8
    acc +1
    jmp -359
    acc -1
    nop -388
    acc -7
    acc +28
    jmp -211
    jmp -384
    acc +32
    acc +16
    acc +40
    jmp +17
    acc +0
    acc +43
    acc -14
    jmp -512
    nop -264
    jmp -474
    nop -543
    acc +17
    nop -288
    jmp -38
    jmp +24
    acc -4
    jmp -321
    acc +49
    acc -16
    jmp -532
    acc +0
    acc -11
    acc -16
    jmp -104
    acc -12
    jmp -301
    acc +6
    nop -498
    acc +0
    jmp -126
    nop -127
    acc +1
    jmp -6
    acc +40
    jmp -547
    acc +16
    acc +18
    jmp -123
    acc -5
    acc +27
    acc +44
    acc +15
    jmp -22
    acc +48
    acc -18
    jmp -350
    acc -7
    acc +30
    acc +26
    jmp +1
    jmp +1
""".trimIndent()