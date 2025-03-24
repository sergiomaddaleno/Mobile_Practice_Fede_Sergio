package com.smf.practica_fede_sergio.Retrofit

sealed class MarsPhotosUiState {
    data class Success(val photos: List<MarsPhotos>) : MarsPhotosUiState()
    data class Error(val message: String? = null) : MarsPhotosUiState()
    object Loading : MarsPhotosUiState()
}
