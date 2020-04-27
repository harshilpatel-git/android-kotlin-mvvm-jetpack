package com.harshil.androidmvvmandjetpackcomponents.data.repository

import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User
import com.harshil.androidmvvmandjetpackcomponents.data.network.AuthApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.SafeApiRequest
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.LoginResponse

class AuthRepository(
    private val authApi: AuthApi,
    private val db: AppDatabase
) : SafeApiRequest() {
    suspend fun userLogin(username: String, password: String): LoginResponse {
        return apiRequest { authApi.userLogin(username, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)
}