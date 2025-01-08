package com.example.lojaonline.domain.use_case

import android.util.Log
import com.example.lojaonline.domain.model.Address
import com.example.lojaonline.domain.repository.AddressRepository

class AddressUseCase(private val repository: AddressRepository) {

    suspend operator fun invoke(userId: Int ,addAddress: Address){
        repository.addAddress(userId, addAddress)
        Log.d("UseCase", "Adding address: $addAddress")
    }
}