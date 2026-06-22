package com.snehil.minori.mainui.homescreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.domain.model.Category
import com.snehil.minori.domain.model.Product
import com.snehil.minori.domain.model.Banner
import com.snehil.minori.domain.model.Deal
import com.snehil.minori.domain.usecase.GetHomeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.snehil.minori.core.wishlist.toWishlistItem

data class HomeState(
    val categories: List<Category> = emptyList(),
    val allProducts: List<Product> = emptyList(),
    val products: List<Product> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val deals: List<Deal> = emptyList(),
    val selectedCategoryId: Int = 0,
    val searchQuery: String = "",
    val selectedSort: String = "Popularity",
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low"),
    val wishlistedIds: Set<String> = emptySet(),
    val cartCount: Int = 0,
    val cartTotal: Double = 0.0,
    val cartItemsText: String = ""
)

sealed interface HomeEffect {
    data class NavigateToProductDetails(val productId: String) : HomeEffect
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val wishlistManager: com.snehil.minori.core.wishlist.WishlistManager,
    private val cartManager: com.snehil.minori.core.cart.CartManager
) : BaseViewModel<HomeState, HomeEffect>() {

    override val initialState: HomeState = HomeState()

    init {
        loadHomeData()
        viewModelScope.launch {
            wishlistManager.wishlistFlow.collect { items ->
                val wishlisted = items.filter { it.type == "Product" }.map { it.id }.toSet()
                updateState { it.copy(wishlistedIds = wishlisted) }
            }
        }
        viewModelScope.launch {
            cartManager.cartItems.collect { items ->
                val count = cartManager.getCartCount()
                val total = cartManager.getCartTotal()
                val itemsText = items.joinToString(", ") { "${it.quantity}x ${it.product.name}" }
                updateState {
                    it.copy(
                        cartCount = count,
                        cartTotal = total,
                        cartItemsText = itemsText
                    )
                }
            }
        }
    }

    fun loadHomeData() {
        viewModelScope.launch {
            getHomeDataUseCase()
                .onStart { setLoading(true); setError(null) }
                .catch { exception ->
                    setLoading(false)
                    setError(exception.message ?: "Failed to load home feed")
                }
                .collect { homeData ->
                    setLoading(false)
                    updateState {
                        it.copy(
                            categories = homeData.categories,
                            allProducts = homeData.products,
                            banners = homeData.banners,
                            deals = homeData.deals
                        )
                    }
                    filterAndSortProducts()
                }
        }
    }

    fun onCategorySelected(categoryId: Int) {
        updateState { it.copy(selectedCategoryId = categoryId) }
        filterAndSortProducts()
    }

    fun onSearchQueryChanged(query: String) {
        updateState { it.copy(searchQuery = query) }
        filterAndSortProducts()
    }

    fun onSortSelected(sort: String) {
        updateState { it.copy(selectedSort = sort) }
        filterAndSortProducts()
    }

    fun toggleWishlist(product: Product) {
        wishlistManager.toggleWishlist(product.toWishlistItem())
    }

    private fun filterAndSortProducts() {
        val query = currentState.searchQuery.lowercase().trim()
        val categoryId = currentState.selectedCategoryId
        val sort = currentState.selectedSort

        var result = currentState.allProducts

        // Category Filter
        if (categoryId != 0) {
            result = result.filter { it.categoryId == categoryId }
        }

        // Search query filter
        if (query.isNotEmpty()) {
            result = result.filter {
                it.name.lowercase().contains(query) ||
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
}
