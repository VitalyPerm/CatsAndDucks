package com.vitaly.catsandducks.model.duck

import io.reactivex.Single
import retrofit2.http.GET

interface DuckApi {
    @GET("random")
    fun getDuck(): Single<Duck>
}