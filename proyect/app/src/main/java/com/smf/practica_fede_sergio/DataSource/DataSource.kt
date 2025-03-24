package com.smf.practica_fede_sergio.DataSource

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
        const val LOGIN_PREFERENCES = "LoginPreferences"
    }

    // Función para obtener el correo del usuario desde DataStore
    val getUserEmail: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is java.io.IOException) {
                Log.e(LOGIN_PREFERENCES, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_MAIL] ?: "" // Devuelve el correo o una cadena vacía si no existe
        }

    // Función para guardar el correo del usuario en DataStore
    suspend fun saveEmailPreference(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[USER_MAIL] = userEmail
        }
    }
}



