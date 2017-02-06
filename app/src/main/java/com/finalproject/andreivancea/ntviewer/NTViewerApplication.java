package com.finalproject.andreivancea.ntviewer;

import android.app.Application;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrei.vancea on 2/6/2017.
 */

public class NTViewerApplication extends Application {

    private static NTViewerApplication instance;
    List<MarkerOptions> markers;

    public static NTViewerApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        markers = new ArrayList<>();
    }

    public List<MarkerOptions> getMarkers() {
        return markers;
    }

    public void setMarkers(List<MarkerOptions> markers) {
        this.markers = markers;
    }

    public void saveMarker(MarkerOptions marker) {
        markers.add(marker);
    }
}
