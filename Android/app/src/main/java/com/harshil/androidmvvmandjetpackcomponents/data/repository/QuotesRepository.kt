package com.harshil.androidmvvmandjetpackcomponents.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.Quote
import com.harshil.androidmvvmandjetpackcomponents.data.network.AppApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.SafeApiRequest
import com.harshil.androidmvvmandjetpackcomponents.internal.Coroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuotesRepository(
    private val appApi: AppApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {
        if (isFetchNeeded()) {
            val quotesResponse = apiRequest { appApi.getQuotes() }
            quotes.postValue(quotesResponse.quotes)
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutine.io {
            db.getQuoteDao().saveQuotes(quotes)
        }
    }

}