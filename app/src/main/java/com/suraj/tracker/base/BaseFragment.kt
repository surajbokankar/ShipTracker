/*
 * Copyright (c) 2019. Innoplexus Consulting Services Pvt. Ltd.
 * All rights reserved.
 */

package com.suraj.fitstif.base

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding> : Fragment(){

    private var mDataBinding: B? = null
    private var mBroadCast: BroadcastReceiver? = null


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mDataBinding = DataBindingUtil.inflate<B>(inflater, getLayoutResId(), container, false)
        requireActivity()!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return mDataBinding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(mDataBinding)
        observeChanges()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun init(dataBinding: B?)

    abstract fun observeChanges()

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (mBroadCast != null)
                activity?.unregisterReceiver(mBroadCast)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}