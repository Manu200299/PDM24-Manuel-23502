package com.example.lojaonline.presentation.sharedCart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lojaonline.data.local.SessionManager

// presentation/SharedCartScreen.kt
@Composable
fun SharedCartScreen(
    sessionManager: SessionManager,
    onNavigateBack: () -> Unit
) {
    val viewModel: SharedCartViewModel = viewModel(
        factory = SharedCartViewModel.Factory(sessionManager)
    )
    val shareCartState by viewModel.shareCartState.collectAsState()
    val getSharedCartState by viewModel.getSharedCartState.collectAsState()
    val addSharedCartState by viewModel.addSharedCartState.collectAsState()

    var shareCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Share Your Cart",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { viewModel.shareCart() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Share Code")
        }

        when (val state = shareCartState) {
            is SharedCartViewModel.ShareCartState.Success -> {
                Text(
                    text = "Your share code: ${state.shareCode}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            is SharedCartViewModel.ShareCartState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Add Shared Cart",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = shareCode,
            onValueChange = { shareCode = it },
            label = { Text("Enter Share Code") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.addSharedCart(shareCode) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Add Shared Cart")
        }

        when (val state = addSharedCartState) {
            is SharedCartViewModel.AddSharedCartState.Success -> {
                Text(
                    text = "Shared cart added successfully!",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            is SharedCartViewModel.AddSharedCartState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            else -> {}
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}