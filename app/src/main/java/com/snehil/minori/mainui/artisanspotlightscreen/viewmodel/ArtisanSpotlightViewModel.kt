package com.snehil.minori.mainui.artisanspotlightscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.artisanspotlightscreen.model.ArtisanProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArtisanSpotlightState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val products: List<ArtisanProduct> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Weaving", "Tapestry", "Baskets", "Cushions"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class ArtisanSpotlightEffect {
    object NavigateBack : ArtisanSpotlightEffect()
}

@HiltViewModel
class ArtisanSpotlightViewModel @Inject constructor() : BaseViewModel<ArtisanSpotlightState, ArtisanSpotlightEffect>() {

    private val allProducts = listOf(
        ArtisanProduct(1, "Heritage Loom Rug", "Hand-knotted wool area rug featuring intricate geometrical motifs.", 5200, 6500, "20% OFF", 4.9f, 42, R.drawable.artisan_weaving, "Weaving", "Ananya Sen"),
        ArtisanProduct(2, "Woven Cotton Tapestry", "Delicate textured macrame wall art with driftwood mount.", 2400, 3000, "20% OFF", 4.7f, 29, R.drawable.tapestry_wall, "Tapestry", "Kabir Dev"),
        ArtisanProduct(3, "Earthy Plant Hanger", "Triple tiered bohemian heavy rope cotton plant holder.", 800, 1000, "20% OFF", 4.8f, 88, R.drawable.macrame_hanger, "Tapestry", "Meera Nair"),
        ArtisanProduct(4, "Bohemian Wool Runner", "Narrow hallway runner flatweave with neutral tone tassels.", 3600, 4500, "20% OFF", 4.6f, 19, R.drawable.wool_rug, "Weaving", "Ananya Sen"),
        ArtisanProduct(5, "Fringe Seagrass Basket", "Large woven laundry or blanket hamper with detailed braids.", 1600, 2000, "20% OFF", 4.8f, 56, R.drawable.woven_basket, "Baskets", "Raju Prasad"),
        ArtisanProduct(6, "Tufted Linen Cushion", "Soft organic flax linen pillow with cotton tufted designs.", 1200, 1500, "20% OFF", 4.7f, 34, R.drawable.linen_pillow, "Cushions", "Kabir Dev")
    )

    override val initialState: ArtisanSpotlightState = ArtisanSpotlightState(products = allProducts)

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
        emitEffect(ArtisanSpotlightEffect.NavigateBack)
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
                it.artisanName.lowercase().contains(query)
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
