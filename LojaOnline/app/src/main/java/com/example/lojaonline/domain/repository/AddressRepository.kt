package com.example.lojaonline.domain.repository

import com.example.lojaonline.domain.model.Address
import retrofit2.http.Body

interface AddressRepository{
    suspend fun addAddress(userId: Int ,addAddress: Address)
}