package com.vitaly.catsandducks.data

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import java.io.Serializable

interface Service {
    @GET("random")
    fun getDuck(): Observable<DuckResponse>

    @GET("rest")
    fun getCat(): Observable<CatResponse>
}
