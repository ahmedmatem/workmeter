package com.ahmedmatem.android.workmeter.data.login.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ahmedmatem.android.workmeter.data.model.User
import com.ahmedmatem.android.workmeter.data.model.UserWithSites

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username LIKE :username and password LIKE :password LIMIT 1")
    suspend fun getBy(username: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg users: User)

    @Delete
    fun delete(vararg users: User)

    @Transaction
    @Query("SELECT * fROM users")
    fun getUserWithSites(): List<UserWithSites>
}