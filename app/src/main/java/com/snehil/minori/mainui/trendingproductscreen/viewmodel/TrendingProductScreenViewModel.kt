package com.snehil.minori.mainui.trendingproductscreen.viewmodel

import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.trendingproductscreen.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.snehil.minori.core.wishlist.toWishlistItem
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class TrendingProductState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val products: List<Product> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Clayware", "Furniture", "Decor", "Textiles"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class TrendingProductEffect {
    object NavigateBack : TrendingProductEffect()
}

@HiltViewModel
class TrendingProductScreenViewModel @Inject constructor(
    private val wishlistManager: com.snehil.minori.core.wishlist.WishlistManager
) : BaseViewModel<TrendingProductState, TrendingProductEffect>() {

    private val allProducts = listOf(
        Product(1, "Earthy Ceramic Bowl", "Hand-thrown clay bowl with natural white glaze.", 1850, 2500, "26% OFF", 4.7f, 154, R.drawable.ceramic_bowl, "Clayware"),
        Product(2, "Terracotta Clay Pitcher", "Traditional rustic pitcher for fresh water or decor.", 2400, 3200, "25% OFF", 4.9f, 89, R.drawable.clay_pitcher, "Clayware"),
        Product(3, "Glass Vase / Carafe", "Blown decanter in warm amber gradient shade.", 2650, 5300, "50% OFF", 4.5f, 43, R.drawable.glass_carafe, "Decor"),
        Product(4, "Oak Carved Chest", "Handcrafted organic frame drawer with brass keys.", 6850, 9500, "28% OFF", 4.8f, 21, R.drawable.oak_chest, "Furniture"),
        Product(5, "Rattan Lounge Chair", "Minimalist modern handwoven rattan armchair.", 8500, 12000, "29% OFF", 4.6f, 67, R.drawable.rattan_chair, "Furniture"),
        Product(6, "Woven Tribal Rug", "Bohemian woven wool rug with geo-patterns.", 4900, 7000, "30% OFF", 4.7f, 112, R.drawable.wool_rug, "Textiles"),
        Product(7, "Speckled Clay Mug", "Cozy speckled mug with flat base and large handle.", 850, 1200, "29% OFF", 4.8f, 320, R.drawable.ceramic_mug, "Clayware"),
        Product(8, "Scented Soy Candle", "Soy wax candle in a reusable clay pot container.", 950, 1500, "36% OFF", 4.9f, 198, R.drawable.boho_candle, "Decor"),
        Product(9, "Woven Wall Tapestry", "Handcrafted abstract cotton/wool wall hanging.", 3200, 4800, "33% OFF", 4.4f, 56, R.drawable.tapestry_wall, "Textiles"),
        Product(10, "Pottery Workshop Class", "Hands-on pottery workshop with a master artisan.", 1500, 3000, "50% OFF", 5.0f, 420, R.drawable.pottery_class, "Decor")
    )

    override val initialState: TrendingProductState = TrendingProductState(products = allProducts)

    init {
        viewModelScope.launch {
            wishlistManager.wishlistFlow.collect { items ->
                val wishlisted = items.filter { it.type == "TrendingProduct" }.map { it.id.toInt() }.toSet()
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

    private fun filterAndSortProducts() {
        val query = currentState.searchQuery.lowercase().trim()
        val category = currentState.selectedCategory
        val sort = currentState.selectedSort

        var result = allProducts

        // Category Filter
        if (category != "All") {
            result = result.filter { it.category == category }
        }

        // Search Filter
        if (query.isNotEmpty()) {
            result = result.filter {
                it.title.lowercase().contains(query) ||
                it.description.lowercase().contains(query)
            }
        }

        // Sorting
        result = when (sort) {
            "Price: Low to High" -> result.sortedBy { it.price }
            "Price: High to Low" -> result.sortedByDescending { it.price }
            "Popularity" -> result.sortedByDescending { it.rating }
            else -> result
        }

        updateState { it.copy(products = result) }
    }

    fun goBack() {
        emitEffect(TrendingProductEffect.NavigateBack)
    }
}
