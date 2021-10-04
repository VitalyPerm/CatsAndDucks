package com.vitaly.catsandducks.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Query("SELECT * FROM `table`")
    fun getAll(): LiveData<List<LikedPicture>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pic: LikedPicture)

    @Delete
    suspend fun delete(pic: LikedPicture)
}