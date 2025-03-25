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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.text.style.TextOverflow
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
    surfaceVariant = Color(0xFFFFA500), // Gris claro (ejemplo para las tarjetas)
    onSurfaceVariant = Color.Black
)

private val DarkThemeColors = darkColorScheme(
    primary =  Color.Yellow,
    secondary = Color(0xFFA9A9A9),
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Yellow,
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF424242),
    onSurfaceVariant = Color(0xFFE0E0E0)
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
    val uiState by loginViewModel.uiState.collectAsState()
    val emailPrefix = uiState.email.substringBefore("@")
    AppTheme(isDarkMode = uiState.isDarkMode) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Bienvenido de nuevo, $emailPrefix!",
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        actions = {
                            IconButton(onClick = {
                                loginViewModel.logout() // Llama a la función para eliminar los datos del DataStore
                                navController.navigate("register") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ExitToApp,
                                    contentDescription = "Logout",
                                    tint = Color.Red

                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
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
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val iconTint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(12.dp)
            .indication(interactionSource = remember { MutableInteractionSource() }, indication = LocalIndication.current)
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
fun HomeMainScreen(loginViewModel: UserViewModel) {
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


            Spacer(modifier = Modifier.height(15.dp))

            StatsCard(title = "Partidas Jugadas", value = "150")
            StatsCard(title = "Nivel de Jugador", value = "45")
            StatsCard(title = "Puntos Totales", value = "12000")

            Spacer(modifier = Modifier.height(32.dp))

            LevelProgressBar(currentLevel = 45, maxLevel = 100)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Tus Logros",

                fontSize = 22.sp
            )
            LogroCard(title = "Completaste 100 partidas", icon = R.drawable.ic_check_circle)
            LogroCard(title = "Alcanzaste el nivel 30", icon = R.drawable.ic_check_circle)
            LogroCard(title = "Jugaste 3 días seguidos", icon = R.drawable.ic_check_circle)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Consejos para mejorar",

                fontSize = 22.sp
            )
            TipsCard(text = "Haz misiones diarias para mejorar tu nivel rápidamente.")
            TipsCard(text = "Juega con amigos para obtener bonificaciones de equipo.")

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "¡Sigue jugando y mejora cada día!",

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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), // Usar color del tema
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Usar color del tema
                fontSize = 16.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary, // Usar color del tema
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
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        )
        LinearProgressIndicator(
            progress = currentLevel / maxLevel.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
fun SettingsScreen(loginViewModel: UserViewModel) {
    val uiState by loginViewModel.uiState.collectAsState()
    var isDarkMode by remember { mutableStateOf(uiState.isDarkMode) }
    val context = LocalContext.current

    fun toggleDarkMode(isDark: Boolean) {
        loginViewModel.saveDarkMode(isDark)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Ajustes del Juego",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 24.sp,
            color = if (isDarkMode) Color.Yellow else Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
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
                    toggleDarkMode(checked)
                }
            )
        }

        Text(
            text = "Contacto",
            style = MaterialTheme.typography.headlineMedium,
            color = if (isDarkMode) Color.Yellow else Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ContactButton(
            icon = Icons.Default.Email,
            text = "Enviar correo",
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:support@callofduty.com")
                    putExtra(Intent.EXTRA_SUBJECT, "Consulta sobre Call of Duty")
                }
                context.startActivity(intent)
            }
        )

        ContactButton(
            icon = Icons.Default.Phone,
            text = "Llamar al soporte",
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+18005915240"))
                context.startActivity(intent)
            }
        )

        ContactButton(
            icon = Icons.Default.LocationOn,
            text = "Visitar sitio web oficial",
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.callofduty.com"))
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun ContactButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = text)
    }
}


