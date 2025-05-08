package com.smf.practica_fede_sergio.Retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarsPhotoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MarsPhotosUiState>(MarsPhotosUiState.Loading)
    val uiState: StateFlow<MarsPhotosUiState> = _uiState

    init { getMarsPhotos() }

    private fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val response = MarsApi.marsApiService.getPhotos()

                if (response.isSuccessful) {
                    val marsPhotos = response.body()?.results ?: emptyList()
                    _uiState.value = MarsPhotosUiState.Success(marsPhotos)
                } else {
                    val errorMessage = "Error ${response.code()}: ${response.message()}"
                    _uiState.value = MarsPhotosUiState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = "An error occurred: ${e.message}"
                _uiState.value = MarsPhotosUiState.Error(errorMessage)
            }
        }
    }

}
