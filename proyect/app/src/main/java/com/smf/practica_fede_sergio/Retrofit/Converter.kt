package com.smf.practica_fede_sergio.Retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private val json = Json {
    ignoreUnknownKeys = true
}

private const val BASE_URL = "https://rickandmortyapi.com/api/"

val retrofitApi = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(
        json.asConverterFactory("application/json; charset=UTF-8".toMediaType())
    )
    .build()
