package com.ahmedmatem.android.workmeter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar

@Entity
data class Worksheet(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "site_id") val siteId: String,
    val date: String = SimpleDateFormat("dd-MM-yyyy EEE").format(Calendar.getInstance().time),
    // TODO: write logic for sealNum generation
    @ColumnInfo(name = "seal_num") val sealNum: Int,
    val location: String,
    val size: String = "0x0", // Size.parseSize("-3x-6").equals(new Size(-3, -6)) == true
    @ColumnInfo(name = "drawing_url") val drawingUrl: String? = null,
    // TODO: Implement photos property
    // ...
)