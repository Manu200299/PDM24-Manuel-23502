package com.example.api_coinpaprika

import CoinDetailViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage

@Composable
fun CoinDetailScreen(
    coinId: String,
    viewModel: CoinDetailViewModel = viewModel()
){
    val coinDetail by viewModel.coinDetail.collectAsState()
    val headerCustomColor = Color(0xFF0061b0)

    LaunchedEffect(coinId) {
        viewModel.fetchCoinDetail(coinId)
    }

    coinDetail?.let { detail ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(color = headerCustomColor)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header coin logo
                AsyncImage(
                    model = detail.logo,
                    contentDescription = "${detail.name} logo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentScale = ContentScale.Crop,
                )
                // Header text
                Text(
                    text = detail.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = detail.description, style = MaterialTheme.typography.bodyMedium)
                Text(text = "${detail.name} is currently ranked the number #${detail.rank} coin")
            }

        }
    } ?: Text(
        text = "Loading...",
        modifier = Modifier.fillMaxSize().padding(16.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}