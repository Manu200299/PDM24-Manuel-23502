package com.example.lojaonline.domain.model

import com.example.lojaonline.data.remote.model.CreateOrderDetailRequestDto
import com.example.lojaonline.data.remote.model.CreateOrderRequestDto


data class Order(
    val orderID: Int,
    val userID: Int,
    val total: Double,
    val orderDate: String,
    val statusID: Int
)

data class OrderDetail(
    val orderDetailID: Int,
    val orderID: Int,
    val productID: Int,
    val quantity: Int,
    val productPrice: Double
)

data class OrderWithDetails(
    val order: Order,
    val orderDetails: List<OrderDetail>
)

data class CreateOrderRequest(
    val userID: Int,
    val total: Double,
    val statusID: Int,
    val orderDetails: List<CreateOrderDetailRequest>
)

data class CreateOrderDetailRequest(
    val productID: Int,
    val quantity: Int,
    val productPrice: Double
)

data class OrderResponse(
    val message: String,
    val orderID: Int
)
