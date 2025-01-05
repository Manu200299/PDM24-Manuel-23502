//package com.example.lojaonline.presentation
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//@Composable
//fun UserProfileScreen(
//    viewModel: UserProfileViewModel = viewModel(),
//    onLogout: () -> Unit
//) {
//    val userState by viewModel.userState.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        when (val state = userState) {
//            is UserProfileState.Loading -> CircularProgressIndicator()
//            is UserProfileState.Error -> Text(
//                text = "Error: ${state.message}",
//                color = MaterialTheme.colorScheme.error
//            )
//            is UserProfileState.Success -> {
//                Text(
//                    text = "Welcome, ${state.username}!",
//                    style = MaterialTheme.typography.headlineMedium,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//                Text(
//                    text = "Your token: ${state.token}",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.padding(bottom = 32.dp)
//                )
//                Button(
//                    onClick = {
//                        viewModel.logout()
//                        onLogout()
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Logout")
//                }
//            }
//        }
//    }
//}
//
