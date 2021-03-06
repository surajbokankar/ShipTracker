package com.suraj.tracker.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import com.suraj.fitstif.base.BaseFragment
import com.suraj.tracker.R
import com.suraj.tracker.databinding.FragmentGalleryBinding
import com.suraj.tracker.ui.adapter.GalleryAdapter
import com.suraj.tracker.ui.fragment.model.ImageModel
import com.suraj.tracker.util.Utils.requestStoragePermission
import com.suraj.tracker.util.Utils.showToast
import com.suraj.tracker.util.Utils.verifyStoragePermissions
import kotlinx.android.synthetic.main.fragment_gallery.*


class GalleryFragment : BaseFragment<FragmentGalleryBinding>(), View.OnClickListener {
    private val GALLERY_INT = 1
    lateinit var images: ArrayList<ImageModel>
    lateinit var mAdapter: GalleryAdapter

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
            if (verifyStoragePermissions(
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
               requestStoragePermission(requireActivity())
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
                    val mImageUri: Uri = data?.data!!
                    storeBitMap(mImageUri)
                } else {
                    if (data?.getClipData() != null) {
                        val mClipData: ClipData = data?.getClipData()!!
                        for (i in 0 until mClipData.itemCount) {
                            val item = mClipData.getItemAt(i)
                            val uri: Uri = item.uri
                            storeBitMap(uri)
                        }
                    }
                }
                setAdapter()
            } else {
                showToast(requireContext(), "You haven't picked Image")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(requireContext(), "Something Went Wrong")
        }
    }
    private fun storeBitMap(uri: Uri){
        val bitmap= MediaStore.Images.Media.getBitmap(
            requireActivity().getContentResolver(),
            uri
        )
        val model = ImageModel()
        model.image=bitmap
        images.add(model)
    }

    private fun setAdapter() {
        mAdapter = GalleryAdapter(requireContext(), images)
        list_image_view.adapter = mAdapter
    }

}