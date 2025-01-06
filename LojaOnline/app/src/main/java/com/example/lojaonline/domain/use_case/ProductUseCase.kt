package com.example.lojaonline.domain.use_case

import com.example.lojaonline.data.repository.ProductRepositoryImpl
import com.example.lojaonline.domain.model.AddProduct
import com.example.lojaonline.domain.model.Product

class AddProductUseCase(private val repository: ProductRepositoryImpl) {
    suspend operator fun invoke(product: AddProduct): Product{
        return repository.addProduct(product)
    }
}

class GetAllProductUseCase(private val repository: ProductRepositoryImpl){
    suspend operator fun invoke(): List<Product>{
        return repository.getAllProducts()
    }
}