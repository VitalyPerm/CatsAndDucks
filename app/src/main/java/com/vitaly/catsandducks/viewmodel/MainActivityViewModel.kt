package com.vitaly.catsandducks.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.vitaly.catsandducks.R
import com.vitaly.catsandducks.di.DaggerApiComponent
import com.vitaly.catsandducks.model.cat.Cat
import com.vitaly.catsandducks.model.cat.CatService
import com.vitaly.catsandducks.model.duck.Duck
import com.vitaly.catsandducks.model.duck.DuckService
import com.vitaly.catsandducks.utils.getProgressDrawable
import com.vitaly.catsandducks.utils.loadImage
import com.vitaly.catsandducks.utils.showToast
import com.vitaly.catsandducks.view.MainActivity
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
    var lastPicUrl: String? = null
    var savedPicUrl: String? = null
    fun loadCat(image: ImageView) {
        disposable.add(
            catService.getCat()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Cat>() {
                    override fun onSuccess(t: Cat) {
                        image.loadImage(t.url, progressBar)
                        lastPicUrl = t.url
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun loadDuck(image: ImageView) {
        disposable.add(
            duckService.getDuck()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Duck>() {
                    override fun onSuccess(t: Duck) {
                        image.loadImage(t.url, progressBar)
                        lastPicUrl = t.url
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

    fun loadPic(url: String, image: ImageView) {
        Glide
            .with(image)
            .load(url)
            .into(image)
    }

    fun saveData() {
        val prefs = getApplication<Application>().getSharedPreferences(MainActivity.SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        prefs.edit().putString(MainActivity.URL, lastPicUrl).apply()
        showToast(getApplication(), "Сохранено в SharedPreferences")
    }

    fun loadData() {
        val prefs = getApplication<Application>().getSharedPreferences(MainActivity.SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        savedPicUrl = prefs.getString(MainActivity.URL, "Нет сохраненных данных")
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}