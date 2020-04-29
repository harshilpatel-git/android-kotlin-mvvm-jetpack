package com.harshil.androidmvvmandjetpackcomponents.ui.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harshil.androidmvvmandjetpackcomponents.data.repository.QuotesRepository

// View model factory class are used to give dependencies to the View model as we
// can not directly instantiate view model
@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val quotesRepository: QuotesRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(quotesRepository) as T
    }
}