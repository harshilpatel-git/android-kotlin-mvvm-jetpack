package com.harshil.androidmvvmandjetpackcomponents.data.network

import com.harshil.androidmvvmandjetpackcomponents.data.network.response.LoginResponse
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.QuotesResponse
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.SignUpResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AppApi {

    // https://api.simplifiedcoding.in/course-api/mvvm/login
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignUp(
        @Field("name") name: String,
        @Field("dob") dob: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<SignUpResponse>

    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object {
        operator fun invoke(connectionInterceptor: NetworkConnectionInterceptor): AppApi {
            // If there are any extra parameter in url to be added, we can add them here
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
//                    .addQueryParameter("", "")
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.simplifiedcoding.in/course-api/mvvm/")
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AppApi::class.java)
        }
    }
}