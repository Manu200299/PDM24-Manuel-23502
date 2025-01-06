package com.example.lojaonline.domain.repository

import com.example.lojaonline.domain.model.CreateOrderRequest
import com.example.lojaonline.domain.model.OrderResponse
import com.example.lojaonline.domain.model.OrderWithDetails

interface OrderRepository {
//    suspend fun createOrder(createOrderRequest: CreateOrderRequest): OrderResponse
    suspend fun getOrderFromUser(userId: Int): List<OrderWithDetails>
    suspend fun getOrderById(orderId: Int): OrderWithDetails
}
