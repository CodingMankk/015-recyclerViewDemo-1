package com.oztaking.www.multiplepicselectother;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author
 * @date
 */

public class MyApp extends Application {
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Fresco 建议放在这里
        Fresco.initialize(this);
    }
}


