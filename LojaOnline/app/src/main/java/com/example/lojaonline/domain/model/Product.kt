package com.example.lojaonline.domain.model

import android.icu.text.DecimalFormat
import com.example.lojaonline.data.remote.model.AddProductDto

data class Product(
    val productID: Int,
    val categoryID: Int,
    val name: String,
    val description: String,
    val color: String,
    val price: Double,
    val stock: Int,
    val createdAt: String,
    val updatedAt: String
)

data class AddProduct(
    val categoryID: Int,
    val name: String,
    val description: String,
    val color: String,
    val price: Double,
    val stock: Int,
    val createdAt: String,
    val updatedAt: String
) {
    fun toAddProductDto(): AddProductDto {
        return AddProductDto(
            categoryID = categoryID,
            name = name,
            description = description,
            color = color,
            price = price,
            stock = stock,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
