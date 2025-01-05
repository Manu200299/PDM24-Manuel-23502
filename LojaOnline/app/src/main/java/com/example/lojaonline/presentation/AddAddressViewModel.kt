package com.example.lojaonline.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.AddressRepositoryImpl
import com.example.lojaonline.domain.model.Address
import com.example.lojaonline.domain.use_case.AddressUseCase
import kotlinx.coroutines.launch

class AddAddressViewModel: ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = AddressRepositoryImpl(api)
    private val addAddressUseCase = AddressUseCase(repository)

    fun addAddress(addAddress: Address){
        viewModelScope.launch {
            try{
                Log.d("AddressAddViewModel", "Adding address: $addAddress")
                addAddressUseCase(addAddress)
            } catch (e : Exception){
                Log.e("ViewModel", "Error adding address: ${e.message}")
            }
        }
    }
}