package com.example.qrcodescanner.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val THEME_KEY = booleanPreferencesKey("dark_theme_enabled")

class SettingsRepository(private val context: Context) {
    private val dataStore = context.dataStore

    val isDarkTheme: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: false
        }

    suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = enabled
        }
    }
}