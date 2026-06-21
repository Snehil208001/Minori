package com.snehil.minori.mainui.newarrivalsscreen.viewmodel

import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.newarrivalsscreen.model.ArrivalProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class NewArrivalsState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val products: List<ArrivalProduct> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Bags", "Home"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class NewArrivalsEffect {
    object NavigateBack : NewArrivalsEffect()
}

@HiltViewModel
class NewArrivalsViewModel @Inject constructor() : BaseViewModel<NewArrivalsState, NewArrivalsEffect>() {

    private val allProducts = listOf(
        ArrivalProduct(1, "Organic Linen Tote", "Spacious summer tote bag with braided handles and hand-frayed fringe detailing.", 1450, 2000, "27% OFF", 4.8f, 34, R.drawable.linen_tote, "Bags", "100% Organic Linen"),
        ArrivalProduct(2, "Rustic Linen Pillow", "Heavy-texture cream linen throw pillow cover with organic flax fibers.", 1200, 1600, "25% OFF", 4.6f, 27, R.drawable.linen_pillow, "Home", "100% Organic Linen"),
        ArrivalProduct(3, "Hand-Braided Jute Rug", "Earthy natural round braided jute fiber rug for entryways.", 3200, 4000, "20% OFF", 4.7f, 18, R.drawable.wool_rug, "Home", "Natural Jute Fiber"),
        ArrivalProduct(4, "Seagrass Market Basket", "Handwoven sturdy market carrier bag with brown leather handles.", 1800, 2400, "25% OFF", 4.8f, 52, R.drawable.woven_basket, "Bags", "Seagrass & Leather"),
        ArrivalProduct(5, "Fringe Cotton Hanger", "Bohemian hand-knotted cotton rope hanger for plant pots.", 650, 900, "27% OFF", 4.5f, 67, R.drawable.macrame_hanger, "Home", "Organic Cotton Rope"),
        ArrivalProduct(6, "Abstract Canvas Hanging", "Minimalist abstract screen-printed linen wall banner tapestry.", 2200, 3000, "26% OFF", 4.7f, 14, R.drawable.tapestry_wall, "Home", "Handwoven Cotton Linen")
    )

    override val initialState: NewArrivalsState = NewArrivalsState(products = allProducts)

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
        emitEffect(NewArrivalsEffect.NavigateBack)
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
                it.description.lowercase().contains(query) ||
                it.materialUsed.lowercase().contains(query)
            }
        }

        result = when (sort) {
            "Price: Low to High" -> result.sortedBy { it.price }
            "Price: High to Low" -> result.sortedByDescending { it.price }
            "Popularity" -> result.sortedByDescending { it.rating }
            else -> result
        }

        updateState { it.copy(products = result) }
    }
}
