package com.example.lojaonline.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lojaonline.data.local.SessionManager

@Composable
fun UserProfileScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit,
    onAddAddressClick: () -> Unit,
    onAddProductsClick: () -> Unit,
    onProductsClick: () -> Unit,
) {
    val viewModel: UserProfileViewModel = viewModel(
        factory = UserProfileViewModel.Factory(sessionManager)
    )
    val profileState by viewModel.profileState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = profileState) {
            is ProfileState.Idle -> Text("Idle")
            is ProfileState.Loading -> CircularProgressIndicator()
            is ProfileState.Success -> {
                Text(
                    text = "Welcome, ${state.user.username}!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = onAddAddressClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Address")
                }
                Button(
                    onClick = onAddProductsClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Products")
                }
                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick = onProductsClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Products")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.logout()
                        onLogout()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            }
            is ProfileState.Error -> Text(
                text = "Error: ${state.message}",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}



