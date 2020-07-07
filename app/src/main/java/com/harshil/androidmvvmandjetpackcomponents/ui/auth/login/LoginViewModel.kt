package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User
import com.harshil.androidmvvmandjetpackcomponents.data.network.response.LoginResponse
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _loginResponse = MutableLiveData<LoginResponse>()

    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

    fun getLoggedInUser() = authRepository.getUser()

    suspend fun userLogin(username: String, password: String) =
        // To make network calls on IO thread and not on Main thread
        withContext(Dispatchers.IO) {
            authRepository.userLogin(username, password)
        }

    suspend fun saveUser(user: User) = authRepository.saveUser(user)
}
