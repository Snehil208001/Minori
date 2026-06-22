package com.snehil.minori.mainui.paintingscreen.viewmodel

import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.paintingscreen.model.PaintingProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.snehil.minori.core.wishlist.toWishlistItem
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class PaintingState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val products: List<PaintingProduct> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Oil", "Acrylic", "Watercolor", "Gouache"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class PaintingEffect {
    object NavigateBack : PaintingEffect()
}

@HiltViewModel
class PaintingViewModel @Inject constructor(
    private val wishlistManager: com.snehil.minori.core.wishlist.WishlistManager
) : BaseViewModel<PaintingState, PaintingEffect>() {

    private val allProducts = listOf(
        PaintingProduct(1, "Coastal Mist Oil", "Impressionist coastal scene painted with thick palette knife strokes on stretched canvas.", 14500, 18000, "19% OFF", 4.9f, 21, R.drawable.artisan_weaving, "Oil", "24x36 in", true),
        PaintingProduct(2, "Crimson Sunrise", "Vibrant wet-on-wet watercolor study capturing dawn over misty hills.", 3500, 4500, "22% OFF", 4.7f, 15, R.drawable.tapestry_wall, "Watercolor", "12x16 in", false),
        PaintingProduct(3, "Abstract Calm", "Minimalist abstract canvas featuring gentle earth tones and textured plaster lines.", 9500, 12000, "20% OFF", 4.8f, 34, R.drawable.glass_carafe, "Acrylic", "30x30 in", true),
        PaintingProduct(4, "Forest Path Gouache", "Intimate botanical Gouache painting on heavy cotton paper, detailing forest light.", 2200, 3000, "26% OFF", 4.6f, 12, R.drawable.boho_candle, "Gouache", "10x14 in", false)
    )

    override val initialState: PaintingState = PaintingState(products = allProducts)

    init {
        viewModelScope.launch {
            wishlistManager.wishlistFlow.collect { items ->
                val wishlisted = items.filter { it.type == "Painting" }.map { it.id.toInt() }.toSet()
                updateState { it.copy(wishlistedIds = wishlisted) }
            }
        }
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
        val product = allProducts.find { it.id == productId } ?: return
        wishlistManager.toggleWishlist(product.toWishlistItem())
    }

    fun goBack() {
        emitEffect(PaintingEffect.NavigateBack)
    }

    private fun filterAndSortProducts() {
        val query = currentState.searchQuery.lowercase().trim()
        val category = currentState.selectedCategory
        val sort = currentState.selectedSort

        var result = allProducts

        if (category != "All") {
            result = result.filter { it.medium == category }
        }

        if (query.isNotEmpty()) {
            result = result.filter {
                it.title.lowercase().contains(query) ||
                it.description.lowercase().contains(query) ||
                it.medium.lowercase().contains(query)
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
