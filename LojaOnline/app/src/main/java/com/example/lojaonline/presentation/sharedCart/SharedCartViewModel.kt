package com.example.lojaonline.presentation.sharedCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.SharedCartRepositoryImpl
import com.example.lojaonline.domain.model.CartItem
import com.example.lojaonline.domain.use_case.AddSharedCartUseCase
import com.example.lojaonline.domain.use_case.ShareCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SharedCartViewModel(
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = SharedCartRepositoryImpl(api)
    private val shareCartUseCase = ShareCartUseCase(repository)
    private val addSharedCartUseCase = AddSharedCartUseCase(repository)

    private val _shareCartState = MutableStateFlow<ShareCartState>(ShareCartState.Idle)
    val shareCartState: StateFlow<ShareCartState> = _shareCartState.asStateFlow()

    private val _getSharedCartState = MutableStateFlow<GetSharedCartState>(GetSharedCartState.Idle)
    val getSharedCartState: StateFlow<GetSharedCartState> = _getSharedCartState.asStateFlow()

    private val _addSharedCartState = MutableStateFlow<AddSharedCartState>(AddSharedCartState.Idle)
    val addSharedCartState: StateFlow<AddSharedCartState> = _addSharedCartState.asStateFlow()

    fun shareCart() {
        viewModelScope.launch {
            _shareCartState.value = ShareCartState.Loading
            try {
                val userId = sessionManager.userID.first() ?: throw Exception("User not logged in")
                val shareCode = shareCartUseCase(userId)
                _shareCartState.value = ShareCartState.Success(shareCode)
            } catch (e: Exception) {
                _shareCartState.value = ShareCartState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addSharedCart(shareCode: String) {
        viewModelScope.launch {
            _addSharedCartState.value = AddSharedCartState.Loading
            try {
                val userId = sessionManager.userID.first() ?: throw Exception("User not logged in")
                addSharedCartUseCase(userId, shareCode)
                _addSharedCartState.value = AddSharedCartState.Success
            } catch (e: Exception) {
                _addSharedCartState.value = AddSharedCartState.Error(e.message ?: "Unknown error")
            }
        }
    }
    sealed class ShareCartState {
        object Idle : ShareCartState()
        object Loading : ShareCartState()
        data class Success(val shareCode: String) : ShareCartState()
        data class Error(val message: String) : ShareCartState()
    }

    sealed class GetSharedCartState {
        object Idle : GetSharedCartState()
        object Loading : GetSharedCartState()
        data class Success(val cartItems: List<CartItem>) : GetSharedCartState()
        data class Error(val message: String) : GetSharedCartState()
    }

    sealed class AddSharedCartState {
        object Idle : AddSharedCartState()
        object Loading : AddSharedCartState()
        object Success : AddSharedCartState()
        data class Error(val message: String) : AddSharedCartState()
    }

    class Factory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedCartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SharedCartViewModel(sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}