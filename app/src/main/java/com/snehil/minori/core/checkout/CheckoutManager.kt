package com.snehil.minori.core.checkout

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

data class UserAddress(
    val name: String = "",
    val phone: String = "",
    val addressLine1: String = "",
    val addressLine2: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
    val addressType: String = "Home"
)

@Singleton
class CheckoutManager @Inject constructor() {
    private val _address = MutableStateFlow<UserAddress?>(null)
    val address: StateFlow<UserAddress?> = _address.asStateFlow()

    private val _selectedShippingMethod = MutableStateFlow("Standard") // Standard, Express
    val selectedShippingMethod: StateFlow<String> = _selectedShippingMethod.asStateFlow()

    private val _selectedPaymentMethod = MutableStateFlow("") // UPI, Card, NetBanking, COD
    val selectedPaymentMethod: StateFlow<String> = _selectedPaymentMethod.asStateFlow()

    fun setAddress(userAddress: UserAddress) {
        _address.value = userAddress
    }

    fun setShippingMethod(method: String) {
        _selectedShippingMethod.value = method
    }

    fun setPaymentMethod(method: String) {
        _selectedPaymentMethod.value = method
    }

    fun clearCheckout() {
        _address.value = null
        _selectedShippingMethod.value = "Standard"
        _selectedPaymentMethod.value = ""
    }
}
