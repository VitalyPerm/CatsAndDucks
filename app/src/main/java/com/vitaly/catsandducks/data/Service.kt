package com.vitaly.catsandducks.data

import io.reactivex.Single
import retrofit2.http.GET

interface Service {
    @GET("random")
    fun getDuck(): Single<DuckResponse>

    @GET("rest")
    fun getCat(): Single<CatResponse>
}
