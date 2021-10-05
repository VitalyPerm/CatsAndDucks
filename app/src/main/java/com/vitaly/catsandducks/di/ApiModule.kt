package com.vitaly.catsandducks.di

import com.vitaly.catsandducks.model.cat.CatApi
import com.vitaly.catsandducks.model.cat.CatService
import com.vitaly.catsandducks.model.duck.DuckApi
import com.vitaly.catsandducks.model.duck.DuckService
import com.vitaly.catsandducks.utils.CAT_BASE_URL
import com.vitaly.catsandducks.utils.DUCK_BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @Provides
    fun provideDuckApi(): DuckApi {
        return Retrofit.Builder()
            .baseUrl(DUCK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DuckApi::class.java)
    }

    @Provides
    fun provideCatApi(): CatApi {
        return Retrofit.Builder()
            .baseUrl(CAT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    @Provides
    fun provideCatService(): CatService {
        return CatService()
    }

    @Provides
    fun provideDuckService(): DuckService {
        return DuckService()
    }
}