package com.vitaly.catsandducks.data

import java.io.Serializable

data class DuckResponse(
    val message: String,
    val url: String
) : Serializable

data class CatResponse(
    val id: String,
    val url: String,
    val webpurl: String,
    val x: Float,
    val y: Float
) : Serializable