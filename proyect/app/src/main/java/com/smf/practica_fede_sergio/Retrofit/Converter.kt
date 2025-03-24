package com.smf.practica_fede_sergio.Retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val MARS_API_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

val retrofitApi = Retrofit.Builder()
    .baseUrl(MARS_API_URL)
    .addConverterFactory(
        Json.asConverterFactory("application/json; charset=UTF-8".toMediaType())
    )
    .build()
