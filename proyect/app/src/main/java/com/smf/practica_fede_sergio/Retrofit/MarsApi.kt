package com.smf.practica_fede_sergio.Retrofit

object MarsApi {
    val marsApiService : MarsApiService by lazy{
        retrofitApi.create(MarsApiService::class.java)
    }
}