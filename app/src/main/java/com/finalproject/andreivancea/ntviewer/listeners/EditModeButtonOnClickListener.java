package com.finalproject.andreivancea.ntviewer.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.finalproject.andreivancea.ntviewer.EditModeActivity;
import com.finalproject.andreivancea.ntviewer.MainActivity;

/**
 * Created by andrei.vancea on 1/31/2017.
 */
public class EditModeButtonOnClickListener implements View.OnClickListener {

    private Activity activity;

    public EditModeButtonOnClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity.getApplicationContext(), EditModeActivity.class);
        activity.startActivity(intent);
    }
}
