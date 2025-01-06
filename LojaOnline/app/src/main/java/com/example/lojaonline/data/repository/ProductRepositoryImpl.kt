package com.example.lojaonline.data.repository

import androidx.core.os.persistableBundleOf
import com.example.lojaonline.data.remote.api.LojaOnlineApi
import com.example.lojaonline.data.remote.model.AddProductDto
import com.example.lojaonline.domain.model.AddProduct
import com.example.lojaonline.domain.model.Product
import com.example.lojaonline.domain.repository.ProductRepository

class ProductRepositoryImpl(private val api: LojaOnlineApi): ProductRepository {
    override suspend fun getAllProducts(): List<Product>{
        val response = api.getAllProducts()
        if (response.isSuccessful){
            return response.body()?.map { it.toProduct() } ?: emptyList()
        } else {
            throw Exception("Failed to get products: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun addProduct(product: AddProduct): Product{
        val response = api.addProduct(product.toAddProductDto())
        if (response.isSuccessful){
            return response.body()?.toProduct() ?: throw Exception("Product response body is null")
        } else {
            throw Exception("Failed to add product: ${response.code()} ${response.message()}")
        }
    }
}