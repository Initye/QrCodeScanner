package com.example.qrcodescanner.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.qrcodescanner.data.storage.THEME_KEY
import com.example.qrcodescanner.data.storage.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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