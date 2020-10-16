/*
 * Copyright (c) 2019. Innoplexus Consulting Services Pvt. Ltd.
 * All rights reserved.
 */

package com.suraj.tracker.util;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.manojbhadane.holdy.Holdy;
import com.manojbhadane.holdy.R.id;
import com.suraj.tracker.R;

public class CommonHolderActivity extends AppCompatActivity {

    private Toolbar toolBar;

    public CommonHolderActivity() {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle b = this.getIntent().getBundleExtra("key_container");
        String title = b.getString("key_title");
        int theme = b.getInt("key_theme");
        boolean shouldShowToolbar = b.getBoolean("key_toolbar_visibility");
        boolean shouldShowToolbarBackBtn = b.getBoolean("key_toolbar_back_btn_visibility");
        String fragment = b.getString("key_fragment");
        int softInputMode = b.getInt("key_softinput_mode");
        int screenOrientation = b.getInt("key_screen_orientation_portrait");
        this.setTheme(theme);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_holder);
        if (softInputMode != 0) {
            this.getWindow().setSoftInputMode(softInputMode);
        }

        this.toolBar = (Toolbar)this.findViewById(id.toolBar);
        if (screenOrientation != -1) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }

        if (shouldShowToolbar) {
            this.toolBar.setVisibility(0);
            this.setSupportActionBar(this.toolBar);
            this.getSupportActionBar().setTitle(title);
            if (shouldShowToolbarBackBtn) {
                this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            this.applyFontForToolbarTitle(this.toolBar);
        } else {
            this.toolBar.setVisibility(8);
        }

        this.loadFragment(fragment, b);
    }

    public void loadFragment(String className, Bundle bundle) {
        try {
            FragmentManager manager = this.getSupportFragmentManager();
            Fragment fragment = (Fragment) Class.forName(className).newInstance();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(id.layContainer, fragment);
            transaction.commit();
        } catch (ClassNotFoundException var6) {
            var6.printStackTrace();
            Toast.makeText(this, "Invalid fragment", 0).show();
            this.finish();
        } catch (ClassCastException var7) {
            var7.printStackTrace();
            Toast.makeText(this, "Invalid fragment", 0).show();
            this.finish();
        } catch (Exception var8) {
            var8.printStackTrace();
            Toast.makeText(this, "Something went wrong", 0).show();
            this.finish();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 16908332:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void applyFontForToolbarTitle(Toolbar toolbar) {
        for(int i = 0; i < toolbar.getChildCount(); ++i) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView)view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    if (Holdy.sTypeface != null) {
                        tv.setTypeface(Holdy.sTypeface);
                    } else {
                        Log.e("Holdy", "Typeface: typeface is null");
                    }
                    break;
                }
            }
        }

    }

}
