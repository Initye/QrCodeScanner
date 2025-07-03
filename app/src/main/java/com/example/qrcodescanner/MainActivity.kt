package com.example.qrcodescanner

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.qrcodescanner.ui.Pages.BottomNav
import com.example.qrcodescanner.ui.Pages.HistoryPage
import com.example.qrcodescanner.ui.Pages.MainPage
import com.example.qrcodescanner.ui.Pages.SettingsPage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.qrcodescanner.ui.SettingsRepository
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "qrCode")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        setContent {
            val context = LocalContext.current
            val settingsRepository = remember { SettingsRepository(context) }
            val isDarkTheme by settingsRepository.isDarkTheme.collectAsState(initial = false)
            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()

            AppTheme(settingsRepository = settingsRepository, dynamicColor = true) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar =  { BottomNav(navController = navController, modifier = Modifier.navigationBarsPadding()) }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = "mainPage", modifier = Modifier.padding(innerPadding)) {
                        composable("mainPage") { MainPage() }
                        composable("historyPage") { HistoryPage() }
                        composable("settingsPage") { SettingsPage(
                            isDark = isDarkTheme,
                            onThemeChange = { isDark ->
                                coroutineScope.launch { settingsRepository.setDarkTheme(isDark) }
                            }
                        ) }
                    }
                }
            }
        }
    }
}



