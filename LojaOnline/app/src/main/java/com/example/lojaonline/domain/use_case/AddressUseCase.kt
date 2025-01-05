package com.example.lojaonline.domain.use_case

import android.util.Log
import com.example.lojaonline.data.repository.AddressRepositoryImpl
import com.example.lojaonline.domain.model.Address

class AddressUseCase(private val repository: AddressRepositoryImpl) {

    suspend operator fun invoke(addAddress: Address){
        repository.addAddress(addAddress)
        Log.d("UseCase", "Adding address: $addAddress")
    }
}