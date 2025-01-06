package com.example.lojaonline.data.remote.model

import com.example.lojaonline.domain.model.Order
import com.example.lojaonline.domain.model.OrderDetail
import com.example.lojaonline.domain.model.OrderResponse
import com.example.lojaonline.domain.model.OrderWithDetails

data class OrderDto(
    val orderID: Int,
    val userID: Int,
    val total: Double,
    val orderDate: String,
    val statusID: Int
) {
    fun toOrder(): Order = Order(orderID, userID, total, orderDate, statusID)
}

data class OrderDetailDto(
    val orderDetailID: Int,
    val orderID: Int,
    val productID: Int,
    val quantity: Int,
    val productPrice: Double
) {
    fun toOrderDetail(): OrderDetail = OrderDetail(orderDetailID, orderID, productID, quantity, productPrice)
}

data class OrderWithDetailsDto(
    val order: OrderDto,
    val orderDetails: List<OrderDetailDto>
) {
    fun toOrderWithDetails(): OrderWithDetails = OrderWithDetails(
        order = order.toOrder(),
        orderDetails = orderDetails.map { it.toOrderDetail() }
    )
}

data class CreateOrderRequestDto(
    val userID: Int,
    val total: Double,
    val statusID: Int,
    val orderDetails: List<CreateOrderDetailRequestDto>
)

data class CreateOrderDetailRequestDto(
    val productID: Int,
    val quantity: Int,
    val productPrice: Double
)

data class OrderResponseDto(
    val message: String,
    val orderID: Int
) {
    fun toOrderResponse(): OrderResponse = OrderResponse(message, orderID)
}

