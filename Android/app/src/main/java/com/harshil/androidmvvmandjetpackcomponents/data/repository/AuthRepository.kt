package com.harshil.androidmvvmandjetpackcomponents.data.repository

import com.harshil.androidmvvmandjetpackcomponents.data.network.AuthApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.SafeApiRequest
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.LoginResponse

class AuthRepository : SafeApiRequest() {
    suspend fun userLogin(username: String, password: String): LoginResponse {
        return apiRequest { AuthApi().userLogin(username, password) }
    }
}