package com.example.qrcodescanner.utils

import android.os.Build
import android.os.VibrationEffect
import android.util.Log
import androidx.compose.ui.geometry.Size

import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


class ImageAnalyzer(
    private val onBarcodeDetected: (String, List<Barcode>, Size) -> Unit
) : ImageAnalysis.Analyzer {
        private val scanner  = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                    Barcode.FORMAT_AZTEC
                    )
                .build()
        )

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val imageSize = Size(mediaImage.width.toFloat(), mediaImage.height.toFloat())

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue //qr code text
                        if (rawValue != null) {
                            onBarcodeDetected(rawValue, barcodes, imageSize)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d("ImageAnalyzer", "Scanner failed")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            Log.d("ImageAnalyzer", "MediaImage null")
            imageProxy.close()
        }
    }
}

