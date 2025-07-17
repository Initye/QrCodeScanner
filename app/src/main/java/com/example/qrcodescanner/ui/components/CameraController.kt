package com.example.qrcodescanner.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.qrcodescanner.utils.ImageAnalyzer
import com.example.qrcodescanner.ui.Pages.NoPermission
import com.example.qrcodescanner.utils.saveQR
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.toComposeRect
import com.example.qrcodescanner.utils.vibratePhone

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CameraController(modifier: Modifier = Modifier, onQRCodeScanned: (String) -> Unit) {
    val density = LocalDensity.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var boundingRect by remember { mutableStateOf<Rect?>(null) }

    var barcodes by remember { mutableStateOf<Pair<Size, List<Barcode>>?>(null) }

    Box(Modifier.aspectRatio(1080f / 1920f).fillMaxWidth()) {
        AndroidView(
            modifier = modifier.align(Alignment.TopStart),
            factory = { context ->
                val previewView = PreviewView(context)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                var isScanning = true

                cameraProviderFuture.addListener(Runnable {
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also { analysis ->
                            analysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                ImageAnalyzer { qrCodeValue, detectedBarcodes, imageSize ->
                                    barcodes = Pair(imageSize, detectedBarcodes)

                                    if (isScanning) {
                                        Log.d("Result QRcode", "$qrCodeValue")
                                        onQRCodeScanned(qrCodeValue)
                                        isScanning = false

                                        CoroutineScope(Dispatchers.Main).launch {
                                            delay(5000)
                                            isScanning = true
                                        }
                                        vibratePhone(context)
                                        boundingRect = detectedBarcodes.first().boundingBox
                                    }
                                }
                            )
                        }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            context as LifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        Log.e("CameraController", "Binding failed $e")
                    }
                }, ContextCompat.getMainExecutor(context))
                previewView
            },
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CameraHandler(modifier: Modifier = Modifier) {
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    Log.d("CameraHandler", "CameraHandler composed with callback")
    val permissionsRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasPermission = granted }
    )

    if (hasPermission) {
        CameraController(modifier = modifier, onQRCodeScanned = { qrCodeValue ->
            CoroutineScope(Dispatchers.IO).launch {
                saveQR(context, qrCodeValue)
            }
        })
    } else {
        NoPermission(
            onRequestPermission = {
                permissionsRequest.launch(Manifest.permission.CAMERA)
            }, openDialog = openDialog
        )
    }
}
