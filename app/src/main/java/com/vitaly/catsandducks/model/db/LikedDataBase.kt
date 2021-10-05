package com.vitaly.catsandducks.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LikedPicture::class], version = 1)
abstract class LikedDataBase : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {

        private var db_instance: LikedDataBase? = null

        @Synchronized
        fun getDatabaseInstance(context: Context): LikedDataBase {
            if (db_instance == null) {
               db_instance = Room.databaseBuilder(
                   context.applicationContext, LikedDataBase::class.java, "db"
               ).build()
            }
            return db_instance as LikedDataBase
        }
    }
}