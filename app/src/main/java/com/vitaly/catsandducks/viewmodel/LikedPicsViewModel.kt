package com.vitaly.catsandducks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vitaly.catsandducks.model.db.Dao
import com.vitaly.catsandducks.model.db.LikedDataBase
import com.vitaly.catsandducks.model.db.LikedPicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikedPicsViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var dao: Dao
    var likedPics: LiveData<List<LikedPicture>>? = null

    fun initDatabase() {
        dao = LikedDataBase.getInstance(getApplication()).getDao()
        getAll()
    }

    private fun getAll() {
        likedPics = dao.getAll()
    }

    fun delete(pic: LikedPicture){
        viewModelScope.launch(Dispatchers.IO){
            dao.delete(pic)
        }
    }
}