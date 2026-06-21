package com.snehil.minori.mainui.newarrivalsscreen.model

data class ArrivalProduct(
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
    val materialUsed: String // e.g. "100% Organic Linen", "Handspun Khadi"
)
