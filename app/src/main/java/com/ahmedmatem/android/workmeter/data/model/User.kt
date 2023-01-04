package com.ahmedmatem.android.workmeter.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "users")
data class User(
    @PrimaryKey val uid: String,
    val username: String,
    val password: String
)

/**
 * Define one-to-many relationships between User and Site
 */

data class UserWithSites(
    @Embedded val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "workerId")
    val sites: List<Site>
)