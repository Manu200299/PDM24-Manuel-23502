package com.example.api_coinpaprika


import CoinDetailScreen
import CoinListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_coinpaprika.presentation.coin_list.CoinDetailViewModel
import com.example.api_coinpaprika.presentation.coin_list.CoinListViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var selectedCoinId by remember { mutableStateOf<String?>(null) }

    if (selectedCoinId == null) {
        val coinListViewModel: CoinListViewModel = viewModel()
        CoinListScreen(coinListViewModel) { coinId ->
            selectedCoinId = coinId
        }
    } else {
        val coinDetailViewModel: CoinDetailViewModel = viewModel()
        CoinDetailScreen(coinDetailViewModel, selectedCoinId!!) {
            selectedCoinId = null
        }
    }
}
