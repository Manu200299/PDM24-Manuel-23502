package com.example.lojaonline.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.domain.model.Product

@Composable
fun ProductListScreen(
    onAddProductClick: () -> Unit,
    sessionManager: SessionManager
) {
    val viewModel: ProductViewModel = viewModel(
        factory = ProductViewModel.Factory(sessionManager)
    )
    val productsState by viewModel.productsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Products",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onAddProductClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Product")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = productsState) {
            is ProductsState.Loading -> CircularProgressIndicator()
            is ProductsState.Success -> {
                LazyColumn {
                    items(state.products) { product ->
                        ProductItem(product)
                    }
                }
            }
            is ProductsState.Error -> Text(
                text = "Error: ${state.message}",
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Price: $${product.price}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Stock: ${product.stock}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Color: ${product.color}", style = MaterialTheme.typography.bodyMedium)
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
