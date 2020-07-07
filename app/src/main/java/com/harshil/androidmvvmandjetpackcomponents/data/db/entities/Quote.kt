package com.harshil.androidmvvmandjetpackcomponents.data.db.entities

import androidx.annotation.VisibleForTesting
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val quote: String
)