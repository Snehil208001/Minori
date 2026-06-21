package com.snehil.minori.mainui.newinstorescreen.viewmodel

import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.newinstorescreen.model.NewProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class NewInStoreState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Newly Added",
    val products: List<NewProduct> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Mirrors", "Furniture", "Accessories", "Decor", "Clayware"),
    val sortOptions: List<String> = listOf("Newly Added", "Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class NewInStoreEffect {
    object NavigateBack : NewInStoreEffect()
}

@HiltViewModel
class NewInStoreViewModel @Inject constructor() : BaseViewModel<NewInStoreState, NewInStoreEffect>() {

    private val allProducts = listOf(
        NewProduct(1, "Hand-Carved Mirror", "Ornate round wall mirror with detailed floral woodwork carvings.", 4200, 6000, "30% OFF", 4.9f, 15, R.drawable.carved_mirror, "Mirrors", 2),
        NewProduct(2, "Rustic Oak Chest", "Solid oak storage drawer with brass pull rings and key.", 7800, 11000, "29% OFF", 4.8f, 8, R.drawable.oak_chest, "Furniture", 1),
        NewProduct(3, "Rattan Armchair", "Handwoven natural rattan lounge chair with soft cushions.", 9200, 13000, "29% OFF", 4.7f, 22, R.drawable.rattan_chair, "Furniture", 5),
        NewProduct(4, "Woven Cotton Basket", "Minimal storage basket with sturdy rope carrying handles.", 1100, 1500, "26% OFF", 4.6f, 14, R.drawable.woven_basket, "Accessories", 4),
        NewProduct(5, "Soy Wax Clay Candle", "Scented organic candle in a reusable handthrown clay container.", 850, 1200, "29% OFF", 4.9f, 31, R.drawable.boho_candle, "Decor", 3),
        NewProduct(6, "Sage Pottery Carafe", "Rustic water pitcher with textured Sage color wash details.", 1800, 2400, "25% OFF", 4.8f, 10, R.drawable.clay_pitcher, "Clayware", 2)
    )

    override val initialState: NewInStoreState = NewInStoreState(products = allProducts)

    init {
        filterAndSortProducts()
    }

    fun updateSearchQuery(query: String) {
        updateState { it.copy(searchQuery = query) }
        filterAndSortProducts()
    }

    fun updateCategory(category: String) {
        updateState { it.copy(selectedCategory = category) }
        filterAndSortProducts()
    }

    fun updateSort(sort: String) {
        updateState { it.copy(selectedSort = sort) }
        filterAndSortProducts()
    }

    fun toggleWishlist(productId: Int) {
        updateState { state ->
            val updated = state.wishlistedIds.toMutableSet()
            if (updated.contains(productId)) {
                updated.remove(productId)
            } else {
                updated.add(productId)
            }
            state.copy(wishlistedIds = updated)
        }
    }

    fun goBack() {
        emitEffect(NewInStoreEffect.NavigateBack)
    }

    private fun filterAndSortProducts() {
        val query = currentState.searchQuery.lowercase().trim()
        val category = currentState.selectedCategory
        val sort = currentState.selectedSort

        var result = allProducts

        if (category != "All") {
            result = result.filter { it.category == category }
        }

        if (query.isNotEmpty()) {
            result = result.filter {
                it.title.lowercase().contains(query) ||
                it.description.lowercase().contains(query)
            }
        }

        result = when (sort) {
            "Price: Low to High" -> result.sortedBy { it.price }
            "Price: High to Low" -> result.sortedByDescending { it.price }
            "Popularity" -> result.sortedByDescending { it.rating }
            "Newly Added" -> result.sortedBy { it.daysAgoAdded }
            else -> result
        }

        updateState { it.copy(products = result) }
    }
}
