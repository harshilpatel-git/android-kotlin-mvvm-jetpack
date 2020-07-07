package com.harshil.androidmvvmandjetpackcomponents.data.network.response

import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User

data class LoginResponse(
    val isSuccessful: Boolean,
    val message: String,
    val user: User?
)