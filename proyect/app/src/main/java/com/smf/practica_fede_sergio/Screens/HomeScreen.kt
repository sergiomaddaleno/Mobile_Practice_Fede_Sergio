package com.smf.practica_fede_sergio.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smf.practica_fede_sergio.ViewModel.UserViewModel

@Composable
fun HomeScreen(userViewModel: UserViewModel) {
    // Recuperamos el nombre guardado desde el ViewModel
    val currentName = userViewModel.name.value

    // Mostrar un saludo con el nombre guardado
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hola, $currentName!", // Muestra el nombre guardado
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}
