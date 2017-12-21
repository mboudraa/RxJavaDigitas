package io.smily.rxjavadigitas

import org.threeten.bp.ZonedDateTime

data class SearchResponse(val products: List<Product>, val validity: ZonedDateTime)

data class Product(val name: String,
                   val price: Int,
                   val expirationDate: ZonedDateTime)
