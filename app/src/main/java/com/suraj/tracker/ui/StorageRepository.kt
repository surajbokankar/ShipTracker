package com.suraj.tracker.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.suraj.tracker.util.Utils
import com.williamww.silkysignature.views.SignaturePad
import java.io.*

object StorageRepository {


    fun onSignatureSave(mSignaturePad:SignaturePad,context: Context) {
        val signatureBitmap = mSignaturePad!!.signatureBitmap
        if (addJpgSignatureToGallery(signatureBitmap,context)) {
            Utils.showToast(context,"Signature saved into the Gallery")
        } else {
            Utils.showToast(context,"Unable to store the signature")
        }
        if (addSvgSignatureToGallery(mSignaturePad!!.signatureSvg,context)) {
            Utils.showToast(context,"SVG Signature saved into the Gallery")
        } else {

            Utils.showToast(context,"Unable to store the SVG signature")
        }
    }

    fun getAlbumStorageDir(albumName: String?): File? {
        // Get the directory for the user's public pictures directory.
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ), albumName
        )
        if (!file.mkdirs()) {
            println("SignaturePad Directory not created")
        }
        return file
    }

    @Throws(IOException::class)
    fun saveBitmapToJPG(bitmap: Bitmap, photo: File?) {
        val newBitmap =
            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)             //drawBitmap(bitmap, 0, 0, null)
        val stream: OutputStream = FileOutputStream(photo)
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        stream.close()
    }

    fun addJpgSignatureToGallery(signature: Bitmap,context: Context): Boolean {
        var result = false
        try {
            val photo = File(
                getAlbumStorageDir("SignaturePad"),
                String.format("Signature_%d.jpg", System.currentTimeMillis())
            )
            saveBitmapToJPG(signature, photo)
            scanMediaFile(photo,context)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    private fun scanMediaFile(photo: File,activity:Context) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri: Uri = Uri.fromFile(photo)
        mediaScanIntent.data = contentUri
        activity.sendBroadcast(mediaScanIntent)
    }

    fun addSvgSignatureToGallery(signatureSvg: String?,context: Context): Boolean {
        var result = false
        try {
            val svgFile = File(
                getAlbumStorageDir("SignaturePad"),
                String.format("Signature_%d.svg", System.currentTimeMillis())
            )
            val stream: OutputStream = FileOutputStream(svgFile)
            val writer = OutputStreamWriter(stream)
            writer.write(signatureSvg)
            writer.close()
            stream.flush()
            stream.close()
            scanMediaFile(svgFile,context)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

}