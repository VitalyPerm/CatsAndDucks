package com.vitaly.catsandducks.presentation

import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.vitaly.catsandducks.R
import com.vitaly.catsandducks.data.Service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var compositeDisposable: CompositeDisposable
    private var doubleClickLastTime = 0L
    var firstLaunch: Boolean = true
    var lastPicUrl: String? = null
    var savedPicUrl: String? = null
    fun loadCat(image: ImageView, baseUrl: String) {
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            createService(baseUrl).getCat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { response ->
                    loadPic(response.url, image)
                    lastPicUrl = response.url
                })
    }
    fun loadDuck(image: ImageView, baseUrl: String) {
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            createService(baseUrl).getDuck()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { response ->
                    loadPic(response.url, image)
                    lastPicUrl = response.url
                })
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

    private fun createService(baseUrl: String): Service {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(Service::class.java)
    }

     fun saveData() {
        val prefs = getApplication<Application>().getSharedPreferences(MainActivity.SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        prefs.edit().putString(MainActivity.URL, lastPicUrl).apply()
    }

     fun loadData() {
        val prefs =  getApplication<Application>().getSharedPreferences(MainActivity.SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        savedPicUrl = prefs.getString(MainActivity.URL, "Нет сохраненных данных")
    }
}