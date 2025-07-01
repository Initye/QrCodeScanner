package com.example.qrcodescanner.ui.Pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
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
        Header(HeaderText = "History")
        if(qrHistoryText.isEmpty()) {
            Text("No scanned QR codes found")
        } else {
            LazyColumn {
                items(qrHistoryText) { qrCode ->
                    HistoryElement(qrCode = qrCode)
                }
            }
        }
    }
}

@Composable
fun HistoryElement(qrCode: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val trimmedQRLink = qrCode.trim()
            val annotatedText = if(trimmedQRLink.startsWith("https://") || trimmedQRLink.startsWith("http://")) {
                    buildAnnotatedString {
                        withLink(
                            LinkAnnotation.Url(
                                qrCode.trim(), //Using trim because of how QR codes are scanning
                                TextLinkStyles(style = SpanStyle(color = Color.Blue))
                            )
                        ) {
                            append(qrCode)
                        }
                    }
            } else {
                AnnotatedString(qrCode)
            }

            val textColor = if(trimmedQRLink.startsWith("https://") || trimmedQRLink.startsWith("http://")) {
                Color.Unspecified
            } else {
                MaterialTheme.colorScheme.onSurface
            }

            Text(
                text = annotatedText,
                modifier = modifier,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = "Open link",
                modifier = modifier
                    .padding(8.dp)
                    .size(12.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
}

@Composable
@Preview
fun HistoryPagePreview() {
    HistoryPage()
}

@Composable
@Preview
fun HistoryElementPreview() {
    HistoryElement(qrCode = "https://google.com")
}