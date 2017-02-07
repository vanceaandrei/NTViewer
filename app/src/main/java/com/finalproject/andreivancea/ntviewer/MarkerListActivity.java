package com.finalproject.andreivancea.ntviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.finalproject.andreivancea.ntviewer.adapter.MarkerListAdapter;
import com.finalproject.andreivancea.ntviewer.model.Server;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarkerListActivity extends AppCompatActivity {

    private MarkerListAdapter adapter;
    boolean editMode = false;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.view_markers_activity_name);
        setSupportActionBar(toolbar);

        List<Server> servers = getServersFromSharedPreferences();

        ListView markerListView = (ListView) findViewById(R.id.list_markers);
        adapter = new MarkerListAdapter(this, R.layout.list_item, servers);
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

    private List<Server> getServersFromSharedPreferences() {
        List<Server> servers = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);
        Map<String, ?> markerMap = preferences.getAll();
        for (String ipAddress : markerMap.keySet()) {
            String[] position = ((String) markerMap.get(ipAddress)).split(",");
            MarkerOptions m = new MarkerOptions().title(ipAddress).position(new LatLng(Double.parseDouble(position[0]), Double.parseDouble(position[1])));
            Server server = new Server(ipAddress);
            server.setMapMarker(m);
            servers.add(server);
        }
        return servers;
    }

}
