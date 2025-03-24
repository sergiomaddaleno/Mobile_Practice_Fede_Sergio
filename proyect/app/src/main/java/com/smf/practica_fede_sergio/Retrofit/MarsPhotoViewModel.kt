package com.smf.practica_fede_sergio.Retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarsPhotoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MarsPhotosUiState())
    var uiState: StateFlow<MarsPhotosUiState> = _uiState

    init { getMarsPhotos()}

    private fun getMarsPhotos() {
       viewModelScope.launch {
           val marsPhotos = MarsApi.marsApiService.getPhotos()
           _uiState.update { currentState ->
               currentState.copy(marsPhotos = marsPhotos)
           }
       }
    }
}