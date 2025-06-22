package com.example.qrcodescanner

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

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