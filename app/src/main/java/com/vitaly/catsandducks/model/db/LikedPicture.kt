package com.vitaly.catsandducks.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "table")
data class LikedPicture(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val url: String
) : Serializable