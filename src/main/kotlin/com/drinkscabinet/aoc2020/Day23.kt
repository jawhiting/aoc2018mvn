package com.drinkscabinet.aoc2020

private fun main() {
    part2()
}

private fun part1() {
    val ring = Ring.parse(input)
    println(ring)

    for( i in 1..100 ) {
        // pick up 3 next cups
        val nextCups = mutableListOf<RingElement>()
        println("Move $i")
        println("Cups: $ring")
        for( c in 0..2 ) nextCups.add(ring.top!!.removeNext())
        println("Cups2: $ring")
        println("Picked up ${nextCups.toList()}")
        var destinationVal = ring.top!!.value - 1
        if (destinationVal == 0) destinationVal = 9
        val unavailable = nextCups.map { it.value }.toSet()
        while( unavailable.contains(destinationVal)) {
            destinationVal -= 1
            if (destinationVal == 0) destinationVal = 9
        }
        // Find destination pos
        val destinationPos = ring.getElement(destinationVal)
        println("Destination val: $destinationVal")
        // now insert the values after destination
        for( c in 2 downTo 0) destinationPos.insertAfter(nextCups[c])

        // now shuffle current to the start
        //its always the next to the right of current, so just shift
        ring.setTopElement(ring.top!!.next)
    }
    println("Final: $ring")
}

private fun part2() {
    val ring = Ring.init2(input)
    println(ring)

    for( i in 1..10_000_000 ) {
        // pick up 3 next cups
        val nextCups = mutableListOf<RingElement>()
        if( i % 100000 == 0 ) {
            println("Move $i")
            println("Cups: $ring")
        }
        for( c in 0..2 ) nextCups.add(ring.top!!.removeNext())
        var destinationVal = ring.top!!.value - 1
        if (destinationVal == 0) destinationVal = 1000000
        val unavailable = nextCups.map { it.value }.toSet()
        while( unavailable.contains(destinationVal)) {
            destinationVal -= 1
            if (destinationVal == 0) destinationVal = 1000000
        }
        // Find destination pos
        val destinationPos = ring.getElement(destinationVal)
        // now insert the values after destination
        for( c in 2 downTo 0) destinationPos.insertAfter(nextCups[c])

        // now shuffle current to the start
        //its always the next to the right of current, so just shift
        ring.setTopElement(ring.top!!.next)
    }
    println("Final: $ring")
    // cups to right of 1
    val one = ring.getElement(1)
    val next = one.next
    val next2 = next.next
    println("$one $next $next2")
    val result = next.value.toLong() * next2.value.toLong()
    println(result)
}


private class Ring() {

    private val elements = mutableMapOf<Int, RingElement>()
    var top : RingElement? = null

    private constructor(values: List<Int>) : this() {
        //
        for( v in values ) {
            val element = getElement(v)
            if( top == null ) {
                top = element
            }
            else {
                top!!.insertBefore(element)
            }
        }
    }

    fun setTopElement(e: RingElement) {
        top = e
    }

    override fun toString() : String {
        val result = StringBuilder()
        var count = 0
        var current = top!!
        do {
            result.append("${current.value} ")
            ++count
            current = current.next
        } while( current != top && count < 11)
        return result.toString()
    }

    public fun getElement(v: Int) : RingElement {
        return elements.computeIfAbsent(v) { RingElement(v) }
    }


    companion object {
        fun parse(s: String) : Ring {
            val ringValues = s.chars().map { it - '0'.toInt() }.toArray().toMutableList()
            return Ring(ringValues)
        }

        fun init2(s: String) : Ring {
            val nums = mutableListOf<Int>()
            nums.addAll(s.chars().map { it - '0'.toInt() }.toArray().toList())
            // Now add the rest
            for( i in 10 .. 1000000 ) {
                nums.add(i)
            }
            println("Created first list")
            return Ring(nums)
        }

    }
}

private data class RingElement(val value: Int) {
    var prev = this
    var next = this

    fun insertAfter(r: RingElement) {
        val ourNext = next
        ourNext.prev = r
        r.next = ourNext
        r.prev = this
        next = r
    }

    fun removeNext() : RingElement {
        val n = next
        next = n.next
        n.next.prev = this
        n.next = n
        n.prev = n
        return n
    }

    fun insertBefore(r: RingElement) {
        val ourPrev = prev
        ourPrev.next = r
        r.prev = ourPrev
        r.next = this
        prev = r
    }
}

private val testInput = "389125467"
private val input = "952438716"