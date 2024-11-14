package com.example.api_coinpaprika.data.remote.model

import com.example.api_coinpaprika.domain.model.CoinDetail

data class CoinDetailDto(
    val id: String,
    val name: String,
    val description: String
) {
    fun toCoinDetail(): CoinDetail {
        return CoinDetail(id = id, name = name, description = description)
    }
}