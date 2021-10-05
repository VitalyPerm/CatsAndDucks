package com.vitaly.catsandducks.model.cat

import retrofit2.Response
import retrofit2.http.GET

interface CatApi {
    @GET("rest")
    suspend fun getCat(): Response<Cat>
}