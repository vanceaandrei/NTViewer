package com.finalproject.andreivancea.ntviewer.listeners;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;

import com.finalproject.andreivancea.ntviewer.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by andrei.vancea on 2/1/2017.
 */
public class ShowSnackBarOnMarkerClickListener implements GoogleMap.OnMarkerClickListener {

    private static final String ACTION_VIEW_SKETCH = "View Sketch";

    private Activity activity;

    public ShowSnackBarOnMarkerClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.map), marker.getTitle(), Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.setAction(ACTION_VIEW_SKETCH, new ViewSketchOnSnackbarActionClicked(activity));
        snackbar.show();
        return true;
    }
}
