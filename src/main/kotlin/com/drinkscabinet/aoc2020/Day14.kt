package com.drinkscabinet.aoc2020

import com.drinkscabinet.Utils
import java.util.*
import kotlin.math.pow

private fun main() {
//    part1()
    println(findAllXs("XX01XX"))
    println(addresses(42, "X1001X"))
    println(addresses(26, "00000000000000000000000000000000X0XX"))
    part2()
}


private fun part1() {
    val mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
    val mask1 = get1Mask(mask)
    println(mask1)
    println(mask1.toString(2))
    val mask0 = get0Mask(mask)
    println(mask0)
    println(mask0.toString(2))

    println(apply(11, mask0, mask1))
    println(apply(101, mask0, mask1))
    println(apply(0, mask0, mask1))

    println("Part1test=${Computer().execute(testInput.lines())}")
    println("Part1=${Computer().execute(input.lines())}")
}

private fun part2() {
    println("Part2test=${Computer().execute2(testInput2.lines())}")
    println("Part2=${Computer().execute2(input.lines())}")
}

fun apply(l: Long, mask0: Long, mask1: Long): Long {
    // set the 1s
    var result = l or mask1
    return result and mask0
}

// OR this to set the 1
fun get1Mask(s: String): Long {
    return s.replace('X', '0').toLong(2)
}

// AND this to set the 0
fun get0Mask(s: String): Long {
    return s.replace('X', '1').toLong(2)
}

private fun addresses(addr: Long, mask: String): Set<Long> {
    // Get all the addresses resulting from the current mask applied to this address
    val mask1 = get1Mask(mask)
//    val mask0 = get0Mask(mask)
    val startAddr = addr or mask1
    println("addr : ${addr.toString(2)}")
    println("mask : ${mask}")
    println("mask1: ${addr.toString(2)}")
//    println("mask0: ${addr.toString(2)}")
    println("apply: ${startAddr.toString(2)}")
    // Now, for every bit position with an X, apply all the combinations
    val result = mutableSetOf<Long>()
    val xCount = mask.count { it == 'X' }
    // get all the x positions
    val xPos = findAllXs(mask).toList()
    println(xPos)
    val combinations = 2.0.pow(xCount.toDouble()).toInt()
    println(startAddr.toString(2))
    for (i in 0 until combinations) {
        // apply the bits to the startAddr
        val bits = BitSet.valueOf(longArrayOf(startAddr))

        // Now manipulate the bits
        val applyBits = BitSet.valueOf(longArrayOf(i.toLong()))
        for (b in 0 until xCount) {
            // get the bit, apply it to the corresponding main bit
            bits[xPos[b]] = applyBits[b]
        }
        val element = bits.toLongArray()[0]
        println(element.toString(2))
        result.add(element)
    }
    return result
}

private fun findAllXs(s: String): Set<Int> {
    val result = mutableSetOf<Int>()
    s.toCharArray().forEachIndexed { index, c -> if (c == 'X') result.add(s.lastIndex - index) }
    return result
}

private class Computer {
    private var ip = 0
    private val memory = mutableMapOf<Long, Long>()
    private var mask = ""
    private var mask0 = 0L
    private var mask1 = 0L

    fun execute(program: List<String>): Long {
        for (instruction in program) {
            execute(instruction)
        }
        // Add all the non-zero values
        return memory.values.sum()
    }

    fun execute(instruction: String) {
        if (instruction.startsWith("mask")) {
            println(instruction.count { it == 'X' })
            mask0 = get0Mask(instruction.substringAfter("= "))
            mask1 = get1Mask(instruction.substringAfter("= "))
        } else {
            val values = Utils.extractLongs(instruction)
            val address = values[0]
            val value = values[1]
            memory[address] = apply(value, mask0, mask1)
        }
    }

    fun execute2(program: List<String>): Long {
        for (instruction in program) {
            execute2(instruction)
        }
        // Add all the non-zero values
        return memory.values.sum()
    }

    fun execute2(instruction: String) {
        if (instruction.startsWith("mask")) {
            println(instruction.count { it == 'X' })
            mask = instruction.substringAfter("= ")
        } else {
            val values = Utils.extractLongs(instruction)
            val address = values[0]
            val value = values[1]
            // Find all the places to write
            val addresses = addresses(address, mask)
            addresses.forEach { memory[it] = value }
        }
    }


}

private val testInput = """
    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
    mem[8] = 11
    mem[7] = 101
    mem[8] = 0
""".trimIndent()

private val testInput2 = """
    mask = 000000000000000000000000000000X1001X
    mem[42] = 100
    mask = 00000000000000000000000000000000X0XX
    mem[26] = 1
""".trimIndent()

private val input = """
    mask = 0000011011111X1001100X0001X1001100X0
    mem[43805] = 6934
    mem[57564] = 3741
    mem[28260] = 67554
    mem[16151] = 813
    mem[3058] = 873646
    mem[51717] = 270314
    mask = 1001X1X011110XX111100001100111001110
    mem[16252] = 730
    mem[28701] = 63670997
    mem[28652] = 9219200
    mem[64761] = 488985928
    mask = 0X01X10X1XX1010111100100100111101001
    mem[2833] = 52082550
    mem[59636] = 6574572
    mem[26957] = 62766
    mask = 00X0XX101X111X1001011111000101000101
    mem[54455] = 839691
    mem[65381] = 2671752
    mem[8488] = 4031472
    mem[5618] = 549
    mem[41399] = 5232
    mem[62807] = 201160
    mem[63847] = 201082
    mask = X000X10011X1X100011X0001000010000110
    mem[47360] = 44151287
    mem[46338] = 79342985
    mem[6868] = 11520126
    mem[27037] = 20408
    mem[25014] = 758862851
    mask = 100001X01111110X0X110001X0101010010X
    mem[21679] = 5106103
    mem[12306] = 690264
    mask = 0000011111X1XX1X0XX10000011000000110
    mem[19803] = 179348214
    mem[19095] = 125150056
    mem[34147] = 1407927
    mem[41272] = 5775647
    mask = X00X01001X0111X101110X0001X110000XXX
    mem[53665] = 290143
    mem[20519] = 265
    mem[44017] = 834
    mem[41856] = 435
    mem[43805] = 2264115
    mask = 1X0X0111111X1X010110X10000011X1XX101
    mem[52128] = 1029842
    mem[27168] = 222084
    mask = 10011100111111X100110X0XX11111011100
    mem[62082] = 9135
    mem[14654] = 3686
    mem[25450] = 1593529
    mem[1497] = 5749950
    mem[30570] = 5450
    mem[2144] = 106125340
    mem[1099] = 9136
    mask = 1000110XX00X10110111X0X00011X0XX0000
    mem[14922] = 97221103
    mem[26301] = 11154
    mem[47381] = 94170015
    mem[31162] = 36266349
    mem[24087] = 2020
    mask = 1100111111X0XX0X00110000110001010000
    mem[45973] = 3120
    mem[38175] = 307
    mem[58216] = 332810821
    mem[14799] = 3922
    mem[29584] = 14393700
    mem[51240] = 61932467
    mask = 00010X1X10011110X110X101X00001010X11
    mem[58448] = 26283204
    mem[16738] = 466633
    mem[6096] = 369
    mem[6987] = 4902006
    mask = 110011XX101111110111X000001XX01101XX
    mem[2710] = 34047107
    mem[63146] = 116986332
    mask = 0001110011110101111X1X001X011X0101X1
    mem[6096] = 5336
    mem[9192] = 44289
    mem[26857] = 13
    mem[54135] = 1490304
    mem[17220] = 3623
    mem[1210] = 50969
    mask = 00011X0011X111011X00000100X1X100X111
    mem[31556] = 293748
    mem[21644] = 60471
    mem[31567] = 972370
    mem[32315] = 104177
    mem[6429] = 10486
    mem[25892] = 62384267
    mask = 000001001111010110X01000XX01100110X0
    mem[51519] = 214
    mem[55256] = 1197991
    mem[65279] = 178690564
    mem[3173] = 1554206
    mem[10263] = 1704
    mem[55736] = 217069
    mask = X0011100X111X1011XX00000X011110X0101
    mem[52317] = 3581460
    mem[22238] = 92255
    mem[6688] = 29033188
    mem[7311] = 1092
    mem[1099] = 379272
    mem[5143] = 9437126
    mask = X0XX01111111111001X10X00110011101010
    mem[23050] = 9416
    mem[34100] = 821285
    mem[62807] = 1156515
    mask = 1001110010X11X111X11011001001X1111X1
    mem[50851] = 1266
    mem[23545] = 177017
    mem[12266] = 483
    mask = 0011X01001010X00101X000011X001110000
    mem[11872] = 12823
    mem[1831] = 38511
    mem[25974] = 553433
    mem[49217] = 1793537
    mem[37800] = 372
    mask = 10001101X0XX1011011101011XXX00000001
    mem[34122] = 466038899
    mem[775] = 32552509
    mem[32422] = 25983159
    mem[46550] = 4296
    mask = 1000X100111111XX01X1001X101010100110
    mem[31650] = 10524288
    mem[6322] = 140581
    mem[10638] = 3290724
    mem[35709] = 137485
    mem[20467] = 175698926
    mask = 1X100011X011X111X11X1001X10010X11X10
    mem[62710] = 140559632
    mem[26295] = 506581898
    mem[23934] = 2027
    mem[1770] = 1402
    mem[59755] = 3234994
    mem[27304] = 923348
    mask = 0000010X11111X00011010100X110010X111
    mem[6824] = 46336422
    mem[56833] = 41706
    mem[45316] = 1198
    mem[39426] = 48436
    mask = 100001001101X1X00111XXXX01X0100XX000
    mem[58691] = 16515016
    mem[11879] = 1449419
    mem[22694] = 1761
    mem[4517] = 6645515
    mem[25556] = 3758
    mem[6868] = 28390000
    mem[32545] = 304290
    mask = X00001X01XX111XX011101010X0X101X0110
    mem[49156] = 266240397
    mem[4497] = 84363
    mem[29232] = 126554202
    mem[12306] = 155188
    mem[48047] = 22264
    mask = X10X110111100X0X011XX0111111XX10001X
    mem[31701] = 73338
    mem[31978] = 1151135
    mem[7483] = 479508851
    mem[36135] = 3964640
    mem[45859] = 62507
    mem[62676] = 99387068
    mask = 0100X11110101XX1011010001X000X111100
    mem[44119] = 222861
    mem[16458] = 1512948
    mem[45791] = 21526597
    mem[19808] = 2902702
    mem[6868] = 2678
    mem[21659] = 112735
    mask = X1001XX11110X1010X11X01101111XX0X101
    mem[30512] = 802102210
    mem[27203] = 3779646
    mem[16581] = 238152
    mem[16257] = 5959659
    mem[39296] = 63813
    mem[16174] = 87595
    mem[10104] = 1072
    mask = 0000011XX1X1X1X0X11X001X000110000010
    mem[28838] = 5264
    mem[42509] = 111014106
    mem[24905] = 521203049
    mem[9718] = 15454
    mem[16951] = 22743
    mask = 100X0101X1110101X0X0X0010101110100X1
    mem[7483] = 40730
    mem[9639] = 13929656
    mem[25817] = 2819
    mem[16257] = 274898612
    mem[55439] = 598143974
    mask = 110011111110X11101X0000X1010X110000X
    mem[37397] = 3714
    mem[54979] = 794
    mem[1014] = 3463104
    mem[50612] = 194522027
    mem[4969] = 337504357
    mem[53665] = 766941
    mask = 1X00X111111X1111011110001X11100011XX
    mem[24546] = 1830044
    mem[57219] = 94920
    mem[31992] = 29180
    mem[34078] = 227189
    mem[59660] = 111830573
    mem[58661] = 248870
    mem[15971] = 175220352
    mask = 00000XX0X11X01000XX00111000X0000X010
    mem[1571] = 215979
    mem[14641] = 30916273
    mem[14751] = 102869423
    mem[19244] = 8960072
    mask = 0X100X1001X10001101X0X00X0X0XX010100
    mem[21724] = 288136
    mem[28198] = 22553074
    mem[57765] = 2310045
    mask = 0101X1X1111000X0011X1010111000100110
    mem[10510] = 8313
    mem[5922] = 72921
    mem[64407] = 1556
    mem[25450] = 1777041
    mem[28796] = 2327
    mem[59755] = 21569957
    mem[60972] = 2783427
    mask = 100011X1100110110X1101011X100X010111
    mem[40715] = 959356
    mem[63146] = 122011
    mem[19713] = 1825336
    mem[45607] = 218
    mask = 100111001XX11X11101101XX1X110101X0X1
    mem[12007] = 98
    mem[26115] = 141260
    mem[54804] = 1053489
    mem[13804] = 14284
    mem[61114] = 912
    mask = 00X0001X01X10X0110X0010101000X0010XX
    mem[17557] = 140533204
    mem[26033] = 2041132
    mask = 10X111010X1X11X0001101X10X1000111X01
    mem[38981] = 1634
    mem[18192] = 56914
    mem[59630] = 563
    mask = 1X0X110111110X01101X00011101X1001101
    mem[41221] = 497
    mem[56726] = 8181081
    mem[1971] = 188296
    mask = 100X11001111111X0X110X00101011010100
    mem[21216] = 381876688
    mem[34122] = 2467929
    mem[2144] = 96144
    mem[54135] = 13354
    mem[53720] = 150
    mem[32315] = 54538146
    mask = 0000X10X11111100011010010XXX0010X000
    mem[18215] = 31721
    mem[32721] = 9183703
    mem[19864] = 62401757
    mem[35244] = 775737
    mem[41775] = 8351542
    mask = 110X01X011X1111011XXX00011X000X11011
    mem[17028] = 8956
    mem[6912] = 1617661
    mem[15337] = 11644529
    mem[43968] = 14512
    mem[8379] = 45870175
    mem[27993] = 549587
    mask = 00X00XX0010100001010010X0110010000X0
    mem[14444] = 53809059
    mem[12350] = 50956019
    mem[26924] = 27242111
    mem[15497] = 25477955
    mem[11723] = 28495396
    mem[22036] = 6534
    mask = 11XX1101X1X00000011110111X110111011X
    mem[23534] = 1008959
    mem[52268] = 340236
    mem[35387] = 1963826
    mem[51744] = 1389733
    mem[37913] = 626
    mem[50832] = 950206044
    mask = 1000X1XX110111X00X110XXX010111011X10
    mem[1361] = 63857600
    mem[52318] = 239923379
    mem[28591] = 568
    mem[40351] = 27729
    mem[19237] = 603
    mem[38475] = 59358178
    mask = 110001111X1111X1011X000010X0X00101X1
    mem[1940] = 44417
    mem[56833] = 11059
    mem[60379] = 5011
    mem[9192] = 59823
    mem[29224] = 357704
    mem[8154] = 13209
    mask = 1100100XX1100X01X1X10011011110001000
    mem[4930] = 145441
    mem[25421] = 12045
    mem[44108] = 46104
    mask = 1X00X1001111X110X11X1011001X01100X10
    mem[22768] = 3060
    mem[4912] = 261459309
    mem[30948] = 398446577
    mem[56062] = 104
    mask = X00XXX0XX11101011X1001000X0110X11111
    mem[51488] = 749
    mem[56696] = 3357828
    mem[14571] = 9057
    mem[5703] = 88
    mask = 1XX00111XX111111011X10X0101X101X1X11
    mem[22686] = 13095
    mem[59326] = 3001957
    mem[63953] = 71522183
    mem[35214] = 3703802
    mem[48938] = 11233495
    mask = 00X000X1X1X11111X110010X000X10100011
    mem[41999] = 936
    mem[58058] = 261362
    mem[54367] = 2216
    mem[61978] = 70816
    mask = X10X11X1111001000X1X00X011010X0XX110
    mem[45798] = 3907
    mem[6617] = 15512
    mem[56811] = 708
    mask = X0100X11X1111X11011011X1X0011X101X0X
    mem[58419] = 3631
    mem[27483] = 38986315
    mem[59244] = 398710
    mem[23764] = 14373
    mem[14069] = 12712994
    mem[40576] = 40270
    mask = 1000110000X11X11011100111X1110000X00
    mem[57219] = 11562875
    mem[41221] = 13920777
    mem[36717] = 3819625
    mask = 1X00X1001X11111X011100XX10100X1100X0
    mem[56785] = 134175
    mem[8778] = 254917
    mem[12948] = 510951
    mem[128] = 860
    mem[11157] = 676314432
    mask = 00000X1X1X1110X00110010X001011001011
    mem[9240] = 1401
    mem[32110] = 1104
    mem[30506] = 88753652
    mem[6582] = 2187931
    mem[12627] = 139691
    mask = 10XX0111X0111111011010001010100X1100
    mem[13077] = 3934
    mem[50479] = 610
    mem[33713] = 72792134
    mem[24686] = 1299
    mem[33343] = 336598
    mask = X00XX10010111110XX00001011X0XX0110X0
    mem[65214] = 6485641
    mem[7967] = 15431108
    mem[54018] = 1706606
    mask = 1X0011X0111111X001110011X00X1111000X
    mem[52561] = 13472068
    mem[56018] = 18723453
    mem[42466] = 85170787
    mem[37684] = 1550
    mem[8720] = 738919690
    mask = 100111001X1111XXX01X000X10101101X1XX
    mem[53713] = 1151
    mem[50072] = 42179504
    mem[7557] = 536670970
    mem[53473] = 62
    mem[30512] = 1563
    mem[61203] = 123868045
    mem[12947] = 280992
    mask = X00X0X1010011110011X11000000X01X00X0
    mem[53713] = 178290029
    mem[36998] = 5983099
    mem[47628] = 5173
    mask = X000010101110101001000X1X10101000X00
    mem[46136] = 244458655
    mem[52984] = 4858009
    mem[22937] = 6676
    mem[23388] = 375417
    mem[64350] = 969
    mask = 000000X10X1101011010010X00001000X11X
    mem[5143] = 27026
    mem[51643] = 38589
    mem[59365] = 1607
    mem[58054] = 1872
    mask = 100111X0101X1110X0100X11111X10010X00
    mem[40606] = 3049691
    mem[12350] = 14661
    mem[35882] = 61044
    mem[14176] = 38552
    mem[2496] = 450375
    mem[46933] = 44633552
    mask = X1X00X110XX1111101101001011000X0101X
    mem[43177] = 822529775
    mem[28838] = 223
    mem[10298] = 849778
    mem[43038] = 105444375
    mem[42666] = 5936816
    mask = X001110010111110101X0X0011XX10001100
    mem[2328] = 76985
    mem[3801] = 1771180
    mem[19235] = 134961
    mem[53720] = 97593730
    mask = 1001110011X10XX1111010XX0X01X0111000
    mem[16129] = 1224773
    mem[18934] = 47178
    mem[17977] = 341
    mem[18583] = 2852768
    mask = 0000XX101111101X0101010X0X0X1000010X
    mem[50612] = 862
    mem[21286] = 41878
    mem[31874] = 10360641
    mem[65381] = 195095
    mem[61682] = 9060
    mem[32198] = 7428006
    mem[32240] = 49912129
    mask = 001X00100101000X101X00X000XXX101X10X
    mem[699] = 21649437
    mem[58127] = 1606
    mem[49337] = 31439847
    mem[3618] = 8032
    mem[29699] = 125520
    mem[29732] = 46761
    mask = 1000010X11X111000XX10101X1X01X100X10
    mem[31281] = 12657
    mem[44108] = 130509615
    mem[19496] = 60937467
    mem[56486] = 3157958
    mem[35746] = 27492
    mask = 1001XX01X111X1X0001X00X1011X00110001
    mem[30357] = 47012
    mem[18233] = 12393
    mem[1099] = 27135
    mask = X1000X111010X1X10110X000X0000011011X
    mem[50296] = 1603392
    mem[35873] = 35369472
    mem[17028] = 200141095
    mem[49219] = 16711
    mem[35720] = 9247398
    mem[19911] = 144739658
    mem[7886] = 9679
    mask = 0010001X0111101101X01X0X010100X110X0
    mem[19896] = 217854
    mem[57355] = 922353
    mem[33713] = 141968390
    mem[41095] = 3604
    mem[12007] = 579276
    mask = 000001X011111XXX011XX0010101X00XX010
    mem[63211] = 223085398
    mem[49337] = 262164785
    mem[48047] = 1784038
    mem[50214] = 121056
    mem[16951] = 6863479
    mem[48471] = 5459
    mask = 1000011010011X0X01X1000X0100101XX110
    mem[8379] = 75563320
    mem[48049] = 2046786
    mem[51605] = 95555128
    mem[20519] = 3218
    mem[54018] = 298192086
    mem[52374] = 3157399
    mask = 1X1000110X1111110110100X0100XX1X110X
    mem[35163] = 119779
    mem[23965] = 261934
    mem[26814] = 4046757
    mem[56811] = 47723376
    mem[26957] = 4810
    mask = 1X001100X011101X0X111010011010X0X111
    mem[37397] = 896
    mem[33586] = 151022681
    mem[51899] = 249489
    mask = 1000X00XX1X1111011110000110011101011
    mem[59834] = 3647285
    mem[9353] = 3923071
    mask = 0000011111X110110X1X010X0110X0X00110
    mem[42000] = 4165519
    mem[33425] = 36
    mem[1188] = 3503480
    mem[3603] = 532
    mem[4150] = 9701863
    mem[6458] = 11777
    mem[16174] = 55920277
    mask = 01100XXX011100011011011X10101X1X0101
    mem[14799] = 1948676
    mem[45433] = 3277
    mem[43873] = 258105154
    mem[26588] = 467687
    mem[36891] = 44643
    mask = 001X001X010100011011000X000X1000X100
    mem[53654] = 21544645
    mem[48482] = 255962506
    mem[39155] = 6486
    mem[5144] = 1463
    mem[1571] = 184289
    mem[32545] = 3427
    mask = 1X0X0100101XX111011X0X1010100110010X
    mem[12534] = 19727
    mem[7245] = 989
    mem[54979] = 56838
    mask = X00111X010XX111000XX0011X01010010100
    mem[47330] = 3421396
    mem[48131] = 29616230
    mem[6868] = 29690
    mem[61114] = 167498
    mem[13174] = 266314582
    mem[46758] = 778753
    mem[6688] = 176052
    mask = 1101X1011110000XXXX100X000100010001X
    mem[16129] = 15847806
    mem[56016] = 66002919
    mask = 1000110010111111XX1X0010101X1X0X0110
    mem[54135] = 10002
    mem[1555] = 16539906
    mem[10358] = 3453
    mask = 1000110010X11X1101111X100010101000X1
    mem[11157] = 279881
    mem[53713] = 9651034
    mem[36785] = 310702
    mem[34588] = 5487
    mem[12063] = 14550145
    mem[23545] = 4041181
    mem[51935] = 745
    mask = 100001XX11011100X0111101101X01000X0X
    mem[8098] = 368361606
    mem[34147] = 2461575
    mem[59550] = 200895
    mem[11123] = 3128
    mem[47604] = 40815968
    mask = 1100X1111X1X11X1011X00001X1110X0010X
    mem[52561] = 78059370
    mem[433] = 1907
    mem[35230] = 3585
    mem[12964] = 1879
    mask = 000X01111101X011X0111011011000X1001X
    mem[35870] = 75432030
    mem[28796] = 5745
    mem[53430] = 17634037
    mem[62177] = 592
    mask = 1X0001X1X1XX11X001X1X00111X011100010
    mem[40863] = 65853640
    mem[20522] = 209364300
    mask = 1X000X0011XX1110XX110X00110X10101011
    mem[2158] = 24116032
    mem[22745] = 1609694
    mem[26857] = 3024935
    mem[30768] = 1210739
    mem[59961] = 4345089
    mask = 1001110XX111110X0011010X01X100101XX1
    mem[1555] = 39088167
    mem[11559] = 1142755
    mem[15057] = 1645973
    mem[36549] = 7583615
    mem[44440] = 969
    mem[50670] = 5038261
    mask = 110010011110010100X10X0XX110X0100001
    mem[35244] = 16194790
    mem[58608] = 62816
    mem[27803] = 13303703
    mem[9172] = 1355842
    mem[26644] = 1340
    mem[49396] = 1051
    mem[12058] = 27414753
    mask = 10XX1100111111100111000010XX00100010
    mem[35746] = 6513
    mem[2226] = 1202233
    mem[50790] = 54270
    mem[8778] = 199450327
    mask = 000001XX1111X011X1X101010X1X101X010X
    mem[31992] = 8657
    mem[646] = 45280
    mem[57264] = 585
    mask = 1000110000X0101XX11100110010X0000101
    mem[6912] = 78358309
    mem[43538] = 229
    mask = 101X00110X111111X1X0101X1100X1X01X0X
    mem[30357] = 438097061
    mem[16494] = 52142984
    mem[7423] = 171318353
    mem[4516] = 1114353
    mem[37939] = 3836
""".trimIndent()