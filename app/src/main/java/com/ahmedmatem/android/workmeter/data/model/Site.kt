package com.ahmedmatem.android.workmeter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sites")
data class Site(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "worker_id") val workerId: String,
    val name: String,
    @ColumnInfo(name = "post_code") val postCode: String?
)
