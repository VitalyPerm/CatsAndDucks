package com.vitaly.catsandducks.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vitaly.catsandducks.R
import com.vitaly.catsandducks.di.DaggerApiComponent
import com.vitaly.catsandducks.model.cat.Cat
import com.vitaly.catsandducks.model.cat.CatService
import com.vitaly.catsandducks.model.db.Dao
import com.vitaly.catsandducks.model.db.LikedDataBase
import com.vitaly.catsandducks.model.db.LikedPicture
import com.vitaly.catsandducks.model.duck.DuckService
import com.vitaly.catsandducks.utils.getProgressDrawable
import com.vitaly.catsandducks.utils.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    private lateinit var dao: Dao
    var likedPics: LiveData<List<LikedPicture>>? = null

    @Inject
    lateinit var catService: CatService

    @Inject
    lateinit var duckService: DuckService

    init {
        DaggerApiComponent.create().injectViewModel(this)
    }

    private var doubleClickLastTime = 0L
    val progressBar = getProgressDrawable(this.getApplication())
    var firstLaunch: Boolean = true
    val picture = MutableLiveData<String>()
    var savedPicUrl: String? = null

    fun loadCat() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = catService.getCat()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    picture.value = response.body()?.url
                }
            } else {
                withContext(Dispatchers.Main) {
                    showToast(getApplication(), response.errorBody().toString())
                }
            }
        }
    }

    // With coroutines
    fun loadDuck() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = duckService.getDuck()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    picture.value = response.body()?.url
                }
            } else {
                withContext(Dispatchers.Main) {
                    showToast(getApplication(), response.errorBody().toString())
                }
            }
        }
    }


    fun doubleTap() {
        if (System.currentTimeMillis() - doubleClickLastTime < 300) {
            doubleClickLastTime = 0
            Toast.makeText(getApplication(), R.string.pic_saved, Toast.LENGTH_SHORT).show()
            if (picture.value != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.insert(LikedPicture(url = picture.value!!))
                }
            }
        } else doubleClickLastTime = System.currentTimeMillis()
    }


    fun saveData() {
        val prefs = getApplication<Application>().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        prefs.edit().putString(URL, picture.value).apply()
        showToast(getApplication(), "Сохранено в SharedPreferences")
    }

    fun loadData() {
        val prefs = getApplication<Application>().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        savedPicUrl = prefs.getString(URL, "")
    }

    fun initDatabase() {
        dao = LikedDataBase.getDatabaseInstance(getApplication()).getDao()
        getAll()
    }

    private fun getAll() {
        likedPics = dao.getAll()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    companion object {
        const val SHARED_PREFS_KEY = "shared_prefs_key"
        const val URL = "url"
    }
}