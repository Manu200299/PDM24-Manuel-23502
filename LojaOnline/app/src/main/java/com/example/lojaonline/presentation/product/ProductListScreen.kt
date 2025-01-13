package com.example.lojaonline.presentation.product

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
import com.example.lojaonline.presentation.cart.AddToCartState
import com.example.lojaonline.presentation.cart.CartViewModel

@Composable
fun ProductListScreen(
    onAddProductClick: () -> Unit,
    onViewCartClick: () -> Unit,
    sessionManager: SessionManager
) {
    val productViewModel: ProductViewModel = viewModel(
        factory = ProductViewModel.Factory(sessionManager)
    )
    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModel.Factory(sessionManager)
    )
    val productsState by productViewModel.productsState.collectAsState()
    val addToCartState by cartViewModel.addToCartState.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.getAllProducts()
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onAddProductClick,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Add Product")
            }
            Button(
                onClick = onViewCartClick,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text("View Cart")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        when (val state = productsState) {
            is ProductsState.Loading -> CircularProgressIndicator()
            is ProductsState.Success -> {
                LazyColumn {
                    items(state.products) { product ->
                        ProductItem(
                            product = product,
                            onAddToCart = { cartViewModel.addToCart(product.productID, 1) }
                        )
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

    // Show a snackbar when an item is added to the cart
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(addToCartState) {
        when (addToCartState) {
            is AddToCartState.Success -> {
                snackbarHostState.showSnackbar("Item added to cart")
            }
            is AddToCartState.Error -> {
                snackbarHostState.showSnackbar("Failed to add item to cart")
            }
            else -> {}
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun ProductItem(product: Product, onAddToCart: () -> Unit) {
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

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAddToCart,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add to Cart")
            }
        }
    }
}
