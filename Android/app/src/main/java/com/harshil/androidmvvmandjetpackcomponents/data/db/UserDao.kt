package com.harshil.androidmvvmandjetpackcomponents.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.CURRENT_USER_ID
import com.harshil.androidmvvmandjetpackcomponents.data.db.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: User): Long

    @Query("select * from user where id = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>
}