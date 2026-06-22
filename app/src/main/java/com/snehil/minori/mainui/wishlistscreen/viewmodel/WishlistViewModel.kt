package com.snehil.minori.mainui.wishlistscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.core.wishlist.WishlistManager
import com.snehil.minori.domain.model.WishlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WishlistState(
    val searchQuery: String = "",
    val products: List<WishlistItem> = emptyList()
)

sealed class WishlistEffect {
    object NavigateBack : WishlistEffect()
}

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistManager: WishlistManager
) : BaseViewModel<WishlistState, WishlistEffect>() {

    private var allWishlistItems: List<WishlistItem> = emptyList()

    override val initialState: WishlistState = WishlistState()

    init {
        viewModelScope.launch {
            wishlistManager.wishlistFlow.collectLatest { items ->
                allWishlistItems = items
                filterProducts()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        updateState { it.copy(searchQuery = query) }
        filterProducts()
    }

    fun removeWishlistItem(item: WishlistItem) {
        wishlistManager.toggleWishlist(item)
    }

    fun goBack() {
        emitEffect(WishlistEffect.NavigateBack)
    }

    private fun filterProducts() {
        val query = currentState.searchQuery.lowercase().trim()
        val filtered = if (query.isEmpty()) {
            allWishlistItems
        } else {
            allWishlistItems.filter {
                it.name.lowercase().contains(query) ||
                it.description.lowercase().contains(query) ||
                (it.categoryLabel?.lowercase()?.contains(query) ?: false)
            }
        }
        updateState { it.copy(products = filtered) }
    }
}
