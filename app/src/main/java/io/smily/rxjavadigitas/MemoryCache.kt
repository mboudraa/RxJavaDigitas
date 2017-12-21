package io.smily.rxjavadigitas

import org.threeten.bp.ZonedDateTime

class MemoryCache {

    data class CachedItem(val item: Any,
                          val validUntil: Long)

    private val map = hashMapOf<String, CachedItem>()

    fun <T> getItem(key: String): T? = map[key]?.item as? T

    fun isItemValid(key: String): Boolean = map[key]?.let { it.validUntil <= ZonedDateTime.now().toInstant().toEpochMilli() } ?: false

    fun deleteItem(key: String) = map.remove(key)

    fun add(text: String, item: Any, validUntil: Long) {
        map[text] = CachedItem(item, validUntil)
    }
}