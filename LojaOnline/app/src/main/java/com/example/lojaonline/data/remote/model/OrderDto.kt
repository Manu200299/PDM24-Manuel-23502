package com.example.lojaonline.data.remote.model

import com.example.lojaonline.domain.model.Order
import com.example.lojaonline.domain.model.OrderDetail
import com.example.lojaonline.domain.model.OrderWithDetails

// POST response
data class OrderResponse(
    val message: String, // "Order created successfully"
    val orderID: Int,
)

// Class of main order get
data class OrderDto(
    val orderID: Int,
    val userID: Int,
    val total: Double,
    val orderDate: String,
    val statusID: Int
){
    fun toOrder(): Order {
        return Order(orderID = orderID, userID = userID, total = total, orderDate = orderDate, statusID = statusID)
    }
}

// Class of order Detail get
data class OrderDetailDto(
    val orderDetailID: Int,
    val orderID: Int,
    val productID: Int,
    val quantity: Int,
    val productPrice: Double
){
    fun toOrderDetail(): OrderDetail{
        return OrderDetail(orderDetailID = orderDetailID, orderID = orderID, productID = productID, quantity = quantity, productPrice = productPrice)
    }
}

// Class of order get combined (GET request response)
data class OrderWithDetailsDto(
    val order: OrderDto,
    val orderDetails: List<OrderDetailDto>
){
    fun toOrderWithDetails(): OrderWithDetails{
        return OrderWithDetails(order = order.toOrder(), orderDetails = orderDetails.map { it.toOrderDetail() })
    }
}


// Class of order request
data class CreateOrderRequestDto(
    val userID: Int,
    val total: Double,
    val statusID: Int,
    val orderDetails: List<CreateOrderDetailRequestDto>
)
// orderDetails field class of order request
data class CreateOrderDetailRequestDto(
    val productID: Int,
    val quantity: Int,
    val productPrice: Double
)
