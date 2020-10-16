package com.suraj.tracker.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suraj.fitstif.base.BaseFragment
import com.suraj.tracker.R
import com.suraj.tracker.databinding.FragmentGalleryBinding
import com.suraj.tracker.ui.adapter.GalleryAdapter
import com.suraj.tracker.ui.fragment.model.ImageModel
import com.suraj.tracker.util.Utils
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.IOException


class GalleryFragment : BaseFragment<FragmentGalleryBinding>(), View.OnClickListener {
    private val GALLERY_INT = 1
    lateinit var images: ArrayList<ImageModel>
    lateinit var mAdapter: GalleryAdapter
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE =
        arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_gallery
    }

    override fun init(dataBinding: FragmentGalleryBinding?) {
        load_images.setOnClickListener(this)
    }

    override fun observeChanges() {

    }

    fun onLoadImage() {
        val galleryIntent = Intent()
        try {
            if (Utils.verifyStoragePermissions(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                galleryIntent.apply {
                    action = Intent.ACTION_GET_CONTENT
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    startActivityForResult(Intent.createChooser(this, "Select Image"), GALLERY_INT)
                }
            } else {
                verifyStoragePermissions()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.load_images -> {
                onLoadImage()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == GALLERY_INT && resultCode == Activity.RESULT_OK
            ) {
                images = ArrayList<ImageModel>()
                if (data?.data != null) {
                    val result = data
                    val mImageUri: Uri = data?.data!!
                    var compressedImageBitmap: Bitmap? = null
                    val model = ImageModel()
                    compressedImageBitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().getContentResolver(),
                        mImageUri
                    );
                    model.image = compressedImageBitmap
                    images.add(model)
                } else {
                    if (data?.getClipData() != null) {
                        val mClipData: ClipData = data?.getClipData()!!
                        for (i in 0 until mClipData.itemCount) {
                            val model = ImageModel()
                            val item = mClipData.getItemAt(i)
                            val uri: Uri = item.uri
                            var compressedImageBitmap: Bitmap? = null
                            compressedImageBitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().getContentResolver(),
                                uri
                            );
                            model.image = compressedImageBitmap
                            images.add(model)
                        }
                    }
                }
                setAdapter()
            } else {
                Utils.showToast(requireContext(), "You haven't picked Image")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.showToast(requireContext(), "Something Went Wrong")
        }
    }

    private fun setAdapter() {
        mAdapter = GalleryAdapter(requireContext(), images)
        list_image_view.adapter = mAdapter
    }

    fun verifyStoragePermissions() {
        val permission = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

}