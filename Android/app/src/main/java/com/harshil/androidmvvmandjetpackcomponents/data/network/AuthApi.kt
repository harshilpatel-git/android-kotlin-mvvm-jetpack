package com.harshil.androidmvvmandjetpackcomponents.data.network

import com.harshil.androidmvvmandjetpackcomponents.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    companion object {
        operator fun invoke(): AuthApi {
            return Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/course-api/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthApi::class.java)
        }
    }
}