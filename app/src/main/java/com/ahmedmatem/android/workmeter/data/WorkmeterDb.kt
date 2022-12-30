package com.ahmedmatem.android.workmeter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmedmatem.android.workmeter.data.login.local.UserDao

@Database(
    entities = [UserDao::class],
    version = 1
)
abstract class WorkmeterDb: RoomDatabase() {
    abstract val userDao: UserDao

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