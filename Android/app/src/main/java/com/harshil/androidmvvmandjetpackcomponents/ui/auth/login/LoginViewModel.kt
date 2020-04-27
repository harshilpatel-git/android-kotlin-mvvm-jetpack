package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository

class LoginViewModel : ViewModel() {

    var username: String = ""
    var password: String = ""

    lateinit var loginListener: LoginListener

    fun onLoginButtonClicked(view: View) {
        loginListener.onLoginStart()
        if (username.isEmpty()) {
            loginListener.onFailure("Enter valid Username.")
            return
        }
        if (password.isEmpty()) {
            loginListener.onFailure("Enter valid Password.")
            return
        }
        val loginResponse = AuthRepository().userLogin(username, password)
        loginListener.onSuccess(loginResponse)
    }

    fun onSignUpButtonClicked(view: View) {

    }
}
