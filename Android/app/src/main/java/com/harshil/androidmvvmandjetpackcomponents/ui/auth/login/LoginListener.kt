package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import androidx.lifecycle.LiveData

interface LoginListener {
    fun onLoginStart()
    fun onSuccess(loginResponse: LiveData<String>)
    fun onFailure(reason: String)
}