/*
 * Copyright (c) 2019. Innoplexus Consulting Services Pvt. Ltd.
 * All rights reserved.
 */

package com.suraj.edelivery.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class GenericAdapter<T, D>(val context: Context, private var mArrayList: ArrayList<T>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract val layoutResId: Int

    abstract fun onBindData(model: T, position: Int, dataBinding: D)

    abstract fun onItemClick(model: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dataBinding =
            DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), layoutResId, parent, false)
        return ItemViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(
            mArrayList!!.get(position), position, (holder as GenericAdapter<*, *>.ItemViewHolder).mDataBinding as D
        )

        (holder.mDataBinding as ViewDataBinding).root.setOnClickListener {
            onItemClick(
                mArrayList!![position],
                position
            )
        }
    }

    fun addItems(arrayList: ArrayList<T>) {
        mArrayList = arrayList
        this.notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return mArrayList!![position]
    }

    override fun getItemCount(): Int {
        return mArrayList!!.size
    }

    internal inner class ItemViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        public var mDataBinding: D

        init {
            mDataBinding = binding as D
        }
    }


}
