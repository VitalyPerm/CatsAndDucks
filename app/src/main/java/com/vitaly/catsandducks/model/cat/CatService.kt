package com.vitaly.catsandducks.model.cat

import com.vitaly.catsandducks.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CatService {

    @Inject
    lateinit var catApi: CatApi

    init {
        DaggerApiComponent.create().injectCatService(this)
    }

    fun getCat(): Single<Cat> {
        return catApi.getCat()
    }
}