package com.example.qrcodescanner.ui.Pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.qrcodescanner.ui.components.CameraHandler

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


@Preview
@Composable
fun MainPagePreview() {
    MainPage()
}
