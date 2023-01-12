package com.ahmedmatem.android.workmeter.data.local.site

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SiteDao {
    @Query("SELECT * FROM site")
    fun getSites() : LiveData<List<Site>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg sites: Site)

    @Delete
    fun delete(vararg sites: Site)
}