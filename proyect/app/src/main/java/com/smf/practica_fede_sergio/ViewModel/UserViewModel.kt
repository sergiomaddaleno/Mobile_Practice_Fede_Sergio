package com.smf.practica_fede_sergio.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    var name = mutableStateOf("")


    fun updateName(newName: String) {
        name.value = newName
    }
}
