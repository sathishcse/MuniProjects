package com.muni.in.data;

import android.app.Application;

import com.muni.in.utils.TypefaceUtil;

/**
 * Created by sathish on 9/5/18.
 */

public class MuniApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/robotoregular.ttf");
    }
}
