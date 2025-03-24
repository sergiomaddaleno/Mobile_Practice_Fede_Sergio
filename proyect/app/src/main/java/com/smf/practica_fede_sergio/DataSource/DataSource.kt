package com.smf.practica_fede_sergio.DataSource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataSource(
    private val dataStore: DataStore<Preferences>
) {

    private companion object {
        val USER_MAIL = stringPreferencesKey("user_mail")
        val DARK_MODE = booleanPreferencesKey("dark_mode") // Clave para el modo oscuro
        const val LOGIN_PREFERENCES = "LoginPreferences"
    }

    // Función para obtener el correo del usuario desde DataStore
    val getUserEmail: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is java.io.IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_MAIL] ?: ""
        }

    // Función para obtener el estado del modo oscuro desde DataStore
    val getDarkModeStatus: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is java.io.IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[DARK_MODE] ?: true
        }

    // Función para guardar el correo del usuario
    suspend fun saveEmailPreference(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[USER_MAIL] = userEmail
        }
    }

    // Función para guardar el estado del modo oscuro
    suspend fun saveDarkModePreference(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = isDarkMode
        }
    }
}
