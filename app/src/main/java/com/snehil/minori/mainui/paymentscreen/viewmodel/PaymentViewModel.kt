package com.snehil.minori.mainui.paymentscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.core.cart.CartManager
import com.snehil.minori.core.checkout.CheckoutManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentState(
    val selectedMethod: String = "UPI", // UPI, Card, NetBanking, COD
    
    // Card inputs
    val cardNumber: String = "",
    val expiryDate: String = "",
    val cvv: String = "",
    val cardholderName: String = "",

    // Card Errors
    val cardNumberError: Boolean = false,
    val expiryError: Boolean = false,
    val cvvError: Boolean = false,
    val cardholderNameError: Boolean = false,

    val totalPayable: Double = 0.0,
    val checkoutSuccess: Boolean = false
)

sealed interface PaymentEffect {
    object NavigateBack : PaymentEffect
    object NavigateToHome : PaymentEffect
    data class ShowToast(val message: String) : PaymentEffect
}

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val cartManager: CartManager,
    private val checkoutManager: CheckoutManager
) : BaseViewModel<PaymentState, PaymentEffect>() {

    override val initialState: PaymentState = PaymentState()

    init {
        viewModelScope.launch {
            combine(
                cartManager.cartItems,
                checkoutManager.selectedShippingMethod
            ) { items, shipping ->
                val subtotal = items.sumOf { it.product.price * it.quantity }
                val shippingFee = if (shipping == "Express") 150.0 else if (subtotal > 1000.0) 0.0 else 50.0
                val tax = Math.round((subtotal * 0.05) * 100.0) / 100.0 // 5% GST
                subtotal + shippingFee + tax
            }.collect { total ->
                updateState { it.copy(totalPayable = total) }
            }
        }
    }

    fun updatePaymentMethod(method: String) {
        updateState { it.copy(selectedMethod = method) }
        checkoutManager.setPaymentMethod(method)
    }

    fun updateCardNumber(value: String) = updateState { it.copy(cardNumber = value, cardNumberError = false) }
    fun updateExpiryDate(value: String) = updateState { it.copy(expiryDate = value, expiryError = false) }
    fun updateCvv(value: String) = updateState { it.copy(cvv = value, cvvError = false) }
    fun updateCardholderName(value: String) = updateState { it.copy(cardholderName = value, cardholderNameError = false) }

    fun payAndPlaceOrder() {
        val s = currentState
        
        if (s.selectedMethod == "Card") {
            val numErr = s.cardNumber.trim().length < 16
            val expErr = s.expiryDate.trim().length < 5 || !s.expiryDate.contains("/")
            val cvvErr = s.cvv.trim().length < 3
            val nameErr = s.cardholderName.trim().isBlank()

            if (numErr || expErr || cvvErr || nameErr) {
                updateState {
                    it.copy(
                        cardNumberError = numErr,
                        expiryError = expErr,
                        cvvError = cvvErr,
                        cardholderNameError = nameErr
                    )
                }
                setError("Please correct the card details.")
                return
            }
        }

        setError(null)
        viewModelScope.launch {
            setLoading(true)
            delay(2000) // Mock payment authorization processing
            setLoading(false)
            updateState { it.copy(checkoutSuccess = true) }
            cartManager.clearCart()
            checkoutManager.clearCheckout()
            emitEffect(PaymentEffect.ShowToast("Payment Successful! Order Placed."))
        }
    }

    fun resetCheckout() {
        updateState { it.copy(checkoutSuccess = false) }
    }

    fun goBack() {
        emitEffect(PaymentEffect.NavigateBack)
    }

    fun goHome() {
        emitEffect(PaymentEffect.NavigateToHome)
    }
}
