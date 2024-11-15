
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.api_coinpaprika.presentation.coin_list.CoinDetailViewModel

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel,
    coinId: String,
    onBackClick: () -> Unit
) {
    LaunchedEffect(coinId) {
        viewModel.fetchCoinDetail(coinId)
    }

    val coinDetail by viewModel.coinDetail.collectAsState()

    when (coinDetail) {
        null -> {
            Text(text = "Loading...")
        }
        else -> {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Name: ${coinDetail!!.name}")
                Text(text = "Description: ${coinDetail!!.description}")
            }
        }
    }

    Button(
        onClick = onBackClick,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Back")
    }
}
