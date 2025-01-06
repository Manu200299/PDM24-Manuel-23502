package com.example.lojaonline.domain.repository

import com.example.lojaonline.domain.model.AddProduct
import com.example.lojaonline.domain.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun addProduct(product: AddProduct): Product
}