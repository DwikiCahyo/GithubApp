package com.dwiki.githubapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)

abstract class DatabaseUser:RoomDatabase() {
    companion object {
        private var INSTANCE : DatabaseUser? = null
        fun getDatabase(context: Context): DatabaseUser?{
            if(INSTANCE == null) {
                synchronized(DatabaseUser::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,DatabaseUser::class.java, "db_user").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteDao(): FavoriteDAO
}