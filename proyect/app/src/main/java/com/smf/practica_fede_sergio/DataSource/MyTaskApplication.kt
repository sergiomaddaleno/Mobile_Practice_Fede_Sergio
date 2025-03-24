package com.smf.practica_fede_sergio.DataSource

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class MyTaskApplication : Application() {

    lateinit var dataSource: DataSource

    override fun onCreate() {
        super.onCreate()
        dataSource = DataSource(dataStore)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login_preferences")
}
