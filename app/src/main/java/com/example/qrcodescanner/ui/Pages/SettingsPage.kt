package com.example.qrcodescanner.ui.Pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun SettingsPage(isDark: Boolean, onThemeChange: (Boolean) -> Unit) {
    Column {
        Header(HeaderText = "Settings")

    }
    ThemeElement(isDark = isDark, onThemeChange = onThemeChange)

}

@Composable
fun ThemeElement(isDark: Boolean, onThemeChange: (Boolean) -> Unit) {
    FilledTonalButton(onClick = { onThemeChange(!isDark); Log.d("ThemeChange", "Changed") } ) {
        if (isDark) {
            Text(
                text = "Switch to Light"
            )
        } else {
            Text (
                text = "Switch to Dark"
            )
        }
    }
}

@Composable
@Preview
fun SettingsPagePreview() {
    SettingsPage(
        isDark = TODO(),
        onThemeChange = TODO()
    )
}