package com.example.api_coinpaprika

import CoinDetailViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CoinDetailScreen(
    coinId: String,
    viewModel: CoinDetailViewModel = viewModel()
){
    val coinDetail by viewModel.coinDetail.collectAsState()

    LaunchedEffect(coinId) {
        viewModel.fetchCoinDetail(coinId)
    }

    coinDetail?.let { detail ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = detail.name, style = MaterialTheme.typography.titleLarge)
            Text(text = detail.description, style = MaterialTheme.typography.bodyMedium)
        }
    } ?: Text(
        text = "Loading...",
        modifier = Modifier.fillMaxSize().padding(16.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}