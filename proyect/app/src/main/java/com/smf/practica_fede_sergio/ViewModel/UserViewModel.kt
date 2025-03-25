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
    val isDarkMode: Boolean = true,
    val isEnglish: Boolean = false
)

class UserViewModel(private val dataSource: DataSource) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState

    init {
        viewModelScope.launch {
            launch {
                dataSource.getUserEmail.collect { email ->
                    _uiState.value = _uiState.value.copy(email = email)
                }
            }
            launch {
                dataSource.getDarkModeStatus.collect { isDarkMode ->
                    _uiState.value = _uiState.value.copy(isDarkMode = isDarkMode)
                }
            }
            launch {
                dataSource.getLanguageStatus.collect { isEnglish ->
                    _uiState.value = _uiState.value.copy(isEnglish = isEnglish)
                }
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
            dataSource.saveDarkModePreference(isDarkMode)
            _uiState.value = _uiState.value.copy(isDarkMode = isDarkMode)
        }
    }

    // Método para guardar el estado del idioma
    fun saveLanguage(isEnglish: Boolean) {
        viewModelScope.launch {
            dataSource.saveLanguagePreference(isEnglish)
            _uiState.value = _uiState.value.copy(isEnglish = isEnglish)
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

