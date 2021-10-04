package com.vitaly.catsandducks.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LikedPicture::class], version = 1)
abstract class LikedDataBase : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        @Volatile
        private var database: LikedDataBase? = null
    }

    @Synchronized
    fun getInstance(context: Context): LikedDataBase {
        if (database == null) {
            database = Room.databaseBuilder(
                context,
                LikedDataBase::class.java,
                "db"
            ).build()
            return database as LikedDataBase
        } else return database as LikedDataBase
    }
}