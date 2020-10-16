package com.suraj.tracker.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

object Utils {

    val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE =
        arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun  showToast(context: Context,msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_LONG)
            .show()
    }

    fun verifyStoragePermissions(activity: Context,perm:String):Boolean {
        // Check if we have write permission
        var isPermission=true
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            perm
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
           isPermission=false
        }
        return isPermission
    }

    fun requestStoragePermission(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}