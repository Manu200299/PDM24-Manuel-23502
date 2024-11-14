package com.example.api_coinpaprika


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.api_coinpaprika.presentation.coin_list.CoinDetailViewModel
import com.example.api_coinpaprika.presentation.coin_list.CoinListViewModel
import com.example.api_coinpaprika.ui.theme.ApicoinpaprikaTheme

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
