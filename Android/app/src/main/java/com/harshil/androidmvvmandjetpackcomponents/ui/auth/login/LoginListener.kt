package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User

interface LoginListener {
    fun onLoginStart()
    fun onSuccess(loginResponse: User?)
    fun onFailure(reason: String)
}