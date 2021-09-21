package com.vitaly.catsandducks.presentation

import android.app.Application
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
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
    lateinit var compositeDisposable: CompositeDisposable
    private var doubleClickLastTime = 0L
    fun loadCat(image: ImageView, baseUrl: String) {
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            createService(baseUrl).getCat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { response -> onResponse(response.url, image) })
    }

    fun loadDuck(image: ImageView, baseUrl: String) {
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            createService(baseUrl).getDuck()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { response -> onResponse(response.url, image) })
    }
    fun doubleTap(){
        if (System.currentTimeMillis() - doubleClickLastTime < 300) {
            doubleClickLastTime = 0
            Toast.makeText(getApplication(), R.string.pic_saved, Toast.LENGTH_SHORT).show()
        } else doubleClickLastTime = System.currentTimeMillis()
    }

    private fun onResponse(url: String, image: ImageView) {
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
}