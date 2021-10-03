package com.vitaly.catsandducks.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vitaly.catsandducks.R
import com.vitaly.catsandducks.di.DaggerApiComponent
import com.vitaly.catsandducks.model.cat.Cat
import com.vitaly.catsandducks.model.cat.CatService
import com.vitaly.catsandducks.model.duck.Duck
import com.vitaly.catsandducks.model.duck.DuckService
import com.vitaly.catsandducks.utils.getProgressDrawable
import com.vitaly.catsandducks.utils.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()

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
        disposable.add(
            catService.getCat()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Cat>() {
                    override fun onSuccess(t: Cat) {
                        picture.value = t.url
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun loadDuck() {
        disposable.add(
            duckService.getDuck()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Duck>() {
                    override fun onSuccess(t: Duck) {
                        picture.value = t.url
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun doubleTap() {
        if (System.currentTimeMillis() - doubleClickLastTime < 300) {
            doubleClickLastTime = 0
            Toast.makeText(getApplication(), R.string.pic_saved, Toast.LENGTH_SHORT).show()
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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    companion object {
        const val SHARED_PREFS_KEY = "shared_prefs_key"
        const val URL = "url"
    }

}