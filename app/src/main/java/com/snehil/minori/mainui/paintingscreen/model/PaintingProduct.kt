package com.snehil.minori.mainui.paintingscreen.model

data class PaintingProduct(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val originalPrice: Int,
    val discount: String,
    val rating: Float,
    val reviewCount: Int,
    val drawableId: Int,
    val medium: String,
    val dimensions: String,
    val isFramed: Boolean
)
