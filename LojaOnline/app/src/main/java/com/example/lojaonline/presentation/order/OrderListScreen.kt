package com.example.lojaonline.presentation.order

import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.domain.model.OrderWithDetails
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OrderListScreen(
    sessionManager: SessionManager,
    onOrderClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: OrderViewModel = viewModel(
        factory = OrderViewModel.Factory(sessionManager)
    )
    val userOrdersState by viewModel.userOrdersState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserOrders()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Orders",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (val state = userOrdersState) {
            is UserOrdersState.Loading -> CircularProgressIndicator()
            is UserOrdersState.Success -> {
                if (state.orders.isEmpty()) {
                    Text("You have no orders yet.")
                } else {
                    LazyColumn {
                        items(state.orders) { orderWithDetails ->
                            OrderItem(orderWithDetails, onOrderClick)
                        }
                    }
                }
            }
            is UserOrdersState.Error -> Text(
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
fun OrderItem(orderWithDetails: OrderWithDetails, onOrderClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onOrderClick(orderWithDetails.order.orderID) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Order ID: ${orderWithDetails.order.orderID}")
            Text("Date: ${orderWithDetails.order.orderDate}")
            Text("Total: $${orderWithDetails.order.total}")
            Text("Status: ${orderWithDetails.order.statusID}")
        }
    }
}
