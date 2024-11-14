package com.example.api_coinpaprika.domain.use_case

import com.example.api_coinpaprika.data.repository.CoinRepositoryImpl
import com.example.api_coinpaprika.domain.model.Coin

class GetCoinsUseCase(private val repository: CoinRepositoryImpl) {
    suspend operator fun invoke(): List<Coin> {
        return repository.getCoins()
    }
}