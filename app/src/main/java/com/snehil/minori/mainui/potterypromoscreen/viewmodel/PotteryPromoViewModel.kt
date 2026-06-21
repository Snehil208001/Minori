package com.snehil.minori.mainui.potterypromoscreen.viewmodel

import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.potterypromoscreen.model.PotteryProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class PotteryPromoState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val products: List<PotteryProduct> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Classes", "Clayware"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class PotteryPromoEffect {
    object NavigateBack : PotteryPromoEffect()
}

@HiltViewModel
class PotteryPromoViewModel @Inject constructor() : BaseViewModel<PotteryPromoState, PotteryPromoEffect>() {

    private val allProducts = listOf(
        PotteryProduct(1, "Pottery Wheel Workshop", "A 2-hour guided session on the spinning potter's wheel. Clay and tools provided.", 1800, 2400, "25% OFF", 5.0f, 142, R.drawable.pottery_spinning, "Classes", "Beginner Friendly"),
        PotteryProduct(2, "Master Pottery Course", "Weekend intensive course covering advanced glazing, trim work, and kiln firing.", 3200, 4000, "20% OFF", 4.9f, 76, R.drawable.pottery_class, "Classes", "Advanced"),
        PotteryProduct(3, "Handthrown Speckled Bowl", "Earthy natural white glazed stoneware bowl for salads or display.", 950, 1200, "20% OFF", 4.8f, 88, R.drawable.ceramic_bowl, "Clayware", "Stoneware"),
        PotteryProduct(4, "Earthy Terracotta Pitcher", "Traditional unglazed clay vessel for fresh water cooling.", 1300, 1800, "27% OFF", 4.7f, 54, R.drawable.clay_pitcher, "Clayware", "Terracotta"),
        PotteryProduct(5, "Speckled Flat Mug", "Flat base ceramic mug with heavy broad handle for cozy tea mornings.", 490, 700, "30% OFF", 4.8f, 210, R.drawable.ceramic_mug, "Clayware", "Stoneware"),
        PotteryProduct(6, "Raised-Lip Clay Plate", "Hand-glazed speckled side dining plate with detailed raised edges.", 640, 800, "20% OFF", 4.6f, 43, R.drawable.clay_plate, "Clayware", "Stoneware")
    )

    override val initialState: PotteryPromoState = PotteryPromoState(products = allProducts)

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
        emitEffect(PotteryPromoEffect.NavigateBack)
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
                it.difficultyLevel.lowercase().contains(query)
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
