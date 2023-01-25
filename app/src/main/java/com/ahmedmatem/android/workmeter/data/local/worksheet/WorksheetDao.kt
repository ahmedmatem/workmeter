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
    fun getById(id: String): Worksheet

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(worksheet: Worksheet)

    /**
     * Site specific queries
     */
    @Query("SELECT COUNT() FROM worksheet WHERE site_id = :siteId")
    fun count(siteId: String) : Int

    @Query("SELECT * FROM worksheet WHERE site_id = :siteId AND isComplete = 0 ORDER BY seal_num")
    fun allIncomplete(siteId: String) : Flow<List<Worksheet>>
}