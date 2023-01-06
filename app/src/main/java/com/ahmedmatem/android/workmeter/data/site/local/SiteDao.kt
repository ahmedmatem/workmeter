package com.ahmedmatem.android.workmeter.data.site.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SiteDao {
    @Query("SELECT * FROM site")
    fun allSites() : List<Site>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg sites: Site)

    @Delete
    fun delete(vararg sites: Site)

    @Query("SELECT * FROM site WHERE user_id = :userId")
    fun getUserSites(userId: String): List<Site>
}