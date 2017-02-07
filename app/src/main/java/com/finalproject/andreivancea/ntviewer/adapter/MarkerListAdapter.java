package com.finalproject.andreivancea.ntviewer.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalproject.andreivancea.ntviewer.R;
import com.finalproject.andreivancea.ntviewer.listeners.DeleteMarkerOnClickListener;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by andrei.vancea on 2/7/2017.
 */

public class MarkerListAdapter extends ArrayAdapter<MarkerOptions> implements DeleteMarkerOnClickListener.MarkerDeleteButtonOnClickListener {

    private boolean editMode;
    private MarkerOptions marker;

    public MarkerListAdapter(Context context, int resource, List<MarkerOptions> objects) {
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

        marker = getItem(position);

        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        TextView positionTextView = (TextView) v.findViewById(R.id.position);
        ImageView deleteButton = (ImageView) v.findViewById(R.id.delete_btn);
        if (titleTextView != null) {
            titleTextView.setText(marker.getTitle());
        }

        if (positionTextView != null) {
            positionTextView.setText("Lat:" + marker.getPosition().latitude + ", Lon:" + marker.getPosition().longitude);
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
        MarkerOptions marker = getItem(position);
        remove(marker);
        removeMarkerFromSharedPreferences(marker);
        notifyDataSetChanged();
    }

    private void removeMarkerFromSharedPreferences(MarkerOptions marker) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getResources().getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(marker.getTitle());
        editor.commit();
    }
}
