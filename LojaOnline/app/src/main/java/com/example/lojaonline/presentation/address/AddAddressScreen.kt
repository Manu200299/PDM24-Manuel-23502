package com.example.lojaonline.presentation.address

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.domain.model.Address

@Composable
fun AddAddressScreen(
    onAddressAdded: () -> Unit,
    sessionManager: SessionManager
) {
    val viewModel: AddAddressViewModel = viewModel(
        factory = AddAddressViewModel.Factory(sessionManager)
    )

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var streetName by remember { mutableStateOf("") }
    var streetAdditional by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var additionalNote by remember { mutableStateOf("") }

    val addressState by viewModel.addressState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Address",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = streetName,
            onValueChange = { streetName = it },
            label = { Text("Street Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = streetAdditional,
            onValueChange = { streetAdditional = it },
            label = { Text("Additional Street Info") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = postalCode,
            onValueChange = { postalCode = it },
            label = { Text("Postal Code") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = district,
            onValueChange = { district = it },
            label = { Text("District") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Country") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = additionalNote,
            onValueChange = { additionalNote = it },
            label = { Text("Additional Note") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val address = Address(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    streetName = streetName,
                    streetAdditional = streetAdditional,
                    postalCode = postalCode,
                    district = district,
                    city = city,
                    country = country,
                    additionalNote = additionalNote
                )
                viewModel.addAddress(address)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Address")
        }

        when (val state = addressState) {
            is AddressState.Loading -> CircularProgressIndicator()
            is AddressState.Error -> Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
            is AddressState.Success -> {
                LaunchedEffect(Unit) {
                    onAddressAdded()
                }
            }
            else -> {}
        }
    }
}

