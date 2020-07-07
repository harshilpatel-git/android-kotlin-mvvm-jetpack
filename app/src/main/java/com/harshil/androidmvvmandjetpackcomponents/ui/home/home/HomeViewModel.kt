package com.harshil.androidmvvmandjetpackcomponents.ui.home.home

import androidx.lifecycle.ViewModel
import com.harshil.androidmvvmandjetpackcomponents.data.repository.QuotesRepository
import com.harshil.androidmvvmandjetpackcomponents.internal.lazyDeferred

class HomeViewModel(
    repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
}

}