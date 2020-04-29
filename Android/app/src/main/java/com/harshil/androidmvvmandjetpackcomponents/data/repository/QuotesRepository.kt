package com.harshil.androidmvvmandjetpackcomponents.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harshil.androidmvvmandjetpackcomponents.data.db.AppDatabase
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.Quote
import com.harshil.androidmvvmandjetpackcomponents.data.network.AppApi
import com.harshil.androidmvvmandjetpackcomponents.data.network.SafeApiRequest
import com.harshil.androidmvvmandjetpackcomponents.data.preferences.PreferenceProvider
import com.harshil.androidmvvmandjetpackcomponents.internal.Coroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class QuotesRepository(
    private val appApi: AppApi,
    private val db: AppDatabase,
    private val preferenceProvider: PreferenceProvider
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()
    private val MIN_INTERVAL = 2

    init {
        quotes.observeForever {
            preferenceProvider.lastFetchedQuotesTime = LocalDateTime.now().toString()
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
        val lastFetchedAt = preferenceProvider.lastFetchedQuotesTime
        if (lastFetchedAt == null || isFetchNeeded(LocalDateTime.parse(lastFetchedAt))) {
            val quotesResponse = apiRequest { appApi.getQuotes() }
            quotes.postValue(quotesResponse.quotes)
        }
    }

    private fun isFetchNeeded(lastFetchedAt: LocalDateTime): Boolean {
        // If last fetched was before 2 hours
        return ChronoUnit.HOURS.between(lastFetchedAt, LocalDateTime.now()) > MIN_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutine.io {
            db.getQuoteDao().saveQuotes(quotes)
        }
    }

}