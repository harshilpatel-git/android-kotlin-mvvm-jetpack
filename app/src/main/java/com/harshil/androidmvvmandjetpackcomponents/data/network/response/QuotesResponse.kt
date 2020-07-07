package com.harshil.androidmvvmandjetpackcomponents.data.network.response

import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)