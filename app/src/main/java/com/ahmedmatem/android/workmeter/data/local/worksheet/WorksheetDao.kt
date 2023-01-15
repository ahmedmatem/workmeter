package com.ahmedmatem.android.workmeter.data.local.worksheet

import androidx.room.Dao
import androidx.room.Query
import com.ahmedmatem.android.workmeter.data.model.Worksheet

@Dao
interface WorksheetDao {
    @Query("SELECT * FROM worksheet WHERE id = :id")
    fun getById(id: String): Worksheet
}