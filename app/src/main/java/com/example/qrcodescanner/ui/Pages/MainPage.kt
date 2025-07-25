package com.example.qrcodescanner.ui.Pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.qrcodescanner.ui.components.CameraHandler

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(HeaderText = "QR Scanner")
        CameraHandler(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainPagePreview() {
    MainPage()
}
