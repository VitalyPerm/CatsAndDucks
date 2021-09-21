package com.vitaly.catsandducks.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.vitaly.catsandducks.R
import com.vitaly.catsandducks.data.Constants
import com.vitaly.catsandducks.data.Service
import com.vitaly.catsandducks.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var doubleClickLastTime = 0L
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnShowDuck.setOnClickListener {
            compositeDisposable = CompositeDisposable()
            compositeDisposable.add(
                createService(Constants.DUCK_BASE_URL).getDuck()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { response -> onResponse(response.url) })
        }

        binding.btnShowCat.setOnClickListener {
            compositeDisposable = CompositeDisposable()
            compositeDisposable.add(
                createService(Constants.CAT_BASE_URL).getCat()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { response -> onResponse(response.url) })
        }
        binding.ivPic.setOnClickListener {
            if (System.currentTimeMillis() - doubleClickLastTime < 300) {
                doubleClickLastTime = 0
                Toast.makeText(this, getString(R.string.pic_saved), Toast.LENGTH_SHORT).show()
            } else doubleClickLastTime = System.currentTimeMillis()
        }
    }

    private fun createService(baseUrl: String): Service {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(Service::class.java)
    }

    private fun onResponse(url: String) {
        Glide
            .with(binding.ivPic)
            .load(url)
            .into(binding.ivPic)
    }
}