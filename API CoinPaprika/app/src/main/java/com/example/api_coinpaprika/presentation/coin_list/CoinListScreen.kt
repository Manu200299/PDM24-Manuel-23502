
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.api_coinpaprika.domain.model.Coin
import com.example.api_coinpaprika.presentation.coin_list.CoinListViewModel


@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel,
    onCoinClick: (String) -> Unit
) {
    val coins by viewModel.coins.collectAsState()

    // Fetch coins when the composable is first displayed
    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }

    // Display the coins in a lazy column
    LazyColumn {
        items(coins) { coin ->
            CoinItem(coin = coin, onCoinClick = onCoinClick)
        }
    }
}

@Composable
fun CoinItem(
    coin: Coin,
    onCoinClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCoinClick(coin.id) }
            .padding(16.dp)
    ) {
        Text(text = "${coin.name} (${coin.symbol})")
    }
}

