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
import com.example.lojaonline.domain.model.CartItem

@Composable
fun CartScreen(
    sessionManager: SessionManager,
    onBackClick: () -> Unit
) {
    val viewModel: CartViewModel = viewModel(
        factory = CartViewModel.Factory(sessionManager)
    )
    val cartState by viewModel.cartState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCart()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (val state = cartState) {
            is CartState.Loading -> CircularProgressIndicator()
            is CartState.Success -> {
                if (state.cartItems.isEmpty()) {
                    Text("Your cart is empty")
                } else {
                    LazyColumn {
                        items(state.cartItems) { item ->
                            CartItemRow(item)
                        }
                    }
                }
            }
            is CartState.Error -> Text(
                text = "Error: ${state.message}",
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Products")
        }
    }
}

@Composable
fun CartItemRow(item: CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = item.productName, style = MaterialTheme.typography.titleMedium)
                Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = "$${item.productPrice}", style = MaterialTheme.typography.titleMedium)
        }
    }
}
