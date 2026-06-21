package com.snehil.minori.mainui.specialoffersscreen.model

data class OfferProduct(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val originalPrice: Int,
    val discount: String,
    val rating: Float,
    val reviewCount: Int,
    val drawableId: Int,
    val category: String,
    val offerTag: String,
    val bundleItems: List<String>
)
