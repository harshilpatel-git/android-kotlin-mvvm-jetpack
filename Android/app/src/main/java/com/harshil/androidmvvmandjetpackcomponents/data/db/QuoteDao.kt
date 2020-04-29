package com.harshil.androidmvvmandjetpackcomponents.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveQuotes(quotes: List<Quote>)

    @Query("select * from quotes")
    fun getQuotes(): LiveData<List<Quote>>
}