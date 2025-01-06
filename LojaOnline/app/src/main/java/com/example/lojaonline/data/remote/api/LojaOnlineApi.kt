package com.example.lojaonline.data.remote.api


import com.example.lojaonline.data.remote.model.AddProductDto
import com.example.lojaonline.data.remote.model.AddressDto
import com.example.lojaonline.data.remote.model.ProductDto
import com.example.lojaonline.data.remote.model.UserAddDto
import com.example.lojaonline.data.remote.model.UserDto
import com.example.lojaonline.data.remote.model.UserLoginDto
import com.example.lojaonline.data.remote.model.UserLoginResponseDto
import com.example.lojaonline.domain.model.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


object RetrofitInstance{
    val api: LojaOnlineApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5207/api/") // .NET RESTful WebAPI
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LojaOnlineApi::class.java)
    }
}

interface LojaOnlineApi {
    @GET("user/getAllUser")
    suspend fun getUsers(): List<UserDto>

    @GET("user/getUserById/{id}")
    suspend fun getUserById(@Path("id") userId: Int): Response<UserDto>

    @POST("user/addUser")
    suspend fun addUser(@Body userAddDto: UserAddDto): Response<Unit>

    @POST("login/login")
    suspend fun loginUser(@Body userLoginDto: UserLoginDto): Response<UserLoginResponseDto>

    @POST("address/addAddressToUser/{id}")
    suspend fun addAddressToUser(
        @Path("id") userId: Int,
        @Body addAddressDto: AddressDto
    ): Response<Void>

    @GET("product/getAllProduct")
    suspend fun getAllProducts(): Response<List<ProductDto>>

    @POST("product/addProduct")
    suspend fun addProduct(@Body product: AddProductDto): Response<ProductDto>
}