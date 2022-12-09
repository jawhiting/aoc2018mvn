package com.drinkscabinet

import Delta

class Grid<V>(private val default: V): MutableMap<Coord, V> {
    private val map = mutableMapOf<Coord, V>().withDefault { default }

    override fun equals(other: Any?): Boolean = map.equals(other)
    override fun hashCode(): Int = map.hashCode()
    override fun toString(): String = map.toString()
    override val size: Int get() = map.size
    override fun isEmpty(): Boolean = map.isEmpty()
    override fun containsKey(key: Coord): Boolean = map.containsKey(key)
    override fun containsValue(value: @UnsafeVariance V): Boolean = map.containsValue(value)
    override fun get(key: Coord): V? = map[key]
    override val keys: MutableSet<Coord> get() = map.keys
    override val values: MutableCollection<V> get() = map.values
    override val entries: MutableSet<MutableMap.MutableEntry<Coord, V>> get() = map.entries

    override fun put(key: Coord, value: V): V? = map.put(key, value)
    override fun remove(key: Coord): V? = map.remove(key)
    override fun putAll(from: Map<out Coord, V>) = map.putAll(from)
    override fun clear() = map.clear()

    private fun getOrDefault(key: Coord): V {
        return map[key] ?: default
    }

    fun neighbours(pos: Coord, directions: Iterable<Delta>) : Iterable<Pair<Coord, V>> {
        return directions.map{ pos.move(it) }.map{ it to  getOrDefault(it)}
    }

    fun neighboursMatch(pos: Coord, directions: Iterable<Delta>, filter: (V) -> Boolean) : Int {
        return directions.map { pos.move(it) }.count { filter(getOrDefault(it)) }
    }

    fun neighbours4(pos: Coord) : Iterable<Pair<Coord, V>> {
        return neighbours(pos, Direction.values().asIterable())
    }

}