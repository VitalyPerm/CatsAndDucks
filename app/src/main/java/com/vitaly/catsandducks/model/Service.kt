package com.vitaly.catsandducks.model

import com.vitaly.catsandducks.data.CatResponse
import com.vitaly.catsandducks.data.DuckResponse
import io.reactivex.Single
import retrofit2.http.GET

interface Service {
    @GET("random")
    fun getDuck(): Single<DuckResponse>

    @GET("rest")
    fun getCat(): Single<CatResponse>
}
