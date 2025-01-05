package com.example.lojaonline.data.remote.api


import com.example.lojaonline.data.remote.model.UserAddDto
import com.example.lojaonline.data.remote.model.UserDto
import com.example.lojaonline.data.remote.model.UserLoginDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST



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

    @POST("user/addUser")
    suspend fun addUser(@Body userAddDto: UserAddDto): Response<Unit>

    @POST("login/login")
    suspend fun loginUser(@Body userLoginDto: UserLoginDto): Response<Unit>
}