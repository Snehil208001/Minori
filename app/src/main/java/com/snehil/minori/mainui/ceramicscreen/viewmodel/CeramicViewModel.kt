package com.snehil.minori.mainui.ceramicscreen.viewmodel

import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.ceramicscreen.model.CeramicProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.snehil.minori.core.wishlist.toWishlistItem
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class CeramicState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val products: List<CeramicProduct> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Stoneware", "Porcelain", "Raku", "Earthenware"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class CeramicEffect {
    object NavigateBack : CeramicEffect()
}

@HiltViewModel
class CeramicViewModel @Inject constructor(
    private val wishlistManager: com.snehil.minori.core.wishlist.WishlistManager
) : BaseViewModel<CeramicState, CeramicEffect>() {

    private val allProducts = listOf(
        CeramicProduct(1, "Speckled Stoneware Vase", "Elegant tall vase hand-thrown on the wheel, coated in oatmeal glaze.", 2800, 3500, "20% OFF", 4.8f, 74, R.drawable.clay_pitcher, "Stoneware", "1220°C"),
        CeramicProduct(2, "Porcelain Sake Set", "Translucent white porcelain flask with four small cups, fine celadon wash.", 4200, 5000, "16% OFF", 4.9f, 38, R.drawable.glass_carafe, "Porcelain", "1300°C"),
        CeramicProduct(3, "Matte Terra Serving Bowl", "Rustic wide serving bowl, raw terracotta exterior with glazed center.", 1900, 2500, "24% OFF", 4.7f, 112, R.drawable.ceramic_bowl, "Earthenware", "1080°C"),
        CeramicProduct(4, "Raku Tea Cup", "Traditional crackle-glazed tea bowl, fired in a gas-fueled kiln.", 1500, 2000, "25% OFF", 5.0f, 49, R.drawable.ceramic_mug, "Raku", "950°C")
    )

    override val initialState: CeramicState = CeramicState(products = allProducts)

    init {
        viewModelScope.launch {
            wishlistManager.wishlistFlow.collect { items ->
                val wishlisted = items.filter { it.type == "Ceramic" }.map { it.id.toInt() }.toSet()
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
        emitEffect(CeramicEffect.NavigateBack)
    }

    private fun filterAndSortProducts() {
        val query = currentState.searchQuery.lowercase().trim()
        val category = currentState.selectedCategory
        val sort = currentState.selectedSort

        var result = allProducts

        if (category != "All") {
            result = result.filter { it.clayType == category }
        }

        if (query.isNotEmpty()) {
            result = result.filter {
                it.title.lowercase().contains(query) ||
                it.description.lowercase().contains(query) ||
                it.clayType.lowercase().contains(query)
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
