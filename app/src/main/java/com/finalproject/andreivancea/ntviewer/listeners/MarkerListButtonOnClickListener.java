package com.finalproject.andreivancea.ntviewer.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.finalproject.andreivancea.ntviewer.MainActivity;
import com.finalproject.andreivancea.ntviewer.MarkerListActivity;

/**
 * Created by andrei.vancea on 2/7/2017.
 */
public class MarkerListButtonOnClickListener implements View.OnClickListener {

    private Activity activity;

    public MarkerListButtonOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, MarkerListActivity.class);
        activity.startActivity(intent);
    }

}
