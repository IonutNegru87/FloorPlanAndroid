package com.adbelsham.HousePlans;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


public class HousePlanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(HousePlanApplication.this);
    }

}
