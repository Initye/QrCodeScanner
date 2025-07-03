package com.example.qrcodescanner.ui.Pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.qrcodescanner.R

@Composable
fun SettingsPage(modifier: Modifier = Modifier, isDark: Boolean, onThemeChange: (Boolean) -> Unit) {
    Column {
        Header(HeaderText = "Settings")
        Spacer(modifier = modifier.padding(top = 40.dp))
        Column(
            modifier = modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeadlineSetting(SettingsText = "Theme")
            ThemeElement(isDark = isDark, onThemeChange = onThemeChange)
        }
    }

}

@Composable
fun ThemeElement(isDark: Boolean, onThemeChange: (Boolean) -> Unit) {
    FilledTonalButton(onClick = { onThemeChange(!isDark); Log.d("ThemeChange", "Changed") } ) {
        if (isDark) {
            Text(
                text = stringResource(id = R.string.SwitchThemeLight)
            )
        } else {
            Text (
                text = stringResource(id = R.string.SwitchThemeDark)
            )
        }
    }
}

@Composable
fun HeadlineSetting(SettingsText: String) {
    Text(
        text = SettingsText,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
@Preview
fun SettingsPagePreview() {
    SettingsPage(
        isDark = true,
        onThemeChange = {}
    )
}