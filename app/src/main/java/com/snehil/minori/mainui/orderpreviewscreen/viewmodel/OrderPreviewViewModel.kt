package com.snehil.minori.mainui.orderpreviewscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.core.cart.CartItem
import com.snehil.minori.core.cart.CartManager
import com.snehil.minori.core.checkout.CheckoutManager
import com.snehil.minori.core.checkout.UserAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OrderPreviewState(
    val cartItems: List<CartItem> = emptyList(),
    val address: UserAddress? = null,
    val shippingMethod: String = "Standard", // Standard, Express
    val subtotal: Double = 0.0,
    val shippingFee: Double = 0.0,
    val tax: Double = 0.0,
    val discount: Double = 0.0,
    val total: Double = 0.0
)

sealed interface OrderPreviewEffect {
    object NavigateBack : OrderPreviewEffect
    object NavigateToPayment : OrderPreviewEffect
}

@HiltViewModel
class OrderPreviewViewModel @Inject constructor(
    private val cartManager: CartManager,
    private val checkoutManager: CheckoutManager
) : BaseViewModel<OrderPreviewState, OrderPreviewEffect>() {

    override val initialState: OrderPreviewState = OrderPreviewState()

    init {
        viewModelScope.launch {
            combine(
                cartManager.cartItems,
                checkoutManager.address,
                checkoutManager.selectedShippingMethod
            ) { items, addr, shipping ->
                val subtotal = items.sumOf { it.product.price * it.quantity }
                val shippingFee = if (shipping == "Express") 150.0 else if (subtotal > 1000.0) 0.0 else 50.0
                val tax = Math.round((subtotal * 0.05) * 100.0) / 100.0 // 5% GST
                val discount = 0.0 // Mock discount
                val total = subtotal + shippingFee + tax - discount

                OrderPreviewState(
                    cartItems = items,
                    address = addr,
                    shippingMethod = shipping,
                    subtotal = subtotal,
                    shippingFee = shippingFee,
                    tax = tax,
                    discount = discount,
                    total = total
                )
            }.collect { updatedState ->
                updateState { updatedState }
            }
        }
    }

    fun updateShippingMethod(method: String) {
        checkoutManager.setShippingMethod(method)
    }

    fun proceedToPayment() {
        emitEffect(OrderPreviewEffect.NavigateToPayment)
    }

    fun goBack() {
        emitEffect(OrderPreviewEffect.NavigateBack)
    }
}
