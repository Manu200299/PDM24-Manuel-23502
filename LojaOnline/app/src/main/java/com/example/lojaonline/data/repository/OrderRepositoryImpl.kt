package com.example.lojaonline.data.repository

import com.example.lojaonline.data.remote.api.LojaOnlineApi
import com.example.lojaonline.data.remote.model.OrderResponse
import com.example.lojaonline.domain.model.CreateOrderRequest
import com.example.lojaonline.domain.model.OrderWithDetails
import com.example.lojaonline.domain.repository.OrderRepository

class OrderRepositoryImpl(private val api: LojaOnlineApi): OrderRepository {
    override suspend fun createOrder(orderRequest: CreateOrderRequest): OrderWithDetails {
        val response = api.createOrder(orderRequest.toCreateOrderRequestDto())
        if (response.isSuccessful){
            return response.body()?.to
        }
    }
}