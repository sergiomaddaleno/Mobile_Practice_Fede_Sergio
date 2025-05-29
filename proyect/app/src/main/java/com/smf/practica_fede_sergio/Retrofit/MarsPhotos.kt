package com.smf.practica_fede_sergio.Retrofit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarsPhotos(
    val id: Int,
    @SerialName("image") val imgSrc: String?
)

@Serializable
data class RickAndMortyResponse(
    val results: List<MarsPhotos>
)