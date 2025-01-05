package com.example.lojaonline.data.remote.model

import com.example.lojaonline.domain.model.Address


data class AddressDto(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val streetName: String,
    val streetAdditional: String?,
    val postalCode: String,
    val district: String,
    val city: String,
    val country: String,
    val additionalNote: String?,
){
    fun toAddress(): Address{
        return Address(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber, streetName = streetName, streetAdditional = streetAdditional, postalCode = postalCode, district = district, city = city, country = country, additionalNote = additionalNote)
    }
}