package com.vitaly.catsandducks.model.cat

import io.reactivex.Single
import retrofit2.http.GET

interface CatApi {
    @GET("rest")
    fun getCat(): Single<Cat>
}