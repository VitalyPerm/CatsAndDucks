package com.vitaly.catsandducks.data

import retrofit2.Call
import retrofit2.http.GET
import java.io.Serializable

interface Service {
    @GET("random")
    fun getDuck(): Call<DuckResponse>

    @GET("rest")
    fun getCat(): Call<CatResponse>
}
