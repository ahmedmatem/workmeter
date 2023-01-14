package com.ahmedmatem.android.workmeter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar

@Entity
data class Worksheet(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "seal_num") val sealNum: Number,
    @ColumnInfo(name = "drawing_url") val drawingUrl: String? = null,
    val location: String,
    val size: String, // Size.parseSize("-3x-6").equals(new Size(-3, -6)) == true
    // TODO: Implement photos property
    // ...
    val date: String = SimpleDateFormat("dd-MM-yyyy EEE").format(Calendar.getInstance().time),
)