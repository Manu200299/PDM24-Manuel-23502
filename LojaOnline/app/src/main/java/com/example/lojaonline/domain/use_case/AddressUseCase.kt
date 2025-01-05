package com.example.lojaonline.domain.use_case

import android.util.Log
import com.example.lojaonline.data.repository.AddressRepositoryImpl
import com.example.lojaonline.domain.model.Address

class AddressUseCase(private val repository: AddressRepositoryImpl) {

    suspend operator fun invoke(userId: Int ,addAddress: Address){
        repository.addAddress(userId, addAddress)
        Log.d("UseCase", "Adding address: $addAddress")
    }
}