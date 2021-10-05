package com.vitaly.catsandducks.model.cat

import com.vitaly.catsandducks.di.DaggerApiComponent
import retrofit2.Response
import javax.inject.Inject

class CatService {

    @Inject
    lateinit var catApi: CatApi

    init {
        DaggerApiComponent.create().injectCatService(this)
    }

    suspend fun getCat(): Response<Cat> {
        return catApi.getCat()
    }
}