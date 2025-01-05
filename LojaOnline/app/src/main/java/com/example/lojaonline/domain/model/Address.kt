package com.example.lojaonline.domain.model

import com.example.lojaonline.data.remote.model.AddressDto
import kotlin.concurrent.fixedRateTimer

data class Address(
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
    fun toAddressDto(): AddressDto{
        return AddressDto(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber, streetName = streetName, streetAdditional = streetAdditional, postalCode = postalCode, district = district, city = city, country = country, additionalNote = additionalNote)
    }
}