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
){
    fun toCreateOrderRequestDto(): CreateOrderRequestDto{
        return CreateOrderRequestDto(userID = userID, total = total, statusID = statusID, orderDetails = orderDetails.map { it.toCreateOrderDetailRequestDto() })
    }
}

data class CreateOrderDetailRequest(
    val productID: Int,
    val quantity: Int,
    val productPrice: Double
){
    fun  toCreateOrderDetailRequestDto(): CreateOrderDetailRequestDto{
        return CreateOrderDetailRequestDto(productID = productID, quantity = quantity, productPrice = productPrice)
    }
}
