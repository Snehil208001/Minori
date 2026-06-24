package com.snehil.minori.mainui.addressdetailscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.core.cart.CartManager
import com.snehil.minori.core.checkout.CheckoutManager
import com.snehil.minori.core.checkout.UserAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddressDetailsState(
    val name: String = "",
    val phone: String = "",
    val addressLine1: String = "",
    val addressLine2: String = "",
    val city: String = "",
    val state: String = "Delhi", // Default state selection
    val pincode: String = "",
    val addressType: String = "Home", // Home, Work, Other

    // Validation Errors
    val nameError: Boolean = false,
    val phoneError: Boolean = false,
    val addressLine1Error: Boolean = false,
    val addressLine2Error: Boolean = false,
    val cityError: Boolean = false,
    val pincodeError: Boolean = false,

    val checkoutSuccess: Boolean = false
)

sealed interface AddressDetailsEffect {
    object NavigateBack : AddressDetailsEffect
    object NavigateToHome : AddressDetailsEffect
    object NavigateToOrderPreview : AddressDetailsEffect
    data class ShowToast(val message: String) : AddressDetailsEffect
}

@HiltViewModel
class AddressDetailsViewModel @Inject constructor(
    private val cartManager: CartManager,
    private val checkoutManager: CheckoutManager
) : BaseViewModel<AddressDetailsState, AddressDetailsEffect>() {

    override val initialState: AddressDetailsState = AddressDetailsState()

    fun updateName(value: String) = updateState { it.copy(name = value, nameError = false) }
    fun updatePhone(value: String) = updateState { it.copy(phone = value, phoneError = false) }
    fun updateAddressLine1(value: String) = updateState { it.copy(addressLine1 = value, addressLine1Error = false) }
    fun updateAddressLine2(value: String) = updateState { it.copy(addressLine2 = value, addressLine2Error = false) }
    fun updateCity(value: String) = updateState { it.copy(city = value, cityError = false) }
    fun updateStateSelected(value: String) = updateState { it.copy(state = value) }
    fun updatePincode(value: String) = updateState { it.copy(pincode = value, pincodeError = false) }
    fun updateAddressType(value: String) = updateState { it.copy(addressType = value) }

    fun saveAddressAndProceed() {
        val s = currentState
        
        val nameErr = s.name.trim().isBlank()
        val phoneErr = s.phone.trim().isBlank() || s.phone.length < 10
        val line1Err = s.addressLine1.trim().isBlank()
        val line2Err = s.addressLine2.trim().isBlank()
        val cityErr = s.city.trim().isBlank()
        val pinErr = s.pincode.trim().isBlank() || s.pincode.length < 6

        if (nameErr || phoneErr || line1Err || line2Err || cityErr || pinErr) {
            updateState {
                it.copy(
                    nameError = nameErr,
                    phoneError = phoneErr,
                    addressLine1Error = line1Err,
                    addressLine2Error = line2Err,
                    cityError = cityErr,
                    pincodeError = pinErr
                )
            }
            setError("Please correct the highlighted fields with valid details.")
            return
        }

        setError(null)
        checkoutManager.setAddress(
            UserAddress(
                name = s.name.trim(),
                phone = s.phone.trim(),
                addressLine1 = s.addressLine1.trim(),
                addressLine2 = s.addressLine2.trim(),
                city = s.city.trim(),
                state = s.state,
                pincode = s.pincode.trim(),
                addressType = s.addressType
            )
        )
        emitEffect(AddressDetailsEffect.NavigateToOrderPreview)
    }

    fun resetCheckout() {
        updateState { it.copy(checkoutSuccess = false) }
    }

    fun goBack() {
        emitEffect(AddressDetailsEffect.NavigateBack)
    }

    fun goHome() {
        emitEffect(AddressDetailsEffect.NavigateToHome)
    }
}
