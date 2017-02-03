package com.finalproject.andreivancea.ntviewer.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.finalproject.andreivancea.ntviewer.NetworkSketchViewActivity;

/**
 * Created by andrei.vancea on 2/1/2017.
 */
public class ViewSketchOnSnackbarActionClicked implements View.OnClickListener {

    private Activity activity;

    public ViewSketchOnSnackbarActionClicked(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(activity.getApplicationContext(), "GLSurfaceView activity", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(activity, NetworkSketchViewActivity.class);
        activity.startActivity(intent);
    }
}
