package com.example.qrcodescanner.ui.Pages

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.qrcodescanner.R

@Composable
fun Header(modifier: Modifier = Modifier, HeaderText: String) {
    Text(
        text = HeaderText,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.CenterVertically),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun BottomNav(navController: NavController, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(Icons.Default.AddCircle, R.string.QrScanner, R.string.QrScanner, onClick = { navController.navigate("mainPage"); ; Log.d("Navigation", "mainPage") })
        BottomNavItem(Icons.Default.ArrowDropDown, R.string.History, R.string.History, onClick = { navController.navigate("historyPage"); Log.d("Navigation", "History") })
        BottomNavItem(Icons.Default.Settings, R.string.Settings, R.string.Settings, onClick = { navController.navigate("settingsPage"); Log.d("Navigation", "Settings")})
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, text: Int, description: Int, onClick: () -> Unit ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Icon(icon, contentDescription = stringResource(id = description), modifier = Modifier.size(24.dp))
        Text(text = stringResource(id = text), style = MaterialTheme.typography.labelMedium)
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(HeaderText = "")
}

@Preview
@Composable
fun BottomNavPreview() {
    BottomNav(navController = rememberNavController())
}