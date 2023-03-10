package com.ahmedmatem.android.workmeter.data.local.login

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmedmatem.android.workmeter.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username LIKE :username and password LIKE :password LIMIT 1")
    suspend fun getBy(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE uid LIKE :uid LIMIT 1")
    suspend fun getBy(uid: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg users: User)

    @Delete
    fun delete(vararg users: User)
}