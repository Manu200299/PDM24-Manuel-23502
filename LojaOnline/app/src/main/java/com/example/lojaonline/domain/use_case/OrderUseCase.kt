package com.example.lojaonline.domain.use_case

import com.example.lojaonline.data.repository.OrderRepositoryImpl
import com.example.lojaonline.domain.model.CreateOrderRequest
import com.example.lojaonline.domain.model.OrderResponse
import com.example.lojaonline.domain.model.OrderWithDetails

//class CreateOrderUseCase(private val repository: OrderRepositoryImpl) {
//    suspend operator fun invoke(createOrderRequest: CreateOrderRequest): OrderResponse {
//        return repository.createOrder(createOrderRequest)
//    }
//}

class GetOrderByIdUseCase(private val repository: OrderRepositoryImpl){
    suspend operator fun invoke(orderId: Int): OrderWithDetails{
        return repository.getOrderById(orderId)
    }
}

class GetOrdersFromUserUseCase(private val repository: OrderRepositoryImpl){
    suspend operator fun invoke(userId: Int): List<OrderWithDetails>{
        return repository.getOrderFromUser(userId)
    }
}