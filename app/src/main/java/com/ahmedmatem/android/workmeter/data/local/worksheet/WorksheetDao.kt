package com.ahmedmatem.android.workmeter.data.local.worksheet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorksheetDao {
    @Query("SELECT * FROM worksheet WHERE id = :id")
    fun getById(id: String): Flow<Worksheet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(worksheet: Worksheet)
}