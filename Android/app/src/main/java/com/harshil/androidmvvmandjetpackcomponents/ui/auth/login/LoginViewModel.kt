package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository
import com.harshil.androidmvvmandjetpackcomponents.internal.APIException
import com.harshil.androidmvvmandjetpackcomponents.internal.Coroutine

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var username: String = ""
    var password: String = ""

    lateinit var loginListener: LoginListener

    fun getLoggedInUser() = authRepository.getUser()

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
        Coroutine.main {
            try {
                val loginResponse = authRepository.userLogin(username, password)
                loginResponse.user?.let {
                    loginListener.onSuccess(loginResponse.user)
                    authRepository.saveUser(loginResponse.user)
                    return@main
                }
                loginListener.onFailure(loginResponse.message)
            } catch (e: APIException) {
                loginListener.onFailure(e.message!!)
            }

        }
    }

    fun onSignUpButtonClicked(view: View) {

    }
}
