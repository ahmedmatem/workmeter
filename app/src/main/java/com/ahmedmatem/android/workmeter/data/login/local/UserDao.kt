package com.ahmedmatem.android.workmeter.data.login.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("Select * from users where username like :username and password like :password limit 1")
    suspend fun getBy(username: String, password: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: User)

    @Delete
    fun delete(vararg users: User)
}