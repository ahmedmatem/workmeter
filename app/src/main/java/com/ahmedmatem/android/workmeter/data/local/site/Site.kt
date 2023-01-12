package com.ahmedmatem.android.workmeter.data.local.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentSnapshot

@Entity
data class Site(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "post_code") val postCode: String?
)
