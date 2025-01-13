package com.example.lojaonline.presentation

import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.domain.model.OrderDetail
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OrderDetailsScreen(
    orderId: Int,
    sessionManager: SessionManager,
    onBackClick: () -> Unit
) {
    val viewModel: OrderViewModel = viewModel(
        factory = OrderViewModel.Factory(sessionManager)
    )
    val orderDetailsState by viewModel.orderDetailsState.collectAsState()

    LaunchedEffect(orderId) {
        viewModel.getOrderById(orderId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Order Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (val state = orderDetailsState) {
            is OrderDetailsState.Loading -> CircularProgressIndicator()
            is OrderDetailsState.Success -> {
                val order = state.order
                Text("Order ID: ${order.order.orderID}")
                Text("Date: ${order.order.orderDate}")
                Text("Total: $${order.order.total}")
                Text("Status: ${order.order.statusID}")

                Spacer(modifier = Modifier.height(16.dp))

                Text("Order Items:", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(order.orderDetails) { detail ->
                        OrderDetailItem(detail)
                    }
                }
            }
            is OrderDetailsState.Error -> Text(
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
            Text("Back")
        }
    }
}

@Composable
fun OrderDetailItem(detail: OrderDetail) {
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
                Text("Product ID: ${detail.productID}")
                Text("Quantity: ${detail.quantity}")
            }
            Text("Price: $${detail.productPrice}")
        }
    }
}
