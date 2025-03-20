package com.smf.practica_fede_sergio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smf.practica_fede_sergio.Screens.Home
import com.smf.practica_fede_sergio.Screens.HomeScreen
import com.smf.practica_fede_sergio.Screens.Register
import com.smf.practica_fede_sergio.Screens.RegisterScreen
import com.smf.practica_fede_sergio.ViewModel.UserViewModel
import com.smf.practica_fede_sergio.ui.theme.Practica_Fede_SergioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practica_Fede_SergioTheme {
                // Crear un NavController para gestionar la navegación
                val navController = rememberNavController()

                // Crear un UserViewModel que será usado en las pantallas
                val userViewModel: UserViewModel = viewModel()

                // Configuramos el NavHost con las rutas
                NavHost(navController = navController, startDestination = "register") {
                    // Composable para la pantalla de registro
                    composable("register") {
                        RegisterScreen(
                            userViewModel = userViewModel,  // Pasamos el ViewModel a RegisterScreen
                            onLogin = {
                                navController.navigate("home")
                            }
                        )
                    }

                    // Composable para la pantalla principal (Home)
                    composable("home") {
                        HomeScreen(userViewModel = userViewModel)
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Practica_Fede_SergioTheme {
        Greeting("Android")
    }
}