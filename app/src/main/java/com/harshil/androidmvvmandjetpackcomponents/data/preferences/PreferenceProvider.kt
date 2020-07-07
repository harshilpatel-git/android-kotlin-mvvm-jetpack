package com.harshil.androidmvvmandjetpackcomponents.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val LAST_FETCHED_QUOTES_TIME = "LAST_FETCHED_QUOTES_TIME"

class PreferenceProvider(
    context: Context
) {
    private val applicationContext = context.applicationContext

    private val sharedPreferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(applicationContext)

    var lastFetchedQuotesTime
        set(value) = sharedPreferences.edit().putString(
            LAST_FETCHED_QUOTES_TIME,
            value
        ).apply()
        get() = sharedPreferences.getString(LAST_FETCHED_QUOTES_TIME, null)
}