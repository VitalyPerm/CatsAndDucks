package com.vitaly.catsandducks.model.db

class LikedRepository(private val dao: Dao) {
    val allPics = dao.getAll()

    suspend fun insert(likedPicture: LikedPicture) {
        dao.insert(likedPicture)
    }

    suspend fun delete(likedPicture: LikedPicture) {
        dao.delete(likedPicture)
    }

}