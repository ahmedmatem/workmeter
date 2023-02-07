package com.ahmedmatem.android.workmeter.data.local.drawing

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmedmatem.android.workmeter.data.model.Drawing

@Dao
interface DrawingDao {

    @Query("SELECT * FROM drawing WHERE site_id == :siteId")
    fun getSiteDrawings(siteId: String) : List<Drawing>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg drawings: Drawing)
}