package com.example.api_coinpaprika.domain.repository

import com.example.api_coinpaprika.domain.model.Coin
import com.example.api_coinpaprika.domain.model.CoinDetail

interface CoinRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinDetail(coinId: String): CoinDetail
}