/*
 * Copyright (c) 2019. Innoplexus Consulting Services Pvt. Ltd.
 * All rights reserved.
 */

package com.suraj.tracker.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Toast;


public class CommonHolder {

    protected static final String KEY = "key_container";
    protected static final String KEY_TITLE = "key_title";
    protected static final String KEY_THEME = "key_theme";
    protected static final String KEY_FRAGMENT = "key_fragment";
    protected static final String KEY_SOFTINPUT_MODE = "key_softinput_mode";
    protected static final String KEY_TOOLBAR_VISIBILITY = "key_toolbar_visibility";
    protected static final String KEY_TOOLBAR_BACKBTN_VISIBILITY = "key_toolbar_back_btn_visibility";
    protected static final String KEY_SCREEN_ORIENTATION_PORTRAIT = "key_screen_orientation_portrait";

    public static Typeface sTypeface;
    private static int sTheme;

    private CommonHolder() {
    }


    public static CommonHolder.CommonHolderBuilder Builder(Context context) {
        return new CommonHolder.CommonHolderBuilder(context);
    }

    public static void init(int theme, Typeface typeface) {
        sTheme = theme;
        sTypeface = typeface;
    }

    public static void init(int theme) {
        sTheme = theme;
    }

    public static class CommonHolderBuilder {

        private int mSoftInputMode=0;
        private String mTitle;
        private Bundle mBundle;
        private Context mContext;
        private String mFragment = "";
        private int mScreenOrientation = -1;
        private int mTheme;
        private boolean mShowToolbar = true;
        private boolean mShowToolbarBackBtn = true;

        public CommonHolderBuilder(Context context) {
            mContext = context;
        }


        public void build() {
            if (mFragment.length() > 0) {
                Intent intent = new Intent(mContext, CommonHolderActivity.class);
                intent.putExtra(KEY, getBundle());
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, "Invalid fragment", Toast.LENGTH_SHORT).show();
            }
        }

        public String getTitle() {
            return mTitle;
        }

        public CommonHolder.CommonHolderBuilder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Bundle getBundle() {

            if (mBundle == null)
                mBundle = new Bundle();

            mBundle.putString(KEY_TITLE, getTitle());
            mBundle.putString(KEY_FRAGMENT, mFragment);
            mBundle.putInt(KEY_SOFTINPUT_MODE, mSoftInputMode);
            mBundle.putInt(KEY_SCREEN_ORIENTATION_PORTRAIT, mScreenOrientation);
            mBundle.putBoolean(KEY_TOOLBAR_VISIBILITY, isShowToolbar());
            mBundle.putBoolean(KEY_TOOLBAR_BACKBTN_VISIBILITY, isToolbarBackBtnShown());
            if (sTheme != 0)
                mBundle.putInt(KEY_THEME, sTheme);

            if (getTheme() != 0)
                mBundle.putInt(KEY_THEME, getTheme());

            return mBundle;
        }

        public CommonHolder.CommonHolderBuilder setBundle(Bundle bundle) {
            this.mBundle = bundle;
            return this;
        }

        public boolean isShowToolbar() {
            return mShowToolbar;
        }

        public CommonHolder.CommonHolderBuilder setShowToolbar(boolean showToolbar) {
            this.mShowToolbar = showToolbar;
            return this;
        }

        public CommonHolder.CommonHolderBuilder setFragment(String fragment) {
            this.mFragment = fragment;
            return this;
        }

        public boolean isToolbarBackBtnShown() {
            return mShowToolbarBackBtn;
        }

        public CommonHolder.CommonHolderBuilder setToolbarBackBtn(boolean showToolbarBackBtn) {
            this.mShowToolbarBackBtn = showToolbarBackBtn;
            return this;
        }

        public int getTheme() {
            return mTheme;
        }

        public CommonHolder.CommonHolderBuilder setTheme(int theme) {
            this.mTheme = theme;
            return this;
        }

        public CommonHolder.CommonHolderBuilder setSoftInputMode(int softInputMode) {
            mSoftInputMode = softInputMode;
            return this;
        }
        public CommonHolder.CommonHolderBuilder setOrientationToPortrait() {
            mScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            return this;
        }

    }
}
