package com.example.qrcodescanner.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.qrcodescanner.data.storage.dataStore

//Deleting the history of QR codes
suspend fun deleteHistory(context: Context) {
    context.dataStore.edit { preferences ->
        preferences.remove(PreferencesKeys.QR_CODES)
    }
}

object PreferencesKeys {
    val QR_CODES = stringPreferencesKey("qr_codes")
}

suspend fun saveQR(context: Context, qrCode: String) {
    context.dataStore.edit { preferences ->
        val existingCodes = preferences[PreferencesKeys.QR_CODES] ?: ""
        val updatedCodes = if(existingCodes.isEmpty()) {
            qrCode
        } else {
            "$existingCodes, $qrCode" //Creating coma separated list
        }
        preferences[PreferencesKeys.QR_CODES] = updatedCodes.toString()
    }
}

