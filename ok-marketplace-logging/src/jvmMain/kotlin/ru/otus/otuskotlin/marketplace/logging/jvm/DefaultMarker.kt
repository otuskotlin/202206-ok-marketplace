package ru.otus.otuskotlin.marketplace.logging.jvm

import org.slf4j.Marker

/**
 * Реализация SLF4J маркера логов для маркировки различных типов логов
 */
class DefaultMarker(
        private val name: String,
        private val submarkers: List<Marker> = emptyList()
): Marker {
    override fun getName(): String = name

    override fun add(reference: Marker) {}

    override fun remove(reference: Marker): Boolean = false

    @Deprecated("Deprecated in Java")
    override fun hasChildren(): Boolean = hasReferences()

    override fun hasReferences(): Boolean = submarkers.isNotEmpty()

    override fun iterator(): Iterator<Marker> = submarkers.iterator()

    override fun contains(other: Marker): Boolean = submarkers.contains(other)

    override fun contains(name: String): Boolean = submarkers.any { it.name == name }

    override fun toString(): String = arrayOf(name, *submarkers.toTypedArray()).joinToString(",")

}
