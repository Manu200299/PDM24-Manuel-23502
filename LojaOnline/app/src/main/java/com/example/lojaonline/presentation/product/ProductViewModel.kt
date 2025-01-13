package com.example.lojaonline.presentation.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.ProductRepositoryImpl
import com.example.lojaonline.domain.model.AddProduct
import com.example.lojaonline.domain.model.Product
import com.example.lojaonline.domain.use_case.AddProductUseCase
import com.example.lojaonline.domain.use_case.GetAllProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = ProductRepositoryImpl(api)
    private val getAllProductsUseCase = GetAllProductUseCase(repository)
    private val addProductUseCase = AddProductUseCase(repository)

    private val _productsState = MutableStateFlow<ProductsState>(ProductsState.Idle)
    val productsState: StateFlow<ProductsState> = _productsState.asStateFlow()

    private val _addProductState = MutableStateFlow<AddProductState>(AddProductState.Idle)
    val addProductState: StateFlow<AddProductState> = _addProductState.asStateFlow()

    fun getAllProducts() {
        viewModelScope.launch {
            _productsState.value = ProductsState.Loading
            try {
                val products = getAllProductsUseCase()
                _productsState.value = ProductsState.Success(products)
                Log.d("ProductViewModel", "Products fetched successfully")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products.", e)
                _productsState.value = ProductsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addProduct(product: AddProduct) {
        viewModelScope.launch {
            _addProductState.value = AddProductState.Loading
            try {
                val addedProduct = addProductUseCase(product)
                _addProductState.value = AddProductState.Success(addedProduct)
                Log.d("ProductViewModel", "Product Added: $addedProduct")
            } catch (e: Exception) {
                _addProductState.value = AddProductState.Error(e.message ?: "Unknown error")
            }
        }
    }

    class Factory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class ProductsState {
    object Idle : ProductsState()
    object Loading : ProductsState()
    data class Success(val products: List<Product>) : ProductsState()
    data class Error(val message: String) : ProductsState()
}

sealed class AddProductState {
    object Idle : AddProductState()
    object Loading : AddProductState()
    data class Success(val product: Product) : AddProductState()
    data class Error(val message: String) : AddProductState()
}
