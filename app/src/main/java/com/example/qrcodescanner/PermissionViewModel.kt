package com.example.qrcodescanner

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PermissionViewModel: ViewModel() {

    private val _permissionRequest = MutableLiveData<Array<String>>()
    val permissionRequest: LiveData<Array<String>> = _permissionRequest

    companion object {
        // Unique request code for permission request
        private const val PERMISSION_REQUEST_CODE = 123
    }

    fun requestPermissions(){
        val permissions = arrayOf(
            Manifest.permission.CAMERA
        )

        _permissionRequest.value = permissions
    }
}