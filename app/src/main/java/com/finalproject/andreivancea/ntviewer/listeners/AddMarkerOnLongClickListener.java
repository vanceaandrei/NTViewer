package com.finalproject.andreivancea.ntviewer.listeners;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.finalproject.andreivancea.ntviewer.dialogs.IPSetDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by andrei.vancea on 1/31/2017.
 */

public class AddMarkerOnLongClickListener implements GoogleMap.OnMapLongClickListener, IPSetDialog.IPSetDialogListener {

    private FragmentActivity activity;
    private GoogleMap mMap;
    private LatLng choosedLocation;


    public AddMarkerOnLongClickListener(GoogleMap map) {
        this.mMap = map;
    }

    public AddMarkerOnLongClickListener(FragmentActivity activity, GoogleMap mMap) {
        this.activity = activity;
        this.mMap = mMap;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        choosedLocation = latLng;
        DialogFragment fragment = new IPSetDialog();
        ((IPSetDialog) fragment).attachListener(this);
        fragment.show(activity.getSupportFragmentManager(), "IP Address");
    }

    @Override
    public void onDialogPositiveClick(String ipAddress) {
        mMap.addMarker(new MarkerOptions().position(choosedLocation).title(ipAddress));
    }
}
