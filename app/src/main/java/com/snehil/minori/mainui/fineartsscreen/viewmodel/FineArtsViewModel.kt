package com.snehil.minori.mainui.fineartsscreen.viewmodel

import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.fineartsscreen.model.FineArtsProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.snehil.minori.core.wishlist.toWishlistItem
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class FineArtsState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val products: List<FineArtsProduct> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Sculpture", "Printmaking", "Metal Art"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class FineArtsEffect {
    object NavigateBack : FineArtsEffect()
}

@HiltViewModel
class FineArtsViewModel @Inject constructor(
    private val wishlistManager: com.snehil.minori.core.wishlist.WishlistManager
) : BaseViewModel<FineArtsState, FineArtsEffect>() {

    private val allProducts = listOf(
        FineArtsProduct(1, "Bronze Abstract Form", "Heavy, lost-wax cast bronze sculpture on a black marble base.", 28000, 32000, "12% OFF", 4.9f, 9, R.drawable.pottery_class, "Sculpture", "Cast Bronze", "1 of 10", true),
        FineArtsProduct(2, "Monotype Linocut", "Original graphic linoleum block print using soy-based black inks on handmade mulberry paper.", 4800, 6000, "20% OFF", 4.8f, 14, R.drawable.tapestry_wall, "Printmaking", "Mulberry Paper", "4 of 25", true),
        FineArtsProduct(3, "Hand-Forged Brass Bowl", "Sculptural vessel hammered by hand, displaying rich fire scale patina.", 8900, 11000, "19% OFF", 4.7f, 23, R.drawable.ceramic_bowl, "Metal Art", "Hammered Brass", "Unique 1/1", true),
        FineArtsProduct(4, "Carved Alabaster Vessel", "Translucent carved stone vessel with organic natural veins.", 16000, 20000, "20% OFF", 5.0f, 6, R.drawable.glass_carafe, "Sculpture", "White Alabaster", "Unique 1/1", true)
    )

    override val initialState: FineArtsState = FineArtsState(products = allProducts)

    init {
        viewModelScope.launch {
            wishlistManager.wishlistFlow.collect { items ->
                val wishlisted = items.filter { it.type == "FineArts" }.map { it.id.toInt() }.toSet()
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
        emitEffect(FineArtsEffect.NavigateBack)
    }

    private fun filterAndSortProducts() {
        val query = currentState.searchQuery.lowercase().trim()
        val category = currentState.selectedCategory
        val sort = currentState.selectedSort

        var result = allProducts

        if (category != "All") {
            result = result.filter { it.artType == category }
        }

        if (query.isNotEmpty()) {
            result = result.filter {
                it.title.lowercase().contains(query) ||
                it.description.lowercase().contains(query) ||
                it.material.lowercase().contains(query)
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
