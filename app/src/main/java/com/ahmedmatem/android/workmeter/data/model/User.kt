package com.ahmedmatem.android.workmeter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    val username: String,
    val password: String
)