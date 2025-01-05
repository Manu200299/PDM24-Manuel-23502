package com.example.lojaonline.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.AddressRepositoryImpl
import com.example.lojaonline.domain.model.Address
import com.example.lojaonline.domain.use_case.AddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddAddressViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = AddressRepositoryImpl(api)
    private val addAddressUseCase = AddressUseCase(repository)

    private val _addressState = MutableStateFlow<AddressState>(AddressState.Idle)
    val addressState: StateFlow<AddressState> = _addressState.asStateFlow()

    fun addAddress(address: Address) {
        viewModelScope.launch {
            _addressState.value = AddressState.Loading
            try {
                val userId = sessionManager.userID.first() ?: throw Exception("User ID not found")
                addAddressUseCase(userId, address)
                _addressState.value = AddressState.Success
                Log.d("AddressViewModel", "Address added successfully for user: $userId")
            } catch (e: Exception) {
                _addressState.value = AddressState.Error(e.message ?: "Unknown error")
                Log.e("AddressViewModel", "Error adding address: ${e.message}")
            }
        }
    }

    class Factory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddAddressViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddAddressViewModel(sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class AddressState {
    object Idle : AddressState()
    object Loading : AddressState()
    object Success : AddressState()
    data class Error(val message: String) : AddressState()
}

