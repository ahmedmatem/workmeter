package com.ahmedmatem.android.workmeter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Worksheet(
    @PrimaryKey val id: String,
    val date: String,
    @ColumnInfo(name = "seal_num") val sealNum: Number,
    @ColumnInfo(name = "drawing_url") val drawingUrl: String,
    val location: String,
    val size: String, // Size.parseSize("-3x-6").equals(new Size(-3, -6)) == true
    // TODO: Implement photos property
)


