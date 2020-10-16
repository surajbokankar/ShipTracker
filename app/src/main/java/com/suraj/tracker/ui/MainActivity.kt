package com.suraj.tracker.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.util.Util
import com.github.gcacace.signaturepad.views.SignaturePad
import com.suraj.tracker.R
import com.suraj.tracker.ui.StorageRepository.onSignatureSave
import com.suraj.tracker.ui.fragment.GalleryFragment
import com.suraj.tracker.util.CommonHolder
import com.suraj.tracker.util.Utils
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.layout_tool_bar.*
import java.io.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private var mSignaturePad: com.williamww.silkysignature.views.SignaturePad? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        Utils.requestStoragePermission(this);
        setListener()
    }

    private fun setListener() {
        mSignaturePad = signature_pad
        save.setOnClickListener(this)
        clear.setOnClickListener(this)
        gallary.setOnClickListener(this)

        mSignaturePad?.setOnSignedListener(object :
            com.williamww.silkysignature.views.SignaturePad.OnSignedListener {
            override fun onStartSigning() {

            }

            override fun onClear() {
                save!!.isEnabled = false
                clear!!.isEnabled = false
            }

            override fun onSigned() {
                save!!.isEnabled = true
                clear!!.isEnabled = true
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.clear -> {
                signature_pad.clear()
            }
            R.id.save -> {
                onSignatureSave(mSignaturePad!!,this)
            }
            R.id.gallary -> {
                openGallerIntent()
            }
        }
    }

    private fun openGallerIntent() {
        CommonHolder.Builder(this)
            .setFragment(GalleryFragment::class.java.canonicalName)
            .setTitle("Gallery")
            .setToolbarBackBtn(true).setTheme(R.style.AppTheme)
            .setShowToolbar(true)
            .build()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>, grantResults: IntArray
    ) {
        when (requestCode) {
            Utils.REQUEST_EXTERNAL_STORAGE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size <= 0
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        this,
                        "Cannot write images to external storage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}