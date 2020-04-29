package com.harshil.androidmvvmandjetpackcomponents.ui.auth.signup

import androidx.annotation.VisibleForTesting
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.SignUpResponse

interface SignUpListener{
    fun onStart()
    fun onSuccess(signUpResponse: SignUpResponse)
    fun onFailure(reason:String)
}