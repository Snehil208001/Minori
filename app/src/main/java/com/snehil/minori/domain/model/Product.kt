package com.snehil.minori.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val rating: Float,
    val categoryId: Int,
    val description: String,
    val imageUrl: String
)
