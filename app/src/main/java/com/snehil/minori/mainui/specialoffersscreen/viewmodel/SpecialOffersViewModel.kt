package com.snehil.minori.mainui.specialoffersscreen.viewmodel

import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.specialoffersscreen.model.OfferProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.snehil.minori.core.wishlist.toWishlistItem
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class SpecialOffersState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val products: List<OfferProduct> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val categories: List<String> = listOf("All", "Combo Sets", "Curated Sets", "Starter Kits", "Home Linen"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class SpecialOffersEffect {
    object NavigateBack : SpecialOffersEffect()
}

@HiltViewModel
class SpecialOffersViewModel @Inject constructor(
    private val wishlistManager: com.snehil.minori.core.wishlist.WishlistManager
) : BaseViewModel<SpecialOffersState, SpecialOffersEffect>() {

    private val allProducts = listOf(
        OfferProduct(
            id = 1,
            title = "Ceramic Dining Set",
            description = "Earthy Glazed Clay Plate + Speckled Clay Mug. The perfect rustic duo.",
            price = 799,
            originalPrice = 1500,
            discount = "BOGO DEAL",
            rating = 4.8f,
            reviewCount = 140,
            drawableId = R.drawable.clay_plate,
            category = "Combo Sets",
            offerTag = "Save 47%",
            bundleItems = listOf("1x Earthy Glazed Clay Plate", "1x Speckled Clay Mug")
        ),
        OfferProduct(
            id = 2,
            title = "Boho Living Room Bundle",
            description = "Macrame Wall Tapestry + Fringe Linen Cushion Cover.",
            price = 3800,
            originalPrice = 6000,
            discount = "BUNDLE DEALS",
            rating = 4.7f,
            reviewCount = 68,
            drawableId = R.drawable.tapestry_wall,
            category = "Combo Sets",
            offerTag = "Save 36%",
            bundleItems = listOf("1x Woven Wall Tapestry", "1x Fringe Linen Cushion Cover")
        ),
        OfferProduct(
            id = 3,
            title = "Fragrant Serenity Bundle",
            description = "Two Soy Wax Clay Candles with hand-painted patterns.",
            price = 1300,
            originalPrice = 2000,
            discount = "COMBO DISCOUNT",
            rating = 4.9f,
            reviewCount = 112,
            drawableId = R.drawable.boho_candle,
            category = "Curated Sets",
            offerTag = "Save 35%",
            bundleItems = listOf("2x Soy Wax Clay Candles")
        ),
        OfferProduct(
            id = 4,
            title = "Clayware Starter Kit",
            description = "Earthy Ceramic Bowl + Speckled Mug + Rustic Pitcher.",
            price = 2600,
            originalPrice = 4200,
            discount = "STARTER KIT",
            rating = 4.8f,
            reviewCount = 94,
            drawableId = R.drawable.ceramic_bowl,
            category = "Starter Kits",
            offerTag = "Save 38%",
            bundleItems = listOf("1x Earthy Ceramic Bowl", "1x Speckled Clay Mug", "1x Terracotta Clay Pitcher")
        ),
        OfferProduct(
            id = 5,
            title = "Artisan Basket Trio",
            description = "Three nesting braided seagrass storage baskets.",
            price = 1800,
            originalPrice = 3000,
            discount = "TRIPLE DEAL",
            rating = 4.6f,
            reviewCount = 45,
            drawableId = R.drawable.woven_basket,
            category = "Curated Sets",
            offerTag = "Save 40%",
            bundleItems = listOf("3x Graduated Seagrass Baskets")
        ),
        OfferProduct(
            id = 6,
            title = "Lounge Cushion Pack",
            description = "Two organic textured linen pillow covers.",
            price = 1500,
            originalPrice = 2200,
            discount = "COMBO DISCOUNT",
            rating = 4.7f,
            reviewCount = 56,
            drawableId = R.drawable.linen_pillow,
            category = "Home Linen",
            offerTag = "Save 31%",
            bundleItems = listOf("2x Organic Linen Cushion Covers")
        )
    )

    override val initialState: SpecialOffersState = SpecialOffersState(products = allProducts)

    init {
        viewModelScope.launch {
            wishlistManager.wishlistFlow.collect { items ->
                val wishlisted = items.filter { it.type == "OfferProduct" }.map { it.id.toInt() }.toSet()
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
        emitEffect(SpecialOffersEffect.NavigateBack)
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
                it.bundleItems.any { item -> item.lowercase().contains(query) }
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
