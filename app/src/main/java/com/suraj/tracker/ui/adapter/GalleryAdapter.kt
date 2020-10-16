package com.suraj.tracker.ui.adapter

import android.content.Context
import com.suraj.edelivery.base.GenericAdapter
import com.suraj.tracker.R
import com.suraj.tracker.databinding.ListItemGalleryBinding
import com.suraj.tracker.ui.fragment.model.ImageModel

class GalleryAdapter(context: Context,list:ArrayList<ImageModel>):GenericAdapter<ImageModel,ListItemGalleryBinding>(context,list) {
    override val layoutResId: Int
        get() = R.layout.list_item_gallery

    override fun onBindData(model: ImageModel, position: Int, dataBinding: ListItemGalleryBinding) {
        dataBinding.item=model
    }

    override fun onItemClick(model: ImageModel, position: Int) {
        /*TODO("Not yet implemented")*/
    }
}