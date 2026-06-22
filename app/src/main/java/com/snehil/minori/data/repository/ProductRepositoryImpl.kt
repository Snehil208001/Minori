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
            Product("p1", "Earthy Ceramic Bowl", 1850.0, 4.7f, 1, "Hand-thrown clay bowl with natural white glaze.", "ceramic_bowl"),
            Product("p2", "Terracotta Clay Pitcher", 2400.0, 4.9f, 1, "Traditional rustic pitcher for fresh water or decor.", "clay_pitcher"),
            Product("p3", "Glass Vase / Carafe", 2650.0, 4.5f, 5, "Blown decanter in warm amber gradient shade.", "glass_carafe"),
            Product("p4", "Oak Carved Chest", 6850.0, 4.8f, 4, "Handcrafted organic frame drawer with brass keys.", "oak_chest"),
            Product("p5", "Rattan Lounge Chair", 8500.0, 4.6f, 4, "Minimalist modern handwoven rattan armchair.", "rattan_chair"),
            Product("p6", "Woven Tribal Rug", 4900.0, 4.7f, 3, "Bohemian woven wool rug with geo-patterns.", "wool_rug"),
            Product("p7", "Speckled Clay Mug", 850.0, 4.8f, 1, "Cozy speckled mug with flat base and large handle.", "ceramic_mug"),
            Product("p8", "Scented Soy Candle", 950.0, 4.9f, 2, "Soy wax candle in a reusable clay pot container.", "boho_candle"),
            Product("p9", "Woven Wall Tapestry", 3200.0, 4.4f, 3, "Handcrafted abstract cotton/wool wall hanging.", "tapestry_wall"),
            Product("p10", "Fringe Seagrass Basket", 1600.0, 4.8f, 4, "Large woven laundry or blanket hamper with detailed braids.", "woven_basket")
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
