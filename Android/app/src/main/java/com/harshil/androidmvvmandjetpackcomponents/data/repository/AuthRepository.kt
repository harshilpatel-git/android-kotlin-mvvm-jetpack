package com.harshil.androidmvvmandjetpackcomponents.data.repository

import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User
import com.harshil.androidmvvmandjetpackcomponents.data.network.AppApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.SafeApiRequest
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.LoginResponse
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.SignUpResponse

class AuthRepository(
    private val appApi: AppApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(username: String, password: String): LoginResponse {
        return apiRequest { appApi.userLogin(username, password) }
    }

    suspend fun userSignUp(
        name: String,
        dob: String,
        email: String,
        password: String
    ): SignUpResponse {
        return apiRequest { appApi.userSignUp(name, dob, email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}