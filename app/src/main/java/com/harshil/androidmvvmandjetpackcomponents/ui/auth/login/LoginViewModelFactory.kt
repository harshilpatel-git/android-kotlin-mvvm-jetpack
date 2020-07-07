package com.harshil.androidmvvmandjetpackcomponents.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harshil.androidmvvmandjetpackcomponents.data.repository.AuthRepository

// View model factory class are used to give dependencies to the View model as we
// can not directly instantiate view model
@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(authRepository) as T
    }
}