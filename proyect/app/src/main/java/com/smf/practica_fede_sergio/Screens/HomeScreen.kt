package com.smf.practica_fede_sergio.Screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smf.practica_fede_sergio.R
import com.smf.practica_fede_sergio.Retrofit.MarsPhotosScreen
import com.smf.practica_fede_sergio.ViewModel.UserViewModel
import java.time.format.TextStyle
import androidx.compose.ui.res.stringResource
import android.content.res.Configuration
import java.util.Locale

private val LightThemeColors = lightColorScheme(
    primary = Color.Black,
    secondary = Color.Black,
    background = Color.White,
    surface =  Color(0xFFFF9500),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black, // Text color on orange background
    onSurface = Color.Black, // Text color on orange surface
    surfaceVariant = Color(0xFFFF9500), // Light gray (for cards, etc.)
    onSurfaceVariant = Color.Black // Text color on light gray
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
                                loginViewModel.logout()
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

                            BottomAppBarButton(  // NEW BUTTON
                                icon = Icons.Filled.Person,
                                contentDescription = "Players",
                                isSelected = selectedItem.value == "players",
                                onClick = {
                                    selectedItem.value = "players"
                                    homeNavController.navigate("players")
                                }
                            )

                            BottomAppBarButton(
                                icon = Icons.Filled.Info,
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

                    composable("players") { // NEW ROUTE
                        PlayersScreen(loginViewModel = loginViewModel)
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
fun PlayersScreen(loginViewModel: UserViewModel) {
    val uiState by loginViewModel.uiState.collectAsState()
    val players = remember { mutableStateListOf<Player>() }


    var showDialog by remember { mutableStateOf(false) }

    // Function to update the ViewModel


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Añadir personajes de Call of Duty",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.End)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir Personaje",
                    tint = Color.Black
                )
                Text(
                    text = "Añadir Jugador",
                    color = Color.Black
                )
            }
        }

        LazyColumn {
            items(players) { player ->
                PlayerCard(
                    player = player,
                    onPlayerChange = { newName ->
                        val index = players.indexOf(player)
                        if (index != -1) {
                            players[index] = player.copy(name = newName)

                        }
                    },
                    onDelete = {
                        players.remove(player)

                    }
                )
            }
        }

        if (showDialog) {
            AddPlayerDialog(
                onDismissRequest = { showDialog = false },
                onConfirm = { newPlayerName, imageRes ->
                    players.add(Player(newPlayerName, imageRes))

                    showDialog = false
                }
            )
        }
    }
}

data class Player(val name: String, val imageRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerCard(player: Player, onPlayerChange: (String) -> Unit, onDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var playerName by remember { mutableStateOf(player.name) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            if (isEditing) {
                TextField(
                    value = playerName,
                    onValueChange = { playerName = it },
                    label = { Text("Nombre del Jugador") },
                    modifier = Modifier.padding(16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        onPlayerChange(playerName)
                        isEditing = false
                    }) {
                        Text("Guardar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { isEditing = false }) {
                        Text("Cancelar")
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = player.name,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Row {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar Nombre")
                        }
                        IconButton(onClick = onDelete) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar Jugador")
                        }
                    }
                }
            }
            if (expanded) {
                Image(
                    painter = painterResource(id = player.imageRes),
                    contentDescription = "Imagen de ${player.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlayerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String, Int) -> Unit
) {
    val context = LocalContext.current
    var newPlayerName by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf(R.drawable.cod_logo) } // Initial image

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Añadir Nuevo Jugador",
                    style = MaterialTheme.typography.headlineSmall
                )

                TextField(
                    value = newPlayerName,
                    onValueChange = { newPlayerName = it },
                    label = { Text("Nombre del Jugador") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Seleccionar Imagen:",
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ImageChoice(imageRes = R.drawable.ghost, selected = selectedImage == R.drawable.ghost) {
                        selectedImage = R.drawable.ghost
                    }
                    ImageChoice(imageRes = R.drawable.soap, selected = selectedImage == R.drawable.soap) {
                        selectedImage = R.drawable.soap
                    }
                    ImageChoice(imageRes = R.drawable.price, selected = selectedImage == R.drawable.price) {
                        selectedImage = R.drawable.price
                    }
                    ImageChoice(imageRes = R.drawable.alex, selected = selectedImage == R.drawable.alex) {
                        selectedImage = R.drawable.alex
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancelar")
                    }
                    TextButton(
                        onClick = {
                            if (newPlayerName.isNotEmpty()) {
                                onConfirm(newPlayerName, selectedImage)
                            }
                        },
                        enabled = newPlayerName.isNotEmpty()
                    ) {
                        Text("Añadir")
                    }
                }
            }
        }
    }
}

@Composable
fun ImageChoice(imageRes: Int, selected: Boolean, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Character",
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
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



object LocaleHelper {
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(loginViewModel: UserViewModel) {
    val uiState by loginViewModel.uiState.collectAsState()
    var isDarkMode by remember { mutableStateOf(uiState.isDarkMode) }
    var isEnglish by remember { mutableStateOf(uiState.isEnglish) }
    val context = LocalContext.current
    val email = uiState.email
    var linkedAccountsExpanded by remember { mutableStateOf(false) }
    var advancedSettingsExpanded by remember { mutableStateOf(false) }

    fun toggleDarkMode(isDark: Boolean) {
        loginViewModel.saveDarkMode(isDark)
    }

    fun toggleLanguage(isEnglish: Boolean) {
        val newLanguageCode = if (isEnglish) "en" else "es"
        val updatedContext = LocaleHelper.setLocale(context, newLanguageCode)
        loginViewModel.saveLanguage(isEnglish)

        // Restart the activity to apply changes
        (context as? Activity)?.recreate()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header: App Title
        Text(
            text = stringResource(id = R.string.settings_title),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkMode) Color.Yellow else Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 1. Dark Mode Setting
        SettingRow(
            title = stringResource(id = R.string.dark_mode),
            icon = Icons.Default.Build,
            content = {
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { checked ->
                        isDarkMode = checked
                        toggleDarkMode(checked)
                    }
                )
            }
        )

        // 2. Language Setting
        SettingRow(
            title = stringResource(id = R.string.language),
            icon = Icons.Default.Build,
            content = {
                Switch(
                    checked = isEnglish,
                    onCheckedChange = { checked ->
                        isEnglish = checked
                        toggleLanguage(checked)
                    }
                )
            }
        )

        // Header: Account
        Text(
            text = stringResource(id = R.string.contact),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkMode) Color.Yellow else Color.Black,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        // 5. Linked Accounts
        SettingRow(
            title = "Cuentas Enlazadas",
            icon = Icons.Default.Person,
            onClick = {
                linkedAccountsExpanded = !linkedAccountsExpanded
            }
        )

        AnimatedVisibility(
            visible = linkedAccountsExpanded,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, top = 8.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (email.isNotEmpty()) {
                    Text(
                        text = "Correo: $email",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "Correo: No Correo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Advanced Settings
        SettingRow(
            title = "Ajustes Avanzados",
            icon = Icons.Default.Settings,
            onClick = {
                advancedSettingsExpanded = !advancedSettingsExpanded
            }
        )

        AnimatedVisibility(
            visible = advancedSettingsExpanded,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, top = 8.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Slider Example
                var sliderValue by remember { mutableStateOf(50f) }

                Text(
                    text = "Volumen del juego: ${sliderValue.toInt()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    valueRange = 0f..100f,
                    steps = 9,
                    modifier = Modifier.fillMaxWidth()
                )

                // Checkbox example
                var vibrateOnTouch by remember { mutableStateOf(true) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Vibrar al tocar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Checkbox(
                        checked = vibrateOnTouch,
                        onCheckedChange = { vibrateOnTouch = it }
                    )
                }

                // Dropdown Example
                var selectedQuality by remember { mutableStateOf("Alta") }
                val options = listOf("Baja", "Media", "Alta")
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedQuality,
                        onValueChange = { },
                        label = { Text("Calidad de Gráficos") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedQuality = selectionOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // Header: Support
        Text(
            text = "Soporte",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkMode) Color.Yellow else Color.Black,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        // 8. Contact Us
        ContactButton(
            icon = Icons.Default.Email,
            text = stringResource(id = R.string.send_email),
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:support@callofduty.com")
                    putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject)) // Usa context.getString()
                }
                context.startActivity(intent)
            }
        )

        // Legal Section
        Text(
            text = "Legal",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkMode) Color.Yellow else Color.Black,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        // 14. App Version
        Text(
            text = "Version 1.0", // Replace with your actual version string
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}


// Reusable Setting Row
@Composable
fun SettingRow(
    title: String,
    icon: ImageVector,
    onClick: (() -> Unit)? = null,
    content: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (content != null) {
            content()
        }
    }
}



@Composable
fun SettingsScreenPreview() {
    // Use a mock ViewModel for the preview
    val mockViewModel = object : UserViewModel(dataSource = TODO()) {}

    SettingsScreen(loginViewModel = mockViewModel)
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


