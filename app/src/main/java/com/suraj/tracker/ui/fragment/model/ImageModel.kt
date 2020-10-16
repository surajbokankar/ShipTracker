package com.suraj.tracker.ui.fragment.model

import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class ImageModel {
    lateinit var image:Bitmap

    companion object {
        @JvmStatic
        @BindingAdapter("itemView")
        fun setItemView(view: AppCompatImageView, data: Bitmap) {
            try {
                Glide.with(view.context).asBitmap().load(data)
                    .into(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}