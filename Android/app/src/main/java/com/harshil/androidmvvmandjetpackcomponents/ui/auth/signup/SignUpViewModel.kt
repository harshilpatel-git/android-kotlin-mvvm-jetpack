package com.harshil.androidmvvmandjetpackcomponents.ui.auth.signup

import android.view.View
import androidx.lifecycle.ViewModel
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository
import com.harshil.androidmvvmandjetpackcomponents.internal.APIException
import com.harshil.androidmvvmandjetpackcomponents.internal.Coroutine
import com.harshil.androidmvvmandjetpackcomponents.internal.NoConnectivityException

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    var name: String = ""
    var dob: String = ""
    var email: String = ""
    var password: String = ""

    private lateinit var signUpListener: SignUpListener

    fun getLoggedInUser() = authRepository.getUser()

    fun onSignUpButtonClicked(view: View) {
        if (name.isEmpty()) {
            signUpListener.onFailure("Enter valid Name.")
            return
        }
        if (email.isEmpty()) {
            signUpListener.onFailure("Enter valid Email.")
            return
        }
        if (password.isEmpty()) {
            signUpListener.onFailure("Enter Password.")
            return
        }

        Coroutine.main {
            try {
                val signUpResponse = authRepository.userSignUp(name, dob, email, password)
                signUpResponse.user?.let {
                    signUpListener.onSuccess(signUpResponse)
                    authRepository.saveUser(signUpResponse.user)
                    return@main
                }
                signUpListener.onFailure(signUpResponse.message)
            } catch (e: APIException) {
                signUpListener.onFailure(e.message.toString())
            } catch (e: NoConnectivityException) {
                signUpListener.onFailure(e.message.toString())
            }
        }
    }

    fun onBackToLoginButtonClicked(view: View) {

    }
}
