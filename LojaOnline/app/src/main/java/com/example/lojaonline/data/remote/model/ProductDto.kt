package com.example.lojaonline.data.remote.model

import android.icu.text.DecimalFormat
import androidx.compose.ui.input.key.Key.Companion.D
import com.example.lojaonline.domain.model.Product

data class ProductDto(
    val productID: Int,
    val categoryID: Int,
    val name: String,
    val description: String,
    val color: String,
    val price: Double,
    val stock: Int,
    val createdAt: String,
    val updatedAt: String
){
    fun toProduct(): Product {
        return Product(productID = productID, categoryID = categoryID, name = name, description = description, color = color, price = price, stock = stock, createdAt = createdAt, updatedAt = updatedAt )
    }
}

data class AddProductDto(
    val categoryID: Int,
    val name: String,
    val description: String,
    val color: String,
    val price: Double,
    val stock: Int,
    val createdAt: String,
    val updatedAt: String
)