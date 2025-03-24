package com.smf.practica_fede_sergio.Retrofit

import retrofit2.Response
import retrofit2.http.GET

interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos() : Response<List<MarsPhotos>>
}