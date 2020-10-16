package com.suraj.tracker

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.manojbhadane.holdy.Holdy
import com.suraj.tracker.util.CommonHolder

class App :Application() {

    companion object{
        lateinit var instance: App
        var typefaceBold: Typeface? = null
        var typefaceRegular: Typeface? = null
        lateinit var mContext: Context

        fun setContext(context: Context) {
            mContext = context
        }

        fun getActivityContext(): Context {
            return mContext
        }

    }
    override fun onCreate() {
        super.onCreate()
        instance=this
        typefaceRegular = ResourcesCompat.getFont(instance, R.font.roboto)
        typefaceBold = ResourcesCompat.getFont(instance, R.font.roboto)
        CommonHolder.init(R.style.HoldyTheme, typefaceRegular)
    }
}