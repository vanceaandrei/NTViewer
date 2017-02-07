package com.finalproject.andreivancea.ntviewer;

import android.app.Application;

/**
 * Created by andrei.vancea on 2/6/2017.
 */

public class NTViewerApplication extends Application {

    private static NTViewerApplication instance;

    public static NTViewerApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
