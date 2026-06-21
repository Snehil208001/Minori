package com.snehil.minori.domain.repository

import com.snehil.minori.domain.model.Category
import com.snehil.minori.domain.model.Product
import com.snehil.minori.domain.model.Banner
import com.snehil.minori.domain.model.Deal
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getCategories(): Flow<List<Category>>
    fun getProducts(): Flow<List<Product>>
    fun getBanners(): Flow<List<Banner>>
    fun getDeals(): Flow<List<Deal>>
}
