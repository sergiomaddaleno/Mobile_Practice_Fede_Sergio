package com.smf.practica_fede_sergio.Screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smf.practica_fede_sergio.R
import com.smf.practica_fede_sergio.Retrofit.MarsPhotosScreen
import com.smf.practica_fede_sergio.ViewModel.UserViewModel

private val LightThemeColors = lightColorScheme(
    primary = Color.Black,
    secondary = Color.Black,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

// Tema oscuro
private val DarkThemeColors = darkColorScheme(
    primary = Color.Black,
    secondary = Color.Yellow,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Yellow,
    onSecondary = Color.Black,
    onBackground = Color.Yellow,
    onSurface = Color.Yellow,
)

@Composable
fun AppTheme(
    isDarkMode: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (isDarkMode) DarkThemeColors else LightThemeColors,
        content = content
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(loginViewModel: UserViewModel, navController: NavController) {
    val homeNavController = rememberNavController()
    val selectedItem = remember { mutableStateOf("home_main") }
    val uiState by loginViewModel.uiState.collectAsState() // Observamos el estado de UI, que incluye isDarkMode

    // Usamos el tema según el estado del modo oscuro
    AppTheme(isDarkMode = uiState.isDarkMode) { // Cambiar el tema según el estado global
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        containerColor = Color.Black
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            BottomAppBarButton(
                                icon = Icons.Filled.Home,
                                contentDescription = "Home",
                                isSelected = selectedItem.value == "home_main",
                                onClick = {
                                    selectedItem.value = "home_main"
                                    homeNavController.navigate("home_main") {
                                        popUpTo(homeNavController.graph.startDestinationId) { inclusive = true }
                                    }
                                }
                            )
                            BottomAppBarButton(
                                icon = Icons.Filled.Person,
                                contentDescription = "Profile",
                                isSelected = selectedItem.value == "profile",
                                onClick = {
                                    selectedItem.value = "profile"
                                    homeNavController.navigate("profile")
                                }
                            )
                            BottomAppBarButton(
                                icon = Icons.Filled.Settings,
                                contentDescription = "Settings",
                                isSelected = selectedItem.value == "settings",
                                onClick = {
                                    selectedItem.value = "settings"
                                    homeNavController.navigate("settings")
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->

                NavHost(
                    navController = homeNavController,
                    startDestination = "home_main",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    composable("home_main") {
                        HomeMainScreen(loginViewModel = loginViewModel)
                    }
                    composable("profile") {
                        ProfileScreen(loginViewModel = loginViewModel)
                    }
                    composable("settings") {
                        SettingsScreen(loginViewModel = loginViewModel)
                    }
                }
            }
        }
    }
}




@Composable
fun BottomAppBarButton(
    icon: ImageVector,
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.Yellow else Color.DarkGray
    val iconTint = if (isSelected) Color.Black else Color.White

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(12.dp)
            .indication(interactionSource = remember { MutableInteractionSource() }, indication = LocalIndication.current) // Ripple effect
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainScreen(
    loginViewModel: UserViewModel
) {
    val uiState by loginViewModel.uiState.collectAsState()

    val emailPrefix = uiState.email.substringBefore("@")

    Box(
        modifier = Modifier
            .fillMaxSize()

            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {

            Image(
                painter = painterResource(id = R.drawable.bo3),
                contentDescription = "Logo de la app",
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "Bienvenido de nuevo, $emailPrefix!",
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.Yellow),
                fontSize = 28.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))


            StatsCard(title = "Partidas Jugadas", value = "150")
            StatsCard(title = "Nivel de Jugador", value = "45")
            StatsCard(title = "Puntos Totales", value = "12000")

            Spacer(modifier = Modifier.height(32.dp))


            LevelProgressBar(currentLevel = 45, maxLevel = 100)

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "Tus Logros",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Yellow),
                fontSize = 22.sp
            )
            LogroCard(title = "Completaste 100 partidas", icon = R.drawable.ic_check_circle)
            LogroCard(title = "Alcanzaste el nivel 30", icon = R.drawable.ic_check_circle)
            LogroCard(title = "Jugaste 3 días seguidos", icon = R.drawable.ic_check_circle)

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "Consejos para mejorar",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Yellow),
                fontSize = 22.sp
            )
            TipsCard(text = "Haz misiones diarias para mejorar tu nivel rápidamente.")
            TipsCard(text = "Juega con amigos para obtener bonificaciones de equipo.")

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "¡Sigue jugando y mejora cada día!",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun StatsCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                fontSize = 16.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Yellow),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun LevelProgressBar(currentLevel: Int, maxLevel: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Nivel: $currentLevel / $maxLevel",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            fontSize = 16.sp
        )
        LinearProgressIndicator(
            progress = currentLevel / maxLevel.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = Color.Yellow,
            trackColor = Color.Gray
        )
    }
}

@Composable
fun LogroCard(title: String, icon: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun TipsCard(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                fontSize = 14.sp
            )
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    loginViewModel: UserViewModel
) {
    val uiState by loginViewModel.uiState.collectAsState()

    var isDarkMode by remember { mutableStateOf(uiState.isDarkMode) }

    // Función para guardar el estado del modo oscuro
    fun toggleDarkMode(isDark: Boolean) {
        loginViewModel.saveDarkMode(isDark) // Guardamos el estado en el ViewModel
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ajustes del Juego",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 24.sp,
            color = if (isDarkMode) Color.Yellow else Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Modo Oscuro",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isDarkMode) Color.White else Color.Black
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = { checked ->
                    isDarkMode = checked
                    toggleDarkMode(checked) // Actualizamos el estado en el ViewModel
                }
            )
        }
    }
}



fun openCallOfDutyPage(context: Context) {
    val url = "https://www.callofduty.com"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

