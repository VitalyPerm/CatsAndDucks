package com.vitaly.catsandducks.model.duck

import com.vitaly.catsandducks.di.DaggerApiComponent
import retrofit2.Response
import javax.inject.Inject

class DuckService {

    @Inject
    lateinit var duckApi: DuckApi

    init {
        DaggerApiComponent.create().injectDuckService(this)
    }

    suspend fun getDuck(): Response<Duck> {
        return duckApi.getDuck()
    }
}