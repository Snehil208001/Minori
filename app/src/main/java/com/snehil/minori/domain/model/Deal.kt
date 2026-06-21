package com.snehil.minori.domain.model

data class Deal(
    val id: String,
    val product: Product,
    val discountPercent: Int,
    val durationMillis: Long
)
