package com.smf.practica_fede_sergio.Retrofit

import retrofit2.Response
import retrofit2.http.GET

interface MarsApiService {
    @GET("character")
    suspend fun getPhotos(): Response<RickAndMortyResponse>
}