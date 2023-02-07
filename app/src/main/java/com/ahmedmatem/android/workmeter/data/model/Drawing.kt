package com.ahmedmatem.android.workmeter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drawing")
data class Drawing(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "site_id") val siteId: String,
    val name: String,
    val show: Boolean
)
