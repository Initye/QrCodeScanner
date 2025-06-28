package com.example.qrcodescanner.ui.Pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
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
import com.example.qrcodescanner.R

@Composable
fun Header(modifier: Modifier = Modifier) {
    Text(
        text = "QR Scanner",
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.CenterVertically),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun BottomNav(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(Icons.Default.AddCircle, R.string.QrScanner, R.string.QrScanner)
        BottomNavItem(Icons.Default.ArrowDropDown, R.string.History, R.string.History)
        BottomNavItem(Icons.Default.Settings, R.string.Settings, R.string.Settings)
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, text: Int, description: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = stringResource(id = description))
        Text(text = stringResource(id = text))
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header()
}

@Preview
@Composable
fun BottomNavPreview() {
    BottomNav()
}