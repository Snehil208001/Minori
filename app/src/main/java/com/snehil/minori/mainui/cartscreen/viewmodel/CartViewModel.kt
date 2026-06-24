package com.snehil.minori.mainui.cartscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.core.cart.CartItem
import com.snehil.minori.core.cart.CartManager
import com.snehil.minori.domain.model.WishlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartState(
    val cartItems: List<CartItem> = emptyList(),
    val cartTotal: Double = 0.0,
    val cartCount: Int = 0,
    val shippingCharge: Double = 150.0, // Flat shipping charge
    val taxRate: Double = 0.05, // 5% GST
    val checkoutSuccess: Boolean = false
)

sealed interface CartEffect {
    object NavigateBack : CartEffect
    object NavigateToHome : CartEffect
    object NavigateToAddressDetails : CartEffect
    data class ShowToast(val message: String) : CartEffect
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartManager: CartManager
) : BaseViewModel<CartState, CartEffect>() {

    override val initialState: CartState = CartState()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartManager.cartItems.collect { items ->
                val count = cartManager.getCartCount()
                val total = cartManager.getCartTotal()
                updateState {
                    it.copy(
                        cartItems = items,
                        cartCount = count,
                        cartTotal = total
                    )
                }
            }
        }
    }

    fun increaseQuantity(item: CartItem) {
        if (item.quantity < 10) {
            cartManager.updateQuantity(item.product.id, item.quantity + 1)
        }
    }

    fun decreaseQuantity(item: CartItem) {
        if (item.quantity > 1) {
            cartManager.updateQuantity(item.product.id, item.quantity - 1)
        } else {
            removeItem(item)
        }
    }

    fun removeItem(item: CartItem) {
        cartManager.removeFromCart(item.product.id)
        emitEffect(CartEffect.ShowToast("${item.product.name} removed from Cart"))
    }

    fun checkout() {
        if (currentState.cartItems.isEmpty()) {
            emitEffect(CartEffect.ShowToast("Your cart is empty!"))
            return
        }
        emitEffect(CartEffect.NavigateToAddressDetails)
    }

    fun resetCheckout() {
        updateState { it.copy(checkoutSuccess = false) }
    }

    fun goBack() {
        emitEffect(CartEffect.NavigateBack)
    }

    fun goHome() {
        emitEffect(CartEffect.NavigateToHome)
    }
}
