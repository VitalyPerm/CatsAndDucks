package com.vitaly.catsandducks.model.duck

import com.vitaly.catsandducks.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class DuckService {

    @Inject
    lateinit var duckApi: DuckApi

    init {
        DaggerApiComponent.create().injectDuckService(this)
    }

    fun getDuck(): Single<Duck> {
        return duckApi.getDuck()
    }
}