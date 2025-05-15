package com.smf.practica_fede_sergio.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smf.practica_fede_sergio.R
import com.smf.practica_fede_sergio.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    loginViewModel: UserViewModel,
    onLogin: () -> Unit
) {
    val uiState by loginViewModel.uiState.collectAsState()
    var emailInput by remember { mutableStateOf(TextFieldValue(uiState.email)) }
    var passwordInput by remember { mutableStateOf(TextFieldValue("")) }  // Nuevo estado para la contraseña
    var isError by remember { mutableStateOf(false) }
    var emailErrorMessage by remember { mutableStateOf("") }
    var passwordErrorMessage by remember { mutableStateOf("") }  // Mensaje de error para la contraseña

    LaunchedEffect(uiState.email) {
        emailInput = TextFieldValue(uiState.email)
    }

    fun isValidEmail(email: String): Boolean {
        return email.endsWith("@gmail.com")
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty()  // Validar que la contraseña no esté vacía
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center  // Centra todo el contenido
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,  // Centra horizontalmente
            verticalArrangement = Arrangement.Center  // Centra verticalmente
        ) {
            // Imagen de Call of Duty
            Image(
                painter = painterResource(id = R.drawable.cod_logo),
                contentDescription = "Call of Duty Logo",
                modifier = Modifier.size(330.dp)
            )

            Text(
                text = stringResource(id = R.string.login),
                color = Color.Yellow,
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo de email
            TextField(
                value = emailInput,
                onValueChange = {
                    emailInput = it
                    isError = it.text.isEmpty() || !isValidEmail(it.text)
                    emailErrorMessage = when {
                        it.text.isEmpty() -> "El email no puede estar vacío"
                        !isValidEmail(it.text) -> "El email debe terminar en @gmail.com"
                        else -> ""
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.email_subject),
                        color = Color.Black
                    )
                }
                ,
                isError = isError,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )

            if (isError) {
                Text(
                    text = emailErrorMessage,
                    color = Color.Red,
                    fontSize = dimensionResource(id = R.dimen.font_size_15sp).value.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Campo de contraseña
            TextField(
                value = passwordInput,
                onValueChange = {
                    passwordInput = it
                    isError = it.text.isEmpty() || !isValidPassword(it.text)
                    passwordErrorMessage = if (it.text.isEmpty()) "La contraseña no puede estar vacía" else ""
                },
                label = { Text(text = stringResource(id = R.string.password), color = Color.Black) },
                isError = passwordInput.text.isEmpty(),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )

            if (passwordInput.text.isEmpty()) {
                Text(
                    text = passwordErrorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Botón para login
            Button(
                onClick = {
                    if (isValidEmail(emailInput.text) && isValidPassword(passwordInput.text)) {
                        loginViewModel.saveEmail(emailInput.text)
                        onLogin()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = isValidEmail(emailInput.text) && isValidPassword(passwordInput.text),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = if (isValidEmail(emailInput.text) && isValidPassword(passwordInput.text)) Color.Black else Color.White
                )
            ) {
                Text(text = stringResource(id = R.string.enter))
            }
        }
    }
}

