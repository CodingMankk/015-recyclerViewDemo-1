package com.oztaking.www.recyclerviewdemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @function
 * @author
 */

public class RecyclerViewApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
