package com.example.lojaonline.presentation.order

import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.domain.model.OrderResponse
import kotlinx.coroutines.flow.first
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.repository.OrderRepositoryImpl
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.domain.model.OrderWithDetails
import com.example.lojaonline.domain.use_case.GetOrdersFromUserUseCase
import com.example.lojaonline.domain.use_case.GetOrderByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = OrderRepositoryImpl(api)
//    private val createOrderUseCase = CreateOrderUseCase(repository)
    private val getOrdersFromUserUseCase = GetOrdersFromUserUseCase(repository)
    private val getOrderByIdUseCase = GetOrderByIdUseCase(repository)

//    private val _createOrderState = MutableStateFlow<CreateOrderState>(CreateOrderState.Idle)
//    val createOrderState: StateFlow<CreateOrderState> = _createOrderState.asStateFlow()

    private val _userOrdersState = MutableStateFlow<UserOrdersState>(UserOrdersState.Idle)
    val userOrdersState: StateFlow<UserOrdersState> = _userOrdersState.asStateFlow()

    private val _orderDetailsState = MutableStateFlow<OrderDetailsState>(OrderDetailsState.Idle)
    val orderDetailsState: StateFlow<OrderDetailsState> = _orderDetailsState.asStateFlow()

//    fun createOrder(createOrderRequest: CreateOrderRequest) {
//        viewModelScope.launch {
//            _createOrderState.value = CreateOrderState.Loading
//            try {
//                val orderResponse = createOrderUseCase(createOrderRequest)
//                _createOrderState.value = CreateOrderState.Success(orderResponse)
//            } catch (e: Exception) {
//                _createOrderState.value = CreateOrderState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }

    fun getUserOrders() {
        viewModelScope.launch {
            _userOrdersState.value = UserOrdersState.Loading
            try {
                val userId = sessionManager.userID.first() ?: throw Exception("User ID not found")
                val orders = getOrdersFromUserUseCase(userId)
                _userOrdersState.value = UserOrdersState.Success(orders)
            } catch (e: Exception) {
                _userOrdersState.value = UserOrdersState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getOrderById(orderId: Int) {
        viewModelScope.launch {
            _orderDetailsState.value = OrderDetailsState.Loading
            try {
                val order = getOrderByIdUseCase(orderId)
                _orderDetailsState.value = OrderDetailsState.Success(order)
            } catch (e: Exception) {
                _orderDetailsState.value = OrderDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    class Factory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OrderViewModel(sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class CreateOrderState {
    object Idle : CreateOrderState()
    object Loading : CreateOrderState()
    data class Success(val orderResponse: OrderResponse) : CreateOrderState()
    data class Error(val message: String) : CreateOrderState()
}

sealed class UserOrdersState {
    object Idle : UserOrdersState()
    object Loading : UserOrdersState()
    data class Success(val orders: List<OrderWithDetails>) : UserOrdersState()
    data class Error(val message: String) : UserOrdersState()
}

sealed class OrderDetailsState {
    object Idle : OrderDetailsState()
    object Loading : OrderDetailsState()
    data class Success(val order: OrderWithDetails) : OrderDetailsState()
    data class Error(val message: String) : OrderDetailsState()
}
