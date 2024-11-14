package com.example.api_coinpaprika.domain.use_case

import com.example.api_coinpaprika.data.repository.CoinRepositoryImpl
import com.example.api_coinpaprika.domain.model.CoinDetail

class GetCoinDetailUseCase(private val repository: CoinRepositoryImpl) {
    suspend operator fun invoke(coinId: String): CoinDetail {
        return repository.getCoinDetail(coinId)
    }
}