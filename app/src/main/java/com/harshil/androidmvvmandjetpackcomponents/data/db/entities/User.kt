package com.harshil.androidmvvmandjetpackcomponents.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity(tableName = "user")
data class User(
    val name: String,
    val dob: String,
    val username: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_USER_ID
}