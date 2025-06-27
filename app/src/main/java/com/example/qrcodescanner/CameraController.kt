package com.example.qrcodescanner

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qrcodescanner.ui.Pages.NoPermission

@Composable
fun CameraController() {
    val context = LocalContext.current

    AndroidView(
        factory = { context ->
            val previewView = PreviewView(context)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener(Runnable {
                val cameraProvider = cameraProviderFuture.get()
                //Preview use case to display camera preview
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(
                        previewView.surfaceProvider
                    )
                }

                //Choose the camera by requiring a lens facing
                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                //Attach use cases to the camera with the same lifecycle owner
                val camera = cameraProvider.bindToLifecycle(
                    context as LifecycleOwner, cameraSelector, preview
                )

            }, ContextCompat.getMainExecutor(context))
            previewView //Need to return in AndroidView
        },
    )
}

@Composable
fun CameraHandler() {
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA //Checking if app has camera permission
            ) == PackageManager.PERMISSION_GRANTED //Boolean checking, returns true if permission granted
        )
    }
    Log.d("CameraHandler", "CameraHandler composed with callback")
    val permissionsRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasPermission = granted } //Setting the granted
    )

    if(hasPermission) {
        CameraController()
    } else {
        NoPermission(
            onRequestPermission = {
                permissionsRequest.launch(Manifest.permission.CAMERA)
            }, openDialog = openDialog
        )
    }
}