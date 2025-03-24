package com.smf.practica_fede_sergio.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smf.practica_fede_sergio.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(loginViewModel: UserViewModel, navController: NavController) {
    val homeNavController = rememberNavController()
    val selectedItem = remember { mutableStateOf("home_main") }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {
                            selectedItem.value = "home_main"
                            homeNavController.navigate("home_main") {
                                popUpTo(homeNavController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    IconButton(
                        onClick = {
                            selectedItem.value = "profile"
                            homeNavController.navigate("profile")
                        }
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    }
                    IconButton(
                        onClick = {
                            selectedItem.value = "settings"
                            homeNavController.navigate("settings")
                        }
                    ) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = homeNavController,
            startDestination = "home_main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home_main") {
                HomeMainScreen(loginViewModel = loginViewModel)
            }
            composable("profile") {
                ProfileScreen(loginViewModel = loginViewModel)
            }
            composable("settings") {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeMainScreen(loginViewModel: UserViewModel) {
    val uiState by loginViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hola, ${uiState.email}!",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}

@Composable
fun ProfileScreen(loginViewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MarsPhotosScreen()
    }
}

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Settings Screen")
    }
}
