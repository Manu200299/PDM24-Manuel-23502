package com.example.lojaonline.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.CartRepositoryImpl
import com.example.lojaonline.data.repository.UserRepositoryImpl
import com.example.lojaonline.domain.model.AddToCart
import com.example.lojaonline.domain.model.CartItem
import com.example.lojaonline.domain.use_case.AddToCartUseCase
import com.example.lojaonline.domain.use_case.GetCartFromUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CartViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = CartRepositoryImpl(api)
    private val addToCartUseCase = AddToCartUseCase(repository)
    private val getCartFromUserUseCase = GetCartFromUserUseCase(repository)

    private val _cartState = MutableStateFlow<CartState>(CartState.Idle)
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()

    private val _addToCartState = MutableStateFlow<AddToCartState>(AddToCartState.Idle)
    val addToCartState: StateFlow<AddToCartState> = _addToCartState.asStateFlow()

    fun getCart() {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            try {
                val userId = sessionManager.userID.first() ?: throw Exception("User ID not found")
                val cartItems = getCartFromUserUseCase(userId)
                _cartState.value = CartState.Success(cartItems)
            } catch (e: Exception) {
                _cartState.value = CartState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addToCart(productId: Int, quantity: Int) {
        viewModelScope.launch {
            _addToCartState.value = AddToCartState.Loading
            try {
                val userId = sessionManager.userID.first() ?: throw Exception("User ID not found")
                val cartItem = AddToCart(
                    userID = userId,
                    productID = productId,
                    quantity = quantity,
                    isShared = false // You can make this configurable if needed
                )
                val addedItem = addToCartUseCase(cartItem)
                _addToCartState.value = AddToCartState.Success(addedItem)
                getCart() // Refresh the cart after adding an item
            } catch (e: Exception) {
                _addToCartState.value = AddToCartState.Error(e.message ?: "Unknown error")
            }
        }
    }

    class Factory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class CartState {
    object Idle : CartState()
    object Loading : CartState()
    data class Success(val cartItems: List<CartItem>) : CartState()
    data class Error(val message: String) : CartState()
}

sealed class AddToCartState {
    object Idle : AddToCartState()
    object Loading : AddToCartState()
    data class Success(val addedItem: CartItem) : AddToCartState()
    data class Error(val message: String) : AddToCartState()
}
