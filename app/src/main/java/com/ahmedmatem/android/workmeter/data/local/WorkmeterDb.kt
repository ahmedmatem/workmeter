package com.ahmedmatem.android.workmeter.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmedmatem.android.workmeter.data.model.User
import com.ahmedmatem.android.workmeter.data.local.login.UserDao
import com.ahmedmatem.android.workmeter.data.model.Site
import com.ahmedmatem.android.workmeter.data.local.site.SiteDao

@Database(
    entities = [User::class, Site::class],
    version = 1
)
abstract class WorkmeterDb: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val siteDao: SiteDao

    companion object {
        @Volatile
        private var instance: WorkmeterDb? = null

        fun getInstance(context: Context): WorkmeterDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): WorkmeterDb {
            return Room.databaseBuilder(context, WorkmeterDb::class.java, "workmeter-db")
                .build()
        }
    }
}