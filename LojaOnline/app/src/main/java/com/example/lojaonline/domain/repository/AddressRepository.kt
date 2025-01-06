package com.example.lojaonline.domain.repository

import com.example.lojaonline.domain.model.Address

interface AddressRepository{
    suspend fun addAddress(userId: Int, addAddress: Address)
}