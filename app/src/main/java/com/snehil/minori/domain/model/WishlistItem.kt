package com.snehil.minori.domain.model

data class WishlistItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val discount: String? = null,
    val rating: Float,
    val imageUrl: String? = null,
    val drawableId: Int? = null,
    val categoryLabel: String? = null,
    val extraTag: String? = null,
    val type: String // "Product", "Ceramic", "Painting", "FineArts", etc.
)
