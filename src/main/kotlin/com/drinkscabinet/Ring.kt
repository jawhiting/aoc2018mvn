package com.drinkscabinet

import kotlin.math.abs

class Ring<T> {

    var size = 0L
    var root: Entry<T>? = null


    fun addAfter(value: T, index: Int = 0): Entry<T> {
        if (root == null) {
            return addFirstElement(value)
        } else {
            // get entry to add after
            val e = get(index)
            ++size
            return Entry(value, e, e.next)
        }
    }

    fun addBefore(value: T, index: Int = 0): Entry<T> {
        if (root == null) {
            return addFirstElement(value)
        } else {
            val e = get(index)
            ++size
            return Entry(value, e.prev, e)
        }
    }

    operator fun get(index: Int): Entry<T> {
        if (root == null) {
            throw IndexOutOfBoundsException("Ring is empty")
        }
        val target = index % size
        var cur = root!!
        (1..target).forEach { i ->
            cur = cur.next
        }
        return cur
    }

    fun removeAt(index: Int): Entry<T> {
        if (root == null) {
            throw IndexOutOfBoundsException("Ring is empty")
        }
        val toRemove = get(index)
        toRemove.prev.next = toRemove.next
        toRemove.next.prev = toRemove.prev
        toRemove.next = toRemove
        toRemove.prev = toRemove
        --size
        return toRemove
    }

    fun rotateForward() {
        if (root != null) {
            root = root!!.next
        }
    }

    fun rotateBackward() {
        if (root != null) {
            root = root!!.prev
        }
    }

    fun elements(): Sequence<Entry<T>> = sequence {
        if (root != null) {
            yield(root!!)
            var cur = root!!.next
            while (cur != root) {
                yield(cur)
                cur = cur.next
            }
        }
    }


    private fun addFirstElement(value: T): Entry<T> {
        assert(root == null)
        root = Entry(value)
        ++size
        return root!!
    }

    override fun toString(): String {
        return "Ring(size=$size, elements=[${elements().map { it.toString() }.joinToString(",")}])"
    }

    inner class Entry<T>(var value: T) {
        var prev: Entry<T> = this
        var next: Entry<T> = this

        constructor(value: T, prev: Entry<T>, next: Entry<T>) : this(value) {
            this.prev = prev
            prev.next = this
            this.next = next
            next.prev = this
        }

        override fun toString(): String {
            return "$value"
        }

        fun move(steps: Int) {
            return move(steps.toLong())
        }

        fun move(steps: Long) {
            if (steps == 0L || (steps % (size - 1)) == 0L) {
                // do noting
                return
            }
            if (this == root) {
                // Reposition root if we're moving the element
                rotateForward()
            }
            if (steps > 0) {
                moveForward(steps)
            } else {
                moveBackward(abs(steps))
            }
        }

        fun moveForward(steps: Long) {
            // take ourself out of the ring, unless its a zero move
            prev.next = next
            next.prev = prev
            // Now move forward X steps
            var target = this
            for (i in 1..(steps % (size - 1))) {
                target = target.next
            }
            // Now add after this item
            this.next = target.next
            this.prev = target
            this.next.prev = this
            this.prev.next = this
        }

        fun moveBackward(steps: Long) {
            // take ourself out of the ring
            prev.next = next
            next.prev = prev
            // Now move backward X steps
            var target = this
            for (i in 1..(steps % (size - 1))) {
                target = target.prev
            }
            // Now add before this item
            this.prev = target.prev
            this.next = target
            this.next.prev = this
            this.prev.next = this
        }
    }
}
