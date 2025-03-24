package com.smf.practica_fede_sergio.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.smf.practica_fede_sergio.DataSource.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val dataSource: DataSource) : ViewModel() {


    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState

    init {

        viewModelScope.launch {
            dataSource.getUserEmail.collect { email ->
                _uiState.value = _uiState.value.copy(email = email)
            }
        }
    }


    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }


    fun saveEmail(email: String) {
        viewModelScope.launch {
            dataSource.saveEmailPreference(email)
            _uiState.value = _uiState.value.copy(email = email)
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


data class UserUiState(
    val name: String = "",
    val email: String = ""
)
