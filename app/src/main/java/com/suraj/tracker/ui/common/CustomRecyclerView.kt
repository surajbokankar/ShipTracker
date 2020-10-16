package com.suraj.tracker.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suraj.tracker.R

class CustomRecyclerView:RecyclerView {
    private val VERTICAL: Int = 1
    private val HORIZONTAL: Int = 2

    private var mEmptyView: View? = null
    private var mNeedToHideView: View? = null
    private var itemCount=0

    private val mEmptyObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            val adapter = adapter
            itemCount=adapter!!.itemCount
            if (adapter != null && mEmptyView != null) {
                if (adapter.itemCount == 0) {
                    mEmptyView!!.visibility = View.VISIBLE
                    this@CustomRecyclerView.setVisibility(View.GONE)
                    if (mNeedToHideView != null)
                        mNeedToHideView!!.visibility = View.GONE
                } else {
                    mEmptyView!!.visibility = View.GONE
                    this@CustomRecyclerView.setVisibility(View.VISIBLE)
                    if (mNeedToHideView != null)
                        mNeedToHideView!!.visibility = View.VISIBLE
                }
            }
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView)
        val orientation = a.getInt(R.styleable.CustomRecyclerView_listOrientation, VERTICAL)
        val addItemDecorator = a.getBoolean(R.styleable.CustomRecyclerView_addItemDecorator, true)
        val applyAnimation = a.getBoolean(R.styleable.CustomRecyclerView_applyAnimation, true)
        val colCount = a.getInt(R.styleable.CustomRecyclerView_num, 2)

        /**
         * add item decorator
         */
        if (addItemDecorator)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        /**
         * apply list animation
         */


        /**
         * set list orientation
         */
        if (orientation == VERTICAL) {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        } else if(orientation==2) {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }else{
            this.layoutManager = GridLayoutManager(context, colCount)
        }

        a.recycle()
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(mEmptyObserver)

        mEmptyObserver.onChanged()
    }

    fun getlayoutManager(): RecyclerView.LayoutManager {
        return this.layoutManager!!
    }

    /**
     * fun will show emptyView and by default hide recyclerView
     */
    fun setEmptyView(emptyView: View) {
        this.mEmptyView = emptyView
    }

    /**
     * fun will show emptyView & hide recyclerView and what ever view passed as needToHideView
     *
     */
    fun setEmptyView(emptyView: View, needToHideView: View) {
        this.mEmptyView = emptyView
        this.mNeedToHideView = needToHideView
    }
}