package com.snehil.minori.domain.usecase

import com.snehil.minori.domain.model.Category
import com.snehil.minori.domain.model.Product
import com.snehil.minori.domain.model.Banner
import com.snehil.minori.domain.model.Deal
import com.snehil.minori.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class HomeData(
    val categories: List<Category>,
    val products: List<Product>,
    val banners: List<Banner>,
    val deals: List<Deal>
)

class GetHomeDataUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<HomeData> {
        return combine(
            productRepository.getCategories(),
            productRepository.getProducts(),
            productRepository.getBanners(),
            productRepository.getDeals()
        ) { categories, products, banners, deals ->
            HomeData(categories, products, banners, deals)
        }
    }
}
