package com.example.qrcodescanner.ui.Pages

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcodescanner.CameraHandler

@Composable
fun MainPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Header()
        CameraHandler(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f) // Takes 80% of remaining height
        )
        BottomNav()
    }
}


@Preview
@Composable
fun MainPagePreview() {
    MainPage()
}
