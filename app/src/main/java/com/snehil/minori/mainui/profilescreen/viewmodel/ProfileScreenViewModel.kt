package com.snehil.minori.mainui.profilescreen.viewmodel

import com.snehil.minori.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ProfileState(
    val email: String = "cozyboho@minori.com",
    val password: String = "cozyboho123",
    val pincode: String = "110001",
    val address: String = "12, Boho Lane, Clay Pottery District",
    val city: String = "New Delhi",
    val state: String = "Delhi",
    val country: String = "India",
    val bankAccountNo: String = "987654321012",
    val bankHolderName: String = "Boho Artisan Studio",
    val bankIfsc: String = "BOCO0001234",
    
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val pincodeError: Boolean = false,
    val addressError: Boolean = false,
    val cityError: Boolean = false,
    val bankAccountNoError: Boolean = false,
    val bankHolderNameError: Boolean = false,
    val bankIfscError: Boolean = false
)

sealed class ProfileEffect {
    object SaveSuccess : ProfileEffect()
    object NavigateBack : ProfileEffect()
}

@HiltViewModel
class ProfileScreenViewModel @Inject constructor() : BaseViewModel<ProfileState, ProfileEffect>() {
    override val initialState: ProfileState = ProfileState()

    fun updateEmail(value: String) = updateState { it.copy(email = value, emailError = false) }
    fun updatePassword(value: String) = updateState { it.copy(password = value, passwordError = false) }
    fun updatePincode(value: String) = updateState { it.copy(pincode = value, pincodeError = false) }
    fun updateAddress(value: String) = updateState { it.copy(address = value, addressError = false) }
    fun updateCity(value: String) = updateState { it.copy(city = value, cityError = false) }
    fun updateStateSelected(value: String) = updateState { it.copy(state = value) }
    fun updateCountry(value: String) = updateState { it.copy(country = value) }
    fun updateBankAccountNo(value: String) = updateState { it.copy(bankAccountNo = value, bankAccountNoError = false) }
    fun updateBankHolderName(value: String) = updateState { it.copy(bankHolderName = value, bankHolderNameError = false) }
    fun updateBankIfsc(value: String) = updateState { it.copy(bankIfsc = value, bankIfscError = false) }

    fun saveProfile() {
        val state = currentState
        
        val emailErr = state.email.isBlank()
        val pwdErr = state.password.isBlank()
        val pinErr = state.pincode.isBlank()
        val addrErr = state.address.isBlank()
        val cityErr = state.city.isBlank()
        val bankAccErr = state.bankAccountNo.isBlank()
        val bankNameErr = state.bankHolderName.isBlank()
        val bankIfscErr = state.bankIfsc.isBlank()

        if (emailErr || pwdErr || pinErr || addrErr || cityErr || bankAccErr || bankNameErr || bankIfscErr) {
            updateState { 
                it.copy(
                    emailError = emailErr,
                    passwordError = pwdErr,
                    pincodeError = pinErr,
                    addressError = addrErr,
                    cityError = cityErr,
                    bankAccountNoError = bankAccErr,
                    bankHolderNameError = bankNameErr,
                    bankIfscError = bankIfscErr
                )
            }
            setError("Please fill out all required fields.")
            return
        }

        setError(null)
        emitEffect(ProfileEffect.SaveSuccess)
    }

    fun goBack() {
        emitEffect(ProfileEffect.NavigateBack)
    }
}
