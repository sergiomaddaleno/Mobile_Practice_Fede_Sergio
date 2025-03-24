package com.smf.practica_fede_sergio.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smf.practica_fede_sergio.DataSource.MyTaskApplication

import com.smf.practica_fede_sergio.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    loginViewModel: UserViewModel,
    onLogin: () -> Unit
) {
    // Utilizamos remember para persistir el estado del TextField a través de las recomposiciones
    val uiState by loginViewModel.uiState.collectAsState()
    var emailInput by remember { mutableStateOf(TextFieldValue(uiState.email)) }
    var isError by remember { mutableStateOf(false) }

    // Efecto secundario para actualizar el valor del TextField cuando el email en el estado cambia
    LaunchedEffect(uiState.email) {
        emailInput = TextFieldValue(uiState.email)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Registro de Usuario",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (uiState.email.isNotEmpty()) {
            Text(
                text = "Email guardado: ${uiState.email}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        TextField(
            value = emailInput,
            onValueChange = {
                emailInput = it
                isError = it.text.isEmpty()
            },
            label = { Text("Email") },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        if (isError) {
            Text(
                text = "El email no puede estar vacío",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                if (emailInput.text.isNotEmpty()) {
                    loginViewModel.saveEmail(emailInput.text)
                    onLogin()
                } else {
                    isError = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = emailInput.text.isNotEmpty()
        ) {
            Text(text = "Login")
        }
    }
}
