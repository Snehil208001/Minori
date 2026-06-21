package com.snehil.minori.mainui.potterypromoscreen.model

data class PotteryProduct(
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
    val difficultyLevel: String // e.g. "Beginner Friendly", "Intermediate", "Master" for classes
)
