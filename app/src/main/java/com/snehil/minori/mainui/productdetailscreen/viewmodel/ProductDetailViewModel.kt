package com.snehil.minori.mainui.productdetailscreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.core.cart.CartManager
import com.snehil.minori.core.common.ProductRegistry
import com.snehil.minori.core.wishlist.WishlistManager
import com.snehil.minori.domain.model.WishlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailState(
    val product: WishlistItem? = null,
    val isWishlisted: Boolean = false,
    val quantity: Int = 1,
    val cartCount: Int = 0
)

sealed interface ProductDetailEffect {
    object NavigateBack : ProductDetailEffect
    object NavigateToAddressDetails : ProductDetailEffect
    data class ShowToast(val message: String) : ProductDetailEffect
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val wishlistManager: WishlistManager,
    private val cartManager: CartManager
) : BaseViewModel<ProductDetailState, ProductDetailEffect>() {

    private val productId: String = savedStateHandle.get<String>("productId") ?: ""
    private val productType: String = savedStateHandle.get<String>("productType") ?: "Product"

    override val initialState: ProductDetailState = ProductDetailState()

    init {
        loadProductDetails()
        observeWishlistAndCart()
    }

    private fun loadProductDetails() {
        val productItem = ProductRegistry.getProduct(productId, productType)
        updateState {
            it.copy(
                product = productItem,
                isWishlisted = productItem?.let { item -> wishlistManager.isWishlisted(item.id) } ?: false
            )
        }
    }

    private fun observeWishlistAndCart() {
        viewModelScope.launch {
            wishlistManager.wishlistFlow.collect { items ->
                val id = currentState.product?.id ?: return@collect
                val wishlisted = items.any { it.id == id }
                updateState { it.copy(isWishlisted = wishlisted) }
            }
        }

        viewModelScope.launch {
            cartManager.cartItems.collect { items ->
                updateState { it.copy(cartCount = cartManager.getCartCount()) }
            }
        }
    }

    fun toggleWishlist() {
        val item = currentState.product ?: return
        wishlistManager.toggleWishlist(item)
        val action = if (currentState.isWishlisted) "Added to" else "Removed from"
        emitEffect(ProductDetailEffect.ShowToast("$action Wishlist!"))
    }

    fun increaseQuantity() {
        if (currentState.quantity < 10) {
            updateState { it.copy(quantity = it.quantity + 1) }
        }
    }

    fun decreaseQuantity() {
        if (currentState.quantity > 1) {
            updateState { it.copy(quantity = it.quantity - 1) }
        }
    }

    fun addToCart() {
        val item = currentState.product ?: return
        cartManager.addToCart(item, currentState.quantity)
        emitEffect(ProductDetailEffect.ShowToast("Added ${currentState.quantity}x ${item.name} to Cart!"))
    }

    fun buyNow() {
        val item = currentState.product ?: return
        cartManager.clearCart()
        cartManager.addToCart(item, currentState.quantity)
        emitEffect(ProductDetailEffect.NavigateToAddressDetails)
    }

    fun goBack() {
        emitEffect(ProductDetailEffect.NavigateBack)
    }
}
