package com.example.lojaonline.data.repository

import com.example.lojaonline.domain.model.CreateOrderDetailRequest

import android.util.Log
import com.example.lojaonline.data.remote.api.LojaOnlineApi
import com.example.lojaonline.domain.model.CreateOrderRequest
import com.example.lojaonline.domain.model.OrderResponse
import com.example.lojaonline.domain.model.OrderWithDetails
import com.example.lojaonline.domain.repository.OrderRepository

class OrderRepositoryImpl(private val api: LojaOnlineApi) : OrderRepository {

//    override suspend fun createOrder(createOrderRequest: CreateOrderRequest): OrderResponse {
//        val createOrderRequestDto = createOrderRequest.toCreateOrderRequestDto()
//        val response = api.createOrder(createOrderRequestDto)
//        if (response.isSuccessful) {
//            return response.body()?.toOrderResponse()
//                ?: throw Exception("Order response body is null")
//        } else {
//            val statusCode = response.code()
//            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
//            Log.e("OrderRepositoryImpl", "Failed to create order: HTTP $statusCode - $errorMessage")
//            throw Exception("Failed to create order: HTTP $statusCode - $errorMessage")
//        }
//    }

    override suspend fun getOrderFromUser(userId: Int): List<OrderWithDetails> {
        val response = api.getOrderFromUser(userId)
        if (response.isSuccessful) {
            return response.body()?.map { it.toOrderWithDetails() } ?: emptyList()
        } else {
            val statusCode = response.code()
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            Log.e("OrderRepositoryImpl", "Failed to get orders: HTTP $statusCode - $errorMessage")
            throw Exception("Failed to get orders: HTTP $statusCode - $errorMessage")
        }
    }

    override suspend fun getOrderById(orderId: Int): OrderWithDetails {
        val response = api.getOrderById(orderId)
        if (response.isSuccessful) {
            return response.body()?.toOrderWithDetails()
                ?: throw Exception("Order details response body is null")
        } else {
            val statusCode = response.code()
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            Log.e("OrderRepositoryImpl", "Failed to get order details: HTTP $statusCode - $errorMessage")
            throw Exception("Failed to get order details: HTTP $statusCode - $errorMessage")
        }
    }
}

