package com.finalproject.andreivancea.ntviewer.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalproject.andreivancea.ntviewer.R;
import com.finalproject.andreivancea.ntviewer.listeners.DeleteMarkerOnClickListener;
import com.finalproject.andreivancea.ntviewer.model.Server;

import java.util.List;

/**
 * Created by andrei.vancea on 2/7/2017.
 */

public class MarkerListAdapter extends ArrayAdapter<Server> implements DeleteMarkerOnClickListener.MarkerDeleteButtonOnClickListener {

    private static final String STATUS_INACTIVE = "Inactive";
    private static final String STATUS_ACTIVE = "Active";

    private boolean editMode;

    public MarkerListAdapter(Context context, int resource, List<Server> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater li;
            li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.list_item, null);
        }

        Server server = getItem(position);

        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        TextView positionTextView = (TextView) v.findViewById(R.id.position);
        TextView statusTextView = (TextView) v.findViewById(R.id.active);
        ImageView deleteButton = (ImageView) v.findViewById(R.id.delete_btn);
        if (titleTextView != null) {
            titleTextView.setText(server.getIpAddress());
        }

        if (positionTextView != null) {
            positionTextView.setText("Lat:" + server.getMapMarker().getPosition().latitude + ", Lon:" + server.getMapMarker().getPosition().longitude);
        }

        if (statusTextView != null) {
            if (!server.isActive()) {
                statusTextView.setTextColor(Color.RED);
                statusTextView.setText(STATUS_INACTIVE);
            } else {
                statusTextView.setTextColor(Color.GREEN);
                statusTextView.setText(STATUS_ACTIVE);
            }
        }

        if (deleteButton != null) {
            deleteButton.setOnClickListener(new DeleteMarkerOnClickListener(this, position));
            if (editMode) {
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                deleteButton.setVisibility(View.INVISIBLE);
            }
        }
        return v;
    }

    public void toggleEdit() {
        editMode = !editMode;
        notifyDataSetChanged();
    }

    @Override
    public void deleteMarkerButtonClicked(int position) {
        Server marker = getItem(position);
        remove(marker);
        removeMarkerFromSharedPreferences(marker);
        notifyDataSetChanged();
    }

    private void removeMarkerFromSharedPreferences(Server marker) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getResources().getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(marker.getMapMarker().getTitle());
        editor.commit();
    }
}
