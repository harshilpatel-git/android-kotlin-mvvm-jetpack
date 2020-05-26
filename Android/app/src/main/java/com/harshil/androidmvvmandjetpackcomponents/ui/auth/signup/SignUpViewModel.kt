package com.harshil.androidmvvmandjetpackcomponents.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun getLoggedInUser() = authRepository.getUser()

    suspend fun signUpUser(name: String, dob: String, email: String, password: String) =
        // To make network calls on IO thread and not on Main thread
        withContext(Dispatchers.IO) {
            authRepository.userSignUp(name, dob, email, password)
        }

    suspend fun saveUser(user: User) = authRepository.saveUser(user)
}
