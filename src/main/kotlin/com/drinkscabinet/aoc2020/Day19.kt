package com.drinkscabinet.aoc2020

import com.drinkscabinet.Utils

private fun main() {
    val rules = readRules(rules)
    val regex1 = toRegex(0, rules).toRegex()
    println(regex1)
    println(regex1.matches("aab"))
    println(regex1.matches("aba"))

    inputs.lines().filter { regex1.matches(it) }.forEach { println(it) }
    println(inputs.lines().count { regex1.matches(it) })

    // 348 too high
    // 296 too low

    val actualMatches = mutableSetOf<String>()
    for (i in 1..10) {
        val regexN = regex2N(rules, i).toRegex()
        println(regexN.pattern)
        actualMatches.addAll(inputs.lines().filter { regexN.matches(it) }.toSet())
    }

    println(actualMatches.size)
}

private fun regex2N(rules: Map<Int, String>, n: Int): String {
    val reg42 = toRegex(42, rules)
    val reg31 = toRegex(31, rules)
    return "$reg42+$reg42{$n}$reg31{$n}"
}


private fun readRules(s: String): Map<Int, String> {
    val result = mutableMapOf<Int, String>()
    for (line in s.lines()) {
        val id = line.substringBefore(":").toInt()
        val rule = line.substringAfter(": ").trim()
        result[id] = rule
    }
    return result
}

private fun toRegex(id: Int, rules: Map<Int, String>): String {
    val rule = rules[id]!!
    var regex = rule
    if (rule.startsWith("\"")) {
        return rule[1].toString()
    } else {
        val ints = Utils.extractInts(rule)
        ints.sortDescending()
        for (int in ints) {
            if (id == 11) {
                regex = "(${toRegex(42, rules)})+(${toRegex(31, rules)})+"
            } else if (id == 8) {
                regex = "(${toRegex(42, rules)})+"
            } else {
                val r = toRegex(int, rules)
                regex = regex.replace(int.toString(), r)
                if (!regex.startsWith("(") || !regex.endsWith(")")) {
                    regex = "($regex)"
                }
            }
        }

        regex = regex.replace(" ", "")
        return regex
    }
    // 348 too high
    // 296 too low
}

private val testInput1 = """
    0: 1 2
    1: "a"
    2: 1 3 | 3 1
    3: "b"
""".trimIndent()

private val testInput2 = """
    0: 4 1 5
    1: 2 3 | 3 2
    2: 4 4 | 5 5
    3: 4 5 | 5 4
    4: "a"
    5: "b"
""".trimIndent()

private val testInput3 = """
    ababbb
    bababa
    abbbab
    aaabbb
    aaaabbb
""".trimIndent()

private val rules = """
    75: 105 116 | 40 131
    50: 40 116 | 84 131
    57: 5 116 | 15 131
    20: 128 131 | 84 116
    61: 63 116
    56: 84 131 | 128 116
    51: 116 131 | 131 116
    122: 38 116 | 78 131
    87: 120 116 | 114 131
    130: 131 | 116
    23: 40 130
    3: 131 102 | 116 76
    54: 105 116 | 104 131
    0: 8 11
    31: 116 83 | 131 48
    74: 40 116 | 133 131
    9: 94 131 | 16 116
    70: 128 116 | 104 131
    44: 77 131 | 28 116
    108: 116 105 | 131 94
    66: 16 116 | 105 131
    118: 116 131 | 116 116
    69: 105 116 | 94 131
    78: 116 117 | 131 7
    71: 51 116 | 104 131
    132: 130 116 | 116 131
    53: 116 51 | 131 40
    103: 116 97 | 131 70
    105: 116 116
    46: 105 131 | 105 116
    125: 116 33 | 131 113
    1: 116 68 | 131 68
    55: 104 116 | 94 131
    77: 116 16 | 131 84
    91: 25 116 | 58 131
    43: 116 32 | 131 60
    49: 116 106 | 131 55
    33: 62 131 | 69 116
    99: 116 65 | 131 18
    39: 131 94 | 116 68
    84: 130 116 | 131 131
    124: 20 131 | 13 116
    110: 116 37 | 131 117
    41: 94 131 | 133 116
    13: 131 94 | 116 63
    104: 131 116 | 130 131
    113: 116 129 | 131 108
    64: 116 71 | 131 55
    58: 116 68
    2: 131 40 | 116 128
    32: 53 116 | 9 131
    11: 42 31 | 42 11 31
    73: 131 29 | 116 2
    133: 131 116
    21: 91 116 | 6 131
    134: 116 88 | 131 56
    119: 52 131 | 81 116
    45: 28 131 | 85 116
    115: 86 116 | 99 131
    85: 116 63 | 131 51
    109: 112 116 | 61 131
    10: 131 121 | 116 30
    67: 116 68 | 131 84
    28: 131 40
    129: 116 51 | 131 63
    97: 131 132 | 116 51
    72: 45 131 | 103 116
    63: 131 131
    36: 82 116 | 10 131
    114: 131 84 | 116 51
    79: 110 131 | 89 116
    95: 116 133 | 131 105
    25: 63 116 | 16 131
    24: 131 125 | 116 12
    82: 116 107 | 131 109
    40: 131 116 | 116 116
    121: 112 116 | 100 131
    117: 68 116 | 133 131
    8: 42 | 42 8
    94: 116 131 | 131 131
    60: 102 116 | 62 131
    123: 116 80 | 131 72
    19: 127 116 | 21 131
    80: 131 111 | 116 87
    35: 131 98 | 116 59
    102: 116 84
    48: 36 131 | 24 116
    38: 23 131 | 41 116
    100: 131 133 | 116 34
    17: 116 118 | 131 133
    18: 3 116 | 44 131
    26: 79 116 | 4 131
    37: 116 84 | 131 68
    16: 130 130
    81: 131 128 | 116 133
    52: 16 131
    5: 131 14 | 116 50
    42: 115 131 | 22 116
    34: 131 116 | 131 131
    47: 122 116 | 57 131
    93: 131 95 | 116 46
    7: 130 40
    126: 16 130
    12: 73 116 | 49 131
    131: "b"
    98: 131 104 | 116 128
    30: 39 116 | 7 131
    15: 50 131 | 108 116
    107: 54 116 | 1 131
    68: 116 131
    96: 131 75 | 116 17
    111: 27 116 | 75 131
    89: 116 126 | 131 66
    59: 104 130
    106: 51 131 | 105 116
    92: 67 131
    127: 131 119 | 116 134
    112: 34 116 | 84 131
    62: 132 116 | 16 131
    116: "a"
    88: 40 116 | 128 131
    22: 116 19 | 131 123
    90: 116 92 | 131 124
    14: 16 131 | 105 116
    4: 35 116 | 96 131
    27: 40 116 | 51 131
    65: 64 131 | 93 116
    76: 116 84 | 131 40
    6: 116 101 | 131 74
    101: 131 16 | 116 133
    29: 131 34 | 116 51
    86: 131 43 | 116 90
    128: 130 131 | 116 116
    120: 116 133 | 131 94
    83: 26 131 | 47 116
""".trimIndent()

private val inputs = """
    abbbaaabbbbbbabaaaabbababbaabaabbbbaababbbbbaababbbaabbabbbabbababaaabaaaabaabbbaaababab
    babaaaaaaaaababbbabbbabb
    aaaababbbaababaaabbbabba
    bbbaabbabbbabaaaaabbbaab
    abbbbbbbbabbbbbbbabababa
    abbaababbbaabaaabbaaaaba
    aaabbababaabbbabbbbaababbbabbaaababababa
    aabaababbaabaaabbbaaaabbabaaabbbabbbaabb
    bbbbabaabaaabaaaababbaaa
    abbaaaaabbbbabbabbabbbba
    bbabbabbaaaababbbbbbbbbb
    babaaaaaabbabaabbabbabbb
    aabbbbbbbbabababbbaabaab
    abbaaabaaababbbabbabbbbb
    bbbaababbbbabbbbbaabbbaabaaabbabbbbbaaababbbabaa
    baabbbbaaaabbababaaabbba
    bbabbabaaabaabaaaabaabbabaabbbababbbaaab
    aaabaabaabaaabaaaabaaabaabaabaabbababbbbaaaabbaaaabbbbaa
    abaabbabbaabbabbabaaabaa
    bbbbaabaabbbabababbbabaa
    abbbaababaaaabaabbabbbaaaaaababbaaaabbababbbaaabbbababba
    ababbbaaaababbbaaabbaaabaababbaabbaaabbaabaaababaaaabbbbbbabbababaabaaaa
    aaababbaaabbabbabbaaaaaa
    babaaaabaabbbabbbaabaaaaaaabbbbabbbbabab
    aaaaaabbaaababbaaabaaaba
    babbababbbaabaaaaabbaaaa
    aaababbaabbbbaabbabbaaba
    aaaabbabaabbbbbbbaabbbbabbbaaaab
    bbaaababbaaaababbbaababb
    bbbbbbabaaaabbaaabaaabbbbaaabbab
    babbabaabbabaaabbaaaabababaabaabaaabaaab
    baabaababbbbbbbbaaaaabaabaabaaaa
    bbaaaabbabbbabbbbabbaabbaabbbbababababab
    abbabaaabbabbababbabbaaaaaabaabb
    bbbabaaaabbabaababaaaaba
    babbabbaaaaaaabbbbbbbabb
    baabbbbbaaababaaaaaaabaa
    bbabaabaababbbaababaabaabbbaaaabaabababbbbbaababaababbabbaaabaaa
    aaaaabaaaabbbbbbababbaaa
    bbabbbbabbaaabbabbabbabaaaaabaabaabaaabaaaababababbabababaabababaaaabbab
    aaaaaabbbababbbbbaaaaaaa
    bbaabaaabbbabaaabaaabbab
    bbaabbabbaaaabbabaabbbbbababaabbbabaaaabbbbaabaa
    bbabaabbbabbaaabaabbabbb
    aaaabaaaabaaaabaabbbbaba
    baaaabbabaabaaabbabbbabb
    babbaaaabbbaabababbbbbaabbabbbaa
    bbaaabaabaabbbaababbaaba
    aaaababbbaabbbaabaaabbaa
    abbaabbababbaaababbaaaab
    aabaabbbaaaaaaaabababbbbabaabbba
    abbabbaabbaaabaaaabababb
    aaabbababbbabababbaababb
    babaabaabaaabaabaaababaabaabbbabaababaabbbbaabaaabababaabbabbbab
    abbaababaaabaababbbbabaababbbbaa
    aabbbbabbabaabbaaaabaaaaaababaaabbababba
    ababaababaabbbaabaaabaaabaaaaaab
    babbabaaababbbababaababb
    aaaaabbbabaaaabaabaabaaabaabaaaabbbaaaba
    baaaabbabbaabbbbabbaabbbaabbbaaabaabaaabbbbaabbb
    abbaabbbbbaaababbabbaaba
    abbabaaabbabbaaabbabbbaa
    abbaabbbbabbbbbbababbbaaabbbaaaa
    abaaababbaaaababaabbaabb
    baabaaabbabbabbabaaaaabbabaabbbbbbaaaaba
    bbabaaabaaabbbaabbbabbaaabbbbbaa
    aaaababbabbbababbaaaaaababaaaababaabbababbababbaabbabbbaaabaaaba
    baabaabbbabbaaabbbaabbaa
    baabbbbbaabaabaabbbbaaaa
    bbaaaabbbbbbbaaababbababaaaaabab
    abbbbbbbaababbbaaaaabbba
    baaabaaababbabbabbbaabbaaabbaababbbbabbabbabbabaaabbbabb
    bbaabbabaabaabbaaaaabaab
    aabbbbbbabbaaabbbaaabbab
    abbbababbabbaaaaaaaaaaaabbbbaabb
    abbaababbaabbbbaabbababaabababbaabababab
    bbabbbabbbaaaaababaaabbb
    baaaaabbbbabababbbaabbba
    baabbabbaabbababbaababba
    ababbaabaababbbbbbaabbba
    bbabbababbbaababaaaaabab
    abbaaaaabbbbbabbabbbaabbabaabbba
    bbbbabbabaaababaaaaabbababbbabba
    abbabababbbbaababababbaaabbaaaaababaaabbbbbaaaaababbbaabbaababbb
    babaaaaabaaabbbbbbabbbabaaaababa
    abababbbbababbaaababaaba
    ababbbabbaaabaabaaabababaabababaaaabbaaaabaabaaa
    bbabaaaaaababaaabbbaabaa
    aaaaaaaabbaabbabbabbbaba
    aaaaabababaaaaabbabbbabb
    abbaaaaaabbaaababaaabbab
    baaabaaabaaababaabaabbbb
    abbaaaaaabbaabbabaabaaaa
    ababaababbbbbbbbbababbbbbababaab
    babaababaaaaaabbabbbaaaa
    ababbbababaabbabbaabbaaa
    abaabbaabbbaababbbbabbbbabbbaabb
    baaaaabbbbbbabaabaaaaaba
    baabaabbabababbbbbbaabaa
    aabaabbbaababaaabaabbabbaaaaabab
    bbaaababbbaabaaababbbbaa
    babaaabbbbbbbbabbabababaaaaaabbabaaaaabbbababbabbbababaabbaaabbbbabaaabaabaaabaa
    aaaaabaababbababbbbaaaab
    baaaababaabaabbaaaabbaab
    aaabbbaaabbbbbbbaaaaabbb
    abaabbabaaabbbbbbabaaaaabababbbbbbaabbaa
    aaabaaaabbbbbbabbabaaabbbaaaaaaa
    baababaaaabbaababbbbbbbbaaabbabaaaababbbbbaaababbaababbaabaabaababababbaabbbbaba
    bababbaababaababbabbbbaa
    baaababaaabaababbbabababaaabaaaaaaabbaaa
    bbaaabaabbbbaababbbbbaaaaababbaa
    abaabaaaabaabbababaaaaaaaaaaabaabbbaabbabbbabbbbaaaabbabbbabbaaaabbaabaaabbbbbba
    bbabbaaaaaaabbabaabbbaab
    bbabbaaabbabbbabaabaabbabaaaabbaabbbabbb
    babbaaabaaaabbaaaabaaabb
    aaaabbabbaababaabaabbaaa
    babaaaaaaaaaaaaaababbaba
    baabaaabbababbbbbaaaaaaa
    baababbaabbbbbaabbabaabaabababaaabababbbbabababbbbbbbbbaaaababbb
    bbabbaababbaabbbaabbbabb
    baaaababbbbabbbbbaaaaabbaaaaabaabbbbbbaaabbababaaaaaaababbaaaaaa
    bbaaabbbbbbbbaaaabaabbab
    aaaaaabbbbabaaabbbabbaaaaaaaabab
    abbabbaabbbaababbbaabbaa
    abababbbaaaaaaaabaaaaaaa
    aabaaaaabaaababaababbaabbbaabbabaabbbabb
    abbbaaaaaaaabbbabaabbbbbbaaabaabaaaaaabbaaabbaabbbababbaabbbbabb
    abaabbabaabbabaababbbbabbaababababbaabbbabababbbbaaabbaa
    aabbbbbaabbabaaaaaababab
    abbaaabbabbaabbbabaabbba
    baaabaabaabbbaaabbbabaaabaaabaabbbbbbbabbababaaa
    abababbbaaabbbaababaaabbbabababb
    abbbbbabbbabbabbbbaaaaaa
    bbaabbabaabaabbabaabbbbbbaaaaaabaababaab
    aabbbbaabbaaababbaaaabbb
    baabaaabbaaabaabbabbaaba
    aabbbaaaabbaaabbabaaaaab
    aaabaaaaababbbaabaaaaaab
    abbaabbaabbabbbaaababbab
    aaaaabaabaabbbabbaabbaaa
    bbababaabaaabaaabbbbabbb
    aabbbaaabbaaaaabbaaababa
    bbabaaabbaabbaaabbabababbaabbbabbbbbabbabaaabbaa
    abbaababbabbaabbaabbabbb
    ababbbbbaaaaabaabbaabbaa
    aabababbbbbababbbbababba
    babaaaababbbababaababbbbbbbbaabaaaaaabaabababaab
    aaabbabaababbbabbbaabaab
    abbaabbaababaabbaabbbbbaabbaabababbbabbbbaaaaabbabbbaaabaaabbaab
    bbbbabbaabbabbaaabbbabba
    bbbbbbbbbbbabbbbbababbab
    aaabbaabbbaababbbaabbaabaabbbaabbaabbbabbabaaaabaaababbaabbbbbba
    bbabbaabbabbbbabbbbabbbbbbaaaaabbbaaabba
    bbbababaababbaabbaaaaaab
    aabbababbbaaabbbaababbaa
    abababaaababaabaaaaabaaabaabbbbbbabbabab
    ababaababbabbaababbbaabb
    aaabbbaaaabbbaaaabaabaab
    aabbababaaaabbaabbbbabbaaaabbabbabbbabbabaaabababaababbaabaababaabbabaababaabbbb
    babaaaaabbabaaabaabaaabb
    bbabbaababbabaaabbabaabbbbaaabbbaaaaaaab
    bbbaabbbbababbaabbbaabbaabbaabbb
    bbabababaaabaaaaaabbbbbaabaaabababbabbaaaaaababaabaabaab
    bababbaaabbbababababbabb
    baabaaabbaabaabbaabababb
    abbbaababaabaabaabbbaaab
    aaaaaaaaaaaaaabbababbbba
    baabbababbbaaaaabbbbaabababbbbaa
    ababbbbbababbaabbbaaabba
    bababbbbaabbbbbbbbbaaabb
    baaaaaaaaaaabbbaabababaaabbaaaababbbbbbbabaabbbaaaababaabbbabaabbbabbbbbabbbababbbaabaaa
    bbaabbabbaabbbaabbbbaabb
    abbbbaababababbbbabababb
    bbbabaaabbaaaabbababbbbbababbbba
    baaaababbbbabaaaabaaaabb
    abbaaabbaabbaababbbbbbba
    baaaaabbbaabaabababbababababababaaababaababbbbbaabababba
    aabbbbaaabbbbbbbabbabaaabbbaaabb
    abaabbaaaaaabbaabaabbbbbbbbaabbaaaabbababbbaabaabbbbaabbbaaaabaa
    ababbbabbbbbabbabbbbbabb
    bbabbbabaabbbbbaaabbbbabbbabbbbb
    baabbbbbabbabaaaaaaabaab
    baaaabbabbaaaaabbaabaabaabaaabbb
    baaaaabbabbbababbababbab
    abbaabbaaababbbabbbabaab
    aaabaaaaababaababbbabbbbbbbbaababbaaabbbbabbbaaabaababba
    babbaabbbbabaaababaaaabb
    bbbaabbaabbbbbabbbaababa
    bbaabbabbbbbabbabababaaa
    aabbabbabaaabaaaabbbbbba
    aabaabbaabbaabbbabaabbbb
    bbabbaaaabbbababbbbabbabaaabaaaaaababababaababba
    ababbbbbaababbbbbaaaabbaaaabaaab
    abbbbbbbbbabaaabbbbabaaaaaabbbbbabaaaabaabbabbab
    bbaabaaaabbbaabaaaaabbaaababbbaaabaaabbabbbbaaab
    aababbbbaababbbbaaababbbbaaabaabaabbbbabbbabaaabbbbabaabbbbaabaabaababbaaaabbbabbbbbbbba
    aaaabaaaaaabbabbbabbbaabbbaabaab
    abbbaaabbbaaaaaaabaababa
    babbbababaabbbbababbbbbabbabbaabbaabbbabaaabbaaaaababbaaabbbbbaaabababbaabbababa
    bbaaabababbaaababbbbbabb
    aabaabbbbbabbabababaabaa
    bbbaabbbbbaaaabaaaaaaaba
    bbabbabbaababaaabaaabbaa
    bbaaaaabbabbbbbbabbbbbbbababaaaa
    bbbbbbabbabbabbabababbab
    ababbbabbaababaababbbbba
    babaaaabbabbaabbababbabb
    abaaabbaababaaaaabaaabbb
    aaaababbbaabaabbbbabbabbbaaabbab
    baaabbbbbbbaaaaaaaababbaaaaabbbb
    baaabaabbbabbabbbababaab
    aabbabaaaababbbbaabbbaab
    bbbbbbbbbbbabbabbbaaaaba
    aabbbbbbaabbbbbabbaababb
    bbbabaaaabaabbabaabbaaab
    bbbabbbbbbbaabbbabaabaaa
    baabbbabbbbbbbaababbaaba
    abbaaaaabbbaabbbabbbbbbbabaabbbb
    bbaabbbbbaaabbbbbbababaababababaabaabbbb
    abababbbbbabbbbabbbaabbbaabbbbbbbbabaaababababbaabbbbbababaaabba
    abbbbbabaabbabaaabaaaaab
    bbababaababbaaababbbbbaababbabbaabbbbaabaabaababababbaaa
    abbbbbaaabababbbbabaaaabbbaabbbababababa
    babbabbaaabbabaaaaaaaaba
    aabaaaaaabbabbaaaaabbbba
    bbbbbbaaabbaababbababaab
    aaababbbbbaaabaabababaabaabbbabbaaaabbba
    babbbabbaaabbaabbaaabbbaabbaaaabbbbaaaab
    babbbbbbbaaaabaaaabbabbbaaaabbbaaabbaaaaaaabbbbbaabbababbaaaaaaaaaabbbbbababbbaabbaaaaaa
    baaaaabbbabbbbbbaababbbbbaaabbbabaabbaab
    aabbbbbbabbbbbabaabababa
    baabaabbbbbaaaaababbbaba
    aaabaabaaabaababbaabaabababbbaaa
    abbabbbabbbbbbaaaababaab
    abbaaabbbabbabbababbbbabbababaaaaababbaa
    abaaabbabbbabbbbabbaabbbbbbabbabbaabbbbbaaaaaaab
    bbbbbbbbabbbbaaabbaaaaaa
    aababbbaaababbbbbbabbbbb
    baaababaaabaabaabbabbbaa
    bbabbaaababaabbaaaabbbba
    babbabbababbaaaabbbabaaabbbaababababbbababbaaaab
    baabbbababbbbaabbaabbaab
    aaababbabbababaababaabbb
    aaaaaaababbbaaaababaabbbabbaaaabaabababbabbaaabbaabaaabb
    baaababaaabbabababaabbbb
    bbbbbbaabaabbabbababbbabbbababba
    aabaaaaababbabaabbabaaba
    babaaabaabbaaabbabaabbabbabbbaaa
    abbabbbbbbbbbaabbbababaaababaabaabbbaaabbaaabababbaaaaaaabbbabaaaaababab
    abaaababbbaaababbaabaaabaabaabbbbbaabbab
    bbbabaaabbabaaaaaaabaaab
    baabaaaaaabbbababababbbabababbab
    bbbbbaaaaaaabbbbbaababaababababaabaabaaabbaaaabbbabababbaaabbaaaaaaaaaba
    abababbbbabbabaaaabbbabb
    bbbbbabaaababbaaaaaabbbaaaaabaabbaaababbbabababbbabaaaaabaaaaabababaaaaaababababbbaaabba
    ababababaabbbaabbabbaaabbaabaabaababaaabbabbbbbabbabaabbbbbaaaaaaaaabbbbbbbaaabb
    baababaaaabaababbbbaabaaaabbbabbabbbbbbaabbaaaab
    bbaaabbbbbabbababbbbbbba
    abbaaabbbbbaabbababbaaabbbbbbbbaababbaba
    abbbabababbbababbabbaabbbaabbababaaaabaaaaaaaabaabaaaaaa
    bbbaabbabbbbbbabbbaababb
    babbababbbababaabaabbbabbabaabaaaababbaa
    baaabaaaababaabababaabbb
    bbabaaabbabaabaabbbbbaababababaabbabaabaaaaabaaa
    baabbbabaaaabbaababbaabbabbbabaaabaabaaa
    babaaaaababaaaaabbabaabbabaabbba
    abaaababbaabaaabbbabababaabbabbbabaabbba
    bbbbabbaabbbbbaaababaabb
    baaaaabaaabbbabaaaaaabbabaaabababaaabaababbabbabbbabbaabbbbbaabbababbaba
    abaabbababbabaaaabaaabbb
    aabaabbabbabbaaaaabaabbbbbbaaaaa
    baabbabbaabbbbbaaaaaabaabaabbbbaaabbaababaaaaabbabbbabbabbbabbaa
    aabaabbbaababbbaababbbabaabaaaba
    aaaaabaaaabbbbbbaaabaaab
    aabbbbbaaaaaabaaabbbbbba
    abbaaaaababbaabbaababbab
    abaaababaaabaaaabababbaababbaaaa
    baabbbbbaaaabbabbbaabbba
    bbbaabbaaaaabaabbbbbaaaaabbbababbabaaaabababbaaaaaaaabab
    abbbaabaababbaabbaaaabbaabbaaabbaaaabaab
    abbbbaabaababaaaabaaabaa
    baabaabbabbbbbbbbbaabaab
    ababaabbbbabaaaaaaababbaaaabbbabbababbba
    bbbaabababbaabababaaabbaaaaabbaaaaaababbbbbbaabbbbbabaab
    aababbbabbbbabaabbaaabba
    aabbababaabaabbaaaaabbbb
    bbaababbabaaaabaabababbaabbaaabaaaaaababaaabaaabbabababbabaababb
    baabbbaaaaabbbbbaaabaaab
    aaabbabaaabaaaaababbabbabababaabaababbaa
    abbbbbbbbbbbaabaaaabaabb
    babbaaabbbababbbaaabbbbabbababbbbbbaaaababababbaabbaababbaaaabbabbbaabaa
    abbbaaababaaabaababbbabbbabaaaaaabbaabbbaaaabbabbaabbbbbaabaabbbbbbbaabbaabaaababbaabbbb
    baaabaaaaabbbbaabbbaabbaaaabbabb
    aaabbbbbbbbbabaabaaabbba
    abbaaabaaababbbaabbbabaa
    ababaabbbabbabbabaaaaaab
    ababbaaabbbbbbbbaabaabbabaaaabbbaabaabbabaaaaabbaaabbbbbabbaabaababaabba
    aababbbabbaabbababaaaabb
    abbabaaaaaaaabaabbbbaaaa
    babaaaabbaaaabbaaabbbaba
    ababbbbbbbaabaaaabababbbaaabaaab
    babaaaabbaabbbabbbabbbbb
    babaabbaaabbbbaabbaaabbbbabaaaaabbaaaaaa
    abaabbbbaaabaaabbbbaaabaabaabaab
    babbbbbbabbabaaabababaab
    bbabbabbaaaababbbaaabbaa
    abbabbbababaabbababbabbabbabbbaa
    bbbabbbbabaaabbabbbaaaba
    baaaababaaaaabbabbaabbbb
    bbbbbbbbbabbbbbbabbababb
    bbabbbabaabbababaabbaababaabbabaabaabbaaababaaabababaaabaabbbaba
    aababaaabbabaaabaabbbbbbbbaaaaaa
    ababbbaaabbbababaabbaabb
    abbaabbaabaabbaaababbaaa
    aabbbabaabbaabbaabbbbabbbabbbabbabbaabbbbbbaabaa
    bbaabbabababaaaaabbbbaba
    bbabbababbaaaaabbbbaaaab
    aabbbbaabbaabbabbbaaaaaa
    baaabaabbbbababaabaaaaaa
    bababbaaaaabaaaababaabababbabaabbabbababbabbbabbbbbaaaabbababbba
    bbaaaabbaabbabbaabababab
    baababaabaaabaaaabbababb
    baabbbabaababaaaabbbbbabbbbabbabaaaaaaaaabbbbbba
    abbbbaaaaabaaaaaabababba
    abbaabaaaaabbabababbaabaaaabaaabbaaaaabbabaabbaabaaaabbbabbbabbbaaabaabb
    baaaababbbbbabaabbaaabba
    aabababbaaabbababaaaaaabaaabbaaa
    babaaaabbbbaabbbbbbbabaababaaaaabbbaabbbaaabbbbbaabbaaaa
    bbbababaaaaaabaabaaaaaba
    babaaaaabaabbbababbabaababababaabbaaabba
    ababbaabbaabbbbbaabbaaba
    ababaababbbaabbbabbbaaaa
    bbabbababaabaabaaaabaabb
    abbbababbababbaaaabababa
    bbaaaabbbaaaabbabaabbbbabbaaaabbabbbbbbbabaabbbabbbabaabbabbaaba
    babbabaabaaaabaabbbaaabbaabaaabbbaaaaabbbababaaabbaababbbabbbaab
    baabbabbbaabbbbbbbbaabaa
    babbababbaaaaabbbabbbaab
    aabaabaaabbbbaaaaabaaaaabbaabababababaaa
    abbbbbabbaabbbbbbbbaaabb
    bababbbbbbaaababaaababaabaabbaaa
    abbabaaaabbaabbbbaabababbbbbbbabbbabbbbabbbaaaba
    bababbaaabbaababbbaaaaba
    aaabababbbbaaabbbaabbabaaababbbaabbbaababaaababbababababbabbabaa
    aabaabbbbabbbbabbabbbaba
    abbbbbabaabbabbaaaaaabba
    bbbabbababababaabbaaabbabbbaaabbbabbbaba
    baaabaaabaabbbbaaaabbbab
    abbaaaaaaaabbbaabaaaaabbabbbbbaaabababababaabbbb
    aaabbabbababbbbaaabbabaaabababbababbbaaaaabbbbbababbaaabbbaabaab
    babbbbabbaabaaabbbaaaaababbbbaabaabbaaab
    bbabbabbabaabbbaaaaaabbaabaabaabbabbaabaaabbabbb
    aaababbaabbaaabaababbbaabaabbabbaabbaabb
    abbbabaaaabbaaabbabbbbababaabbaaaabaaabbaabbbaabbbabbabb
    aaabbababaabbabaaabbbaaaabbabaaabbaaababbaaabbabaaabbbab
    ababbbabbbbabbabbaabbbababbabaaaabaabbbbaababaab
    abbabaababbbbaabaaaabbaaaababbbababababbbaaaabbb
    aaabbbaabaaabaaabaabbabaabaaaababbbbaabb
    abbabbaabaaabaaaabaaaaab
    aababbabaaabbabbabaabaaababbbbbabaabbbababbbbbabaabbbbbbaaabbaaa
    bbbbbbaaabababbabbbbbabb
    baabababbabbbbabbbabaaaabbabaaaabbbbaabbbaabbbbabaabbbbbbaaababbabbbbaababaaabba
    babbabaaaabaabbaaabaaaab
    baaabaaaaabbaababbaababb
    aaaaaabbbbbbbbbbaababbab
    abbaaabaabaabbabbbaabbbbbbbababaababbaaa
    aaabaaaaabbbbaababbaabaa
    babbaaabbabbababbaaabbab
    aabaabbabbabbabbabbaaababbbbbaaabaabbbababaaaaaabbaababb
    bbabbbabaabbbbabaabbabbabababbab
    ababaaaaaabaababbaababba
    baaaababaaaaaabbaaaaaabbababbabb
    aabaaaaabbbaaaaababbbaab
    aabbaababbabbabbbbabaaba
    aaabaaaabbbbabbaaabbaabb
    abbaabbaaaaabbabaaaabbabbabaabaababbbbba
    baaaabbaaabbbaaabbbabbaaabaabaab
    abbabaabababbbbbbbaababb
    baaabaaabbbaabbababaabaa
    babbaabaabaaabbbbbbabbaaaaaaaaabababbaaaaaaaabaabbbaaabbabaabaabbbaaaaba
    abbbbbaabababbaaaabaabbaabbaabbabbbaabbabbabbbbb
    bbbbabaaaabbbaaaaabbaaaa
    aaabaaaaabababbbbaaabbba
    aabbbbabbaaaababbaaaaaba
    babaaaabbaaaaabbabbababa
    aaaabaabbaabbbbabbbbabaababbaaabaababaabbaabbbbaaaaaaaaa
    abbbbbabaabbabaaaaababaa
    ababaaababaaaabaaabbbaabbaaababb
    aaabaaaabaabbabaaababaaabbbbabaaaabbbaab
    babaaabaaaababbabbabaaabbbaabbabbbabaabaaaaaaaba
    bbaabbbbbbabbbbbabbbabbabbaaaabbbaaabaaababbbbaaababaaabbaaaabbaaaabaabb
    aabbaabaaaabaabaaabaaaba
    babaaabbbbaaaaabaaaabaaa
    baabaabbbaaaababbabaaaaaaaabaabb
    abbababaabbaaaababbbaaabbbaabbbabbbbaaababbabbabbbabaaaabbabababaaabbbabaaaaabab
    bbbabbbbbabaaabbabbbbbbbaabbababbbaaabba
    bbbababababaababaaaabaaa
    bbbbabaabbbbaababbababbb
    ababaabbbbaaaabbbbababbb
    abaaababaaaaaaababbbbbbbbbbbaaaaabbbaaabaababaabaaabbbab
    babaaabbaabbaabaaabbbaab
    baabaabaaababbbbbabbbbbbabbabbab
    baabbbabbbaabbbbbabbbbaa
    ababbaabbabaabababbbbbaabbaaababaaaaaaaabbabbbbbaaabaaaabbbabbbaaabbbaab
    baababaaabbbbbaabbbaabbaababaaaabbaaaabbbbbbaaabaabaaaba
    bbabbaabbbaabbabbbbbbbba
    baabaabaabababbbaabaababbbbbbbbbaaaaababbbaaaaba
    babbaabbbabbbbbbbabbabbb
    abbbbbabbbabaabbbaaabbab
    aabbabaaaaababbaaaabaabb
    baaaabbaaababbbabaaababbbaabaabaaaabbabbbabbabab
    babaabbabaababaaaabbbaba
    abbbaabbbabbaaaaaabbabbbabbababaaaaaabaaaababaabababbbbbabbaaaaaaabbbaabbaaababbbaababaa
    baabbbaabaaaaabbabbababa
    aaaababbbbbbbaaaaabbbabb
    bbbababaaaababbbbbbaaaab
    aabbabbaabbbaabbbbbaaabbbababbbbbbbbabbaababbabbbbabbbbbabbababa
    bbaaaaabaababbbabaaaaaaa
    abaabbabbbaaaabbaaaaabab
    ababbaabbaabbbabaaaabbbb
    baaaaabbaaaabbaaabbbbbaaaababaaabbabaaabbabaabbbbbbabbbabbaabaabbaababbaaabaaaba
    ababbaabbbabaaabbbbabbaaabaaabababbaababbaabaaaa
    bbaaaabbbaabbabbaaabbaaa
    babaaabaaabaabbbbaaaaaba
    bbbbbbbbaabaabbbbaabaaabaabaaaab
    bbbbaabaaabbabbaabaaabaa
    aaaaaababbbbaaabbbbbabbaababababbbbabababaabaabbababbaaaaaaaaaab
    bbabbabaaaaaaabbbabbbbabbabababaaabaaaab
    bbabaabbaabaabbaabbbbabb
    aaabbbbbabbaaaaabbbaabbabaaaababbbbbabaabbbaabaa
    bababbaabbbbbbbbabbbaababbabaaba
    aabaabbbbbbabaaaabbaabbaabbaaaaaaabbababbabababaabbbbabaaabaaaabbbaababb
    bbabbaabbbaaabababbbbaba
""".trimIndent()


val x = """
    (((((((((((((((b((((a((((((((a((((ab|ba))))|b((((ba|aa))))))))a|((((((((ab|bb))))b|((((b|a))((b|a))))a))))b))))|b((((((a((((((b|a))a|bb))))))a|((((((((((b|a))a|ab))))a|((((b|a))((b|a))))b))))b))))))))|a((((a((((((a((ab))|b((((((b|a))a|bb))))))))b))|b((((((((((((((b|a))b|aa))))b|((((((b|a))a|bb))))a))))b|((((b((((ab|bb))))|a((bb))))))a))))))))))))a|((((a((((((((a((((((((ab|ba))))a|((((ba|((b|a))b))))b))))|b((((((((ba|((b|a))b))))a|((((ab|bb))))b))))))))b|((((b((((a((ba))|b((aa))))))|a((((((aa))b|((aa))a))))))))a))))|b((((((((b((a((((((b|a))a|bb))))))|a((((a((((((b|a))a|bb))))|b((((ba|aa))))))))))))a|((((((((a((((b|a))((b|a))))|b((((((b|a))a|bb))))))))b|((b((((ba|aa))))))a))))b))))))))b))))b|((((a((((((((b((((((((((b|a))((b|a))))b))b|((((b((((((b|a))b|aa))))|a((ba))))))a))))|a((((a((((((((ba|aa))))a|((((((b|a))b|aa))))b))))|b((((((((((b|a))a|bb))))b|((((((b|a))b|aa))))a))))))))))))a|((((((((((((((bb))a|((((b|a))((b|a))))b))))a|((a((ab))))b))))a|((((a((((b((((b|a))((b|a))))|a((ba))))))|b((((((((ba|aa))))a|((ba))b))))))))b))))b))))|b((((a((((b((((((((((((ba|aa))))a|((((ab|ba))))b))))a|((((((aa))a|((((ba|aa))))b))))b))))|a((((((((a((ba))|b((((ab|bb))))))))a|((((b((((((b|a))a|bb))))|a((((ab|ba))))))))b))))))))|b((((((((((b((((ba|aa))))))b|((((a((bb))|b((((ab|ba))))))))a))))b|((((a((((b((((((b|a))a|ab))))|a((((ab|ba))))))))|b((((((((((b|a))b|aa))))a|((((ba|((b|a))b))))b))))))))a))))))))))))a)))))+(?<grp11>(((((((((((((b((((a((((((((a((((ab|ba))))|b((((ba|aa))))))))a|((((((((ab|bb))))b|((((b|a))((b|a))))a))))b))))|b((((((a((((((b|a))a|bb))))))a|((((((((((b|a))a|ab))))a|((((b|a))((b|a))))b))))b))))))))|a((((a((((((a((ab))|b((((((b|a))a|bb))))))))b))|b((((((((((((((b|a))b|aa))))b|((((((b|a))a|bb))))a))))b|((((b((((ab|bb))))|a((bb))))))a))))))))))))a|((((a((((((((a((((((((ab|ba))))a|((((ba|((b|a))b))))b))))|b((((((((ba|((b|a))b))))a|((((ab|bb))))b))))))))b|((((b((((a((ba))|b((aa))))))|a((((((aa))b|((aa))a))))))))a))))|b((((((((b((a((((((b|a))a|bb))))))|a((((a((((((b|a))a|bb))))|b((((ba|aa))))))))))))a|((((((((a((((b|a))((b|a))))|b((((((b|a))a|bb))))))))b|((b((((ba|aa))))))a))))b))))))))b))))b|((((a((((((((b((((((((((b|a))((b|a))))b))b|((((b((((((b|a))b|aa))))|a((ba))))))a))))|a((((a((((((((ba|aa))))a|((((((b|a))b|aa))))b))))|b((((((((((b|a))a|bb))))b|((((((b|a))b|aa))))a))))))))))))a|((((((((((((((bb))a|((((b|a))((b|a))))b))))a|((a((ab))))b))))a|((((a((((b((((b|a))((b|a))))|a((ba))))))|b((((((((ba|aa))))a|((ba))b))))))))b))))b))))|b((((a((((b((((((((((((ba|aa))))a|((((ab|ba))))b))))a|((((((aa))a|((((ba|aa))))b))))b))))|a((((((((a((ba))|b((((ab|bb))))))))a|((((b((((((b|a))a|bb))))|a((((ab|ba))))))))b))))))))|b((((((((((b((((ba|aa))))))b|((((a((bb))|b((((ab|ba))))))))a))))b|((((a((((b((((((b|a))a|ab))))|a((((ab|ba))))))))|b((((((((((b|a))b|aa))))a|((((ba|((b|a))b))))b))))))))a))))))))))))a)))))+(((((a((((((((((((((((a((((a((((((b|a))a|bb))))|b((ab))))))|b((((((ab))a|((ba))b))))))))b|((((a((((((b|a))((b|a))))((b|a))))|b((((((((b|a))((b|a))))a|((aa))b))))))))a))))a|((((((((b((((b((((ba|((b|a))b))))|a((((((b|a))b|aa))))))))|a((((((ba|((b|a))b))))((b|a))))))))a|((((b((((((aa))a|((((ba|aa))))b))))|a((((a((((ab|aa))))|b((ba))))))))))b))))b))))b|((((((((((((((((((ba|aa))))((b|a))))b|((((((((ab|bb))))b|((ba))a))))a))))a|((((a((((((ab))a|((ba))b))))|b((((b|a))((((ba|aa))))))))))b))))a|((((((((b((((((((b|a))((b|a))))b|((aa))a))))|a((((((((ba|aa))))a|((((((b|a))a|bb))))b))))))))a|((((((((((((ba|aa))))a|((((((b|a))a|bb))))b))))b|((((a((aa))|b((((ab|bb))))))))a))))b))))b))))a))))|b((((((((((((a((((((((((aa))a|((((ba|((b|a))b))))b))))a|((((a((ab))|b((ab))))))b))))|b((((((((((((ba|bb))))a|((((((b|a))a|bb))))b))))a|((((bb))a))b))))))))a|((((b((((((((((((ba|bb))))a|((((((b|a))a|bb))))b))))a|((((b((ba))|a((((ba|bb))))))))b))))|a((((((((b((((ab|bb))))|a((ab))))))a|((((b|a))((((ba|aa))))))b))))))))b))))b|((((b((((a((((((((((((((b|a))a|ab))))a|((((b|a))((b|a))))b))))b|((((((aa))a|((((ab|bb))))b))))a))))|b((((a((((a((((ab|ba))))|b((bb))))))|b((((a((aa))|b((((ab|bb))))))))))))))))|a((((((((b((((b((((ba|bb))))|a((((ab|ba))))))))|a((((b((((ba|aa))))|a((((((b|a))b|aa))))))))))))a|((((a((((((((ab|ba))))b|((aa))a))))|b((((((((ba|((b|a))b))))a|((((ab|bb))))b))))))))b))))))))a)))))))))+)))
""".trimIndent()