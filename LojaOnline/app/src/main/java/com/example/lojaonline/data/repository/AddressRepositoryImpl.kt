package com.example.lojaonline.data.repository

import android.content.Context
import android.util.Log
import com.example.lojaonline.data.remote.api.LojaOnlineApi
import com.example.lojaonline.domain.model.Address
import com.example.lojaonline.domain.repository.AddressRepository
import okhttp3.Response

class AddressRepositoryImpl(private val api: LojaOnlineApi): AddressRepository {

    override suspend fun addAddress(addAddress: Address) {
        val addAddressDto = addAddress.toAddressDto()
        val response = api.addAddressToUser(addAddressDto)
        if (response.isSuccessful){
            Log.d("UserRepositoryImpl", "User created successfully: $addAddressDto")
        } else  {
            val statusCode = response.code()
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            Log.e("UserRepositoryImpl", "Failed to create user: HTTP $statusCode - $errorMessage")
            throw Exception("Failed to create user: HTTP $statusCode - $errorMessage")        }
    }
}