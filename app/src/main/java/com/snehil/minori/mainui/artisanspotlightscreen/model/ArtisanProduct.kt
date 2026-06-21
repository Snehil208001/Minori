package com.snehil.minori.mainui.artisanspotlightscreen.model

data class ArtisanProduct(
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
    val artisanName: String
)
