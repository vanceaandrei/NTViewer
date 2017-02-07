package com.finalproject.andreivancea.ntviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.finalproject.andreivancea.ntviewer.adapter.MarkerListAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarkerListActivity extends AppCompatActivity {

    private List<MarkerOptions> markers;
    private MarkerListAdapter adapter;
    boolean editMode = false;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        markers = getMarkersFromSharedPreferences();
        ListView markerListView = (ListView) findViewById(R.id.list_markers);
        adapter = new MarkerListAdapter(this, R.layout.list_item, markers);
        markerListView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.toggleEdit();
                editMode = !editMode;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (editMode) {
                        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.ic_menu_close_clear_cancel));
                    } else {
                        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.ic_menu_edit));
                    }
                }
            }
        });
    }

    private List<MarkerOptions> getMarkersFromSharedPreferences() {
        List<MarkerOptions> markers = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);
        Map<String, ?> markerMap = preferences.getAll();
        for (String ipAddress : markerMap.keySet()) {
            String[] position = ((String) markerMap.get(ipAddress)).split(",");
            markers.add(new MarkerOptions().title(ipAddress).position(new LatLng(Double.parseDouble(position[0]), Double.parseDouble(position[1]))));
        }
        return markers;
    }

}
