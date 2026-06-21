package com.snehil.minori.data.repository

import com.snehil.minori.domain.model.Category
import com.snehil.minori.domain.model.Product
import com.snehil.minori.domain.model.Banner
import com.snehil.minori.domain.model.Deal
import com.snehil.minori.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override fun getCategories(): Flow<List<Category>> = flow {
        emit(listOf(
            Category(1, "Ceramics"),
            Category(2, "Paintings"),
            Category(3, "Fiber Art"),
            Category(4, "Woodwork"),
            Category(5, "Glassware")
        ))
    }

    override fun getProducts(): Flow<List<Product>> = flow {
        emit(listOf(
            Product("p1", "Earthy Ceramic Vase", 45.0, 4.8f, 1, "Handmade ceramic vase with organic textures.", ""),
            Product("p2", "Abstract Canvas Art", 120.0, 4.9f, 2, "Modern abstract painting on cotton canvas.", ""),
            Product("p3", "Woven Wall Hanging", 75.0, 4.7f, 3, "Handwoven fiber art tapestry for home decor.", "")
        ))
    }

    override fun getBanners(): Flow<List<Banner>> = flow {
        emit(listOf(
            Banner("b1", "Summer Collection", "Handcrafted Pottery", "20% OFF", ""),
            Banner("b2", "Artisan Spotlight", "Canvas & Fiber Art", "15% OFF", "")
        ))
    }

    override fun getDeals(): Flow<List<Deal>> = flow {
        val product = Product("d1", "Glazed Clay Bowl", 28.0, 4.6f, 1, "Traditional glazed pottery bowl.", "")
        emit(listOf(
            Deal("dl1", product, 30, 7200000)
        ))
    }
}
