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
import com.smf.practica_fede_sergio.Screens.HomeScreen
import com.smf.practica_fede_sergio.Screens.RegisterScreen

import com.smf.practica_fede_sergio.ui.theme.Practica_Fede_SergioTheme
import androidx.compose.ui.platform.LocalContext
import com.smf.practica_fede_sergio.DataSource.MyTaskApplication
import com.smf.practica_fede_sergio.ViewModel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practica_Fede_SergioTheme {

                val navController = rememberNavController()

                val loginViewModel: UserViewModel = viewModel(
                    factory = UserViewModel.factory(
                        (LocalContext.current.applicationContext as MyTaskApplication).dataSource
                    )
                )

                NavHost(navController = navController, startDestination = "register") {
                    composable("register") {
                        RegisterScreen(
                            loginViewModel = loginViewModel,
                            onLogin = {
                                navController.navigate("home")
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen(loginViewModel = loginViewModel, navController = navController)
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
