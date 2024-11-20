package com.example.api_coinpaprika

import CoinListViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_coinpaprika.domain.model.Coin


@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = viewModel(),
    onCoinClick: (String) -> Unit
) {

    val coins by viewModel.coins.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(coins) { coin ->
            CoinItem(coin = coin, onClick = { onCoinClick(coin.id)})
        }
    }
}

@Composable
fun CoinItem(coin: Coin, onClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .background(color = Color.Blue, shape = RectangleShape)
    ) {
        Text(text = coin.name, style = MaterialTheme.typography.titleMedium)
        Text(text = coin.symbol, style = MaterialTheme.typography.bodySmall)
    }
}