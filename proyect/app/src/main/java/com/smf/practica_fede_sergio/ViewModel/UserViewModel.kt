package com.smf.practica_fede_sergio.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.smf.practica_fede_sergio.DataSource.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserUiState(
    val name: String = "",
    val email: String = "",
    val isDarkMode: Boolean = true // Agregamos el estado del modo oscuro
)

class UserViewModel(private val dataSource: DataSource) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState

    init {
        // Cargar el correo electrónico y el estado de modo oscuro desde DataStore
        viewModelScope.launch {
            dataSource.getUserEmail.collect { email ->
                // Si el correo cambia, actualizamos el estado del ViewModel
                _uiState.value = _uiState.value.copy(email = email)
            }

            dataSource.getDarkModeStatus.collect { isDarkMode ->
                // Si el estado de modo oscuro cambia, actualizamos el estado del ViewModel
                _uiState.value = _uiState.value.copy(isDarkMode = isDarkMode)
            }
        }
    }

    // Método para actualizar el nombre
    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    // Método para guardar el correo electrónico
    fun saveEmail(email: String) {
        viewModelScope.launch {
            dataSource.saveEmailPreference(email)
            _uiState.value = _uiState.value.copy(email = email)
        }
    }

    // Método para guardar el estado del modo oscuro
    fun saveDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            dataSource.saveDarkModePreference(isDarkMode) // Guardamos el estado en DataStore
            _uiState.value = _uiState.value.copy(isDarkMode = isDarkMode) // Actualizamos el estado
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataSource.clearEmailPreference()
            _uiState.update { currentState ->
                currentState.copy(email = "")
            }
        }
    }

    companion object {
        fun factory(dataSource: DataSource): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return UserViewModel(dataSource) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}

