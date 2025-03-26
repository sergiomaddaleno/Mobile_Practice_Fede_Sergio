package com.smf.practica_fede_sergio.DataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smf.practica_fede_sergio.Screens.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataSource(
    private val dataStore: DataStore<Preferences>
) {

    private companion object {
        val USER_MAIL = stringPreferencesKey("user_mail")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val IS_ENGLISH = booleanPreferencesKey("is_english")
        const val LOGIN_PREFERENCES = "LoginPreferences"
        val PLAYERS_LIST = stringPreferencesKey("players_list")
    }

    val getPlayersList: Flow<List<Player>> = dataStore.data
        .map { preferences ->
            val playersJson = preferences[PLAYERS_LIST] ?: "[]"
            Json.decodeFromString<List<Player>>(playersJson)
        }

    suspend fun savePlayersList(players: List<Player>) {
        dataStore.edit { preferences ->
            val playersJson = Json.encodeToString(players)
            preferences[PLAYERS_LIST] = playersJson
        }
    }


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

    val getDarkModeStatus: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is java.io.IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[DARK_MODE] ?: false // Default value to false
        }


    val getLanguageStatus: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[IS_ENGLISH] ?: false
        }


    suspend fun saveEmailPreference(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[USER_MAIL] = userEmail
        }
    }


    suspend fun saveDarkModePreference(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = isDarkMode
        }
    }

    suspend fun clearEmailPreference() {
        dataStore.edit { preferences ->
            preferences.remove(USER_MAIL)
        }
    }

    suspend fun saveLanguagePreference(isEnglish: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_ENGLISH] = isEnglish
        }
    }
}
