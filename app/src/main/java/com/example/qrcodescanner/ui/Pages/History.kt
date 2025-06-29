package com.example.qrcodescanner.ui.Pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.qrcodescanner.PreferencesKeys
import com.example.qrcodescanner.dataStore

@Composable
fun HistoryPage() {
    val context = LocalContext.current
    var qrHistoryText by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
    context.dataStore.data.collect { preferences ->
            val codes = preferences[PreferencesKeys.QR_CODES] ?: ""
            qrHistoryText = if (codes.isEmpty()) emptyList() else codes.split(",")
        }
    }
    Column {
        if(qrHistoryText.isEmpty()) {
            Text("No scanned QR codes found")
        } else {
            qrHistoryText.forEach { qrCode ->
                Text(
                    text = qrCode
                )
            }
        }
    }
}

@Composable
@Preview
fun HistoryPagePreview() {
    HistoryPage()
}