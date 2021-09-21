package com.vitaly.catsandducks.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.vitaly.catsandducks.R
import com.vitaly.catsandducks.data.CatResponse
import com.vitaly.catsandducks.data.Constants
import com.vitaly.catsandducks.data.DuckResponse
import com.vitaly.catsandducks.data.Service
import com.vitaly.catsandducks.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var doubleClickLastTime = 0L
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnShowDuck.setOnClickListener {
            val listCall: Call<DuckResponse> = createService(Constants.DUCK_BASE_URL).getDuck()
            listCall.enqueue(object : Callback<DuckResponse> {
                override fun onResponse(
                    call: Call<DuckResponse>,
                    response: Response<DuckResponse>
                ) {
                    if (response.isSuccessful) {
                        val duckResponse: DuckResponse? = response.body()
                        if (duckResponse != null) {
                            Glide
                                .with(binding.ivPic)
                                .load(duckResponse.url)
                                .into(binding.ivPic)
                        }
                    }
                }
                override fun onFailure(call: Call<DuckResponse>, t: Throwable) {
                    Log.e("Error!", t.message.toString())
                }
            })
        }

        binding.btnShowCat.setOnClickListener {
            val listCall: Call<CatResponse> = createService(Constants.CAT_BASE_URL).getCat()
            listCall.enqueue(object : Callback<CatResponse> {
                override fun onResponse(
                    call: Call<CatResponse>,
                    response: Response<CatResponse>
                ) {
                    if (response.isSuccessful) {
                        val catResponse: CatResponse? = response.body()
                        if (catResponse != null) {
                            Glide
                                .with(binding.ivPic)
                                .load(catResponse.url)
                                .into(binding.ivPic)
                        }
                    }
                }
                override fun onFailure(call: Call<CatResponse>, t: Throwable) {
                    Log.e("Error!", t.message.toString())
                }
            })
        }
        binding.ivPic.setOnClickListener {
            if(System.currentTimeMillis() - doubleClickLastTime < 300){
                doubleClickLastTime = 0
                Toast.makeText(this, getString(R.string.pic_saved), Toast.LENGTH_SHORT).show()
            }else doubleClickLastTime = System.currentTimeMillis()
        }
    }

    private fun createService(baseUrl: String): Service {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(Service::class.java)
    }
}