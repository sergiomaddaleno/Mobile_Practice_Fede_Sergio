package com.smf.practica_fede_sergio.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smf.practica_fede_sergio.R
import com.smf.practica_fede_sergio.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(userViewModel: UserViewModel = viewModel(), onLogin: () -> Unit) {
    // Variable para capturar el texto ingresado
    var nameInput = remember { mutableStateOf(TextFieldValue("")) }

    // Recuperamos el nombre desde el ViewModel
    val currentName = userViewModel.name.value

    // Pantalla de registro
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = stringResource(R.string.Registro),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Subtítulo con nombre guardado
        if (currentName.isNotEmpty()) {
            Text(
                text = "Nombre guardado: $currentName",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Campo de texto para ingresar el nombre
        TextField(
            value = nameInput.value,
            onValueChange = { nameInput.value = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Botón para guardar el nombre y navegar
        Button(
            onClick = {
                // Guardamos el nombre en el ViewModel
                userViewModel.updateName(nameInput.value.text)

                // Llamamos al callback para realizar la navegación
                onLogin()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }
}


