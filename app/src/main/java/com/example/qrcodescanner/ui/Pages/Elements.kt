package com.example.qrcodescanner.ui.Pages

import android.R.attr.onClick
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.qrcodescanner.R

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NoPermission(modifier: Modifier = Modifier, onRequestPermission: () -> Unit, openDialog: MutableState<Boolean>) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info",
                modifier = modifier
                    .clickable(onClick = { openDialog.value = true })
            )
            Text(
                text = stringResource(id = R.string.noPermission),
                textAlign = TextAlign.Center
            )
            Button(onClick = { onRequestPermission(); Log.d("Clicked","permission request") }) {
                Text("Grant permission")
            }
        }
    }

    if(openDialog.value) {
        InfoDialog(openDialog = openDialog)
    }

}

@Composable
fun InfoDialog(openDialog: MutableState<Boolean>) {
    Dialog(onDismissRequest = { openDialog.value = false }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.requestPermissionInfo),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun NoPermissionPreview() {
    NoPermission(onRequestPermission = {}, openDialog = mutableStateOf(false))
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun MinimalDialogPreview() {
    InfoDialog(openDialog = mutableStateOf(false))
}