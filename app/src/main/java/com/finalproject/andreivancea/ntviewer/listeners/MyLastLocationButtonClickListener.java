package com.finalproject.andreivancea.ntviewer.listeners;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by andrei.vancea on 1/31/2017.
 */

public class MyLastLocationButtonClickListener implements GoogleMap.OnMyLocationButtonClickListener {
    private static final String TAG = "MyLocationClick";
    private Activity activity;
    private Location mLastLocation;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    public MyLastLocationButtonClickListener(Activity activity, GoogleMap mMap, GoogleApiClient mGoogleApiClient, Location mLastLocation) {
        this.activity = activity;
        this.mMap = mMap;
        this.mGoogleApiClient = mGoogleApiClient;
        this.mLastLocation = mLastLocation;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Not enough permissions for getting location");
            return false;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 7);
            mMap.animateCamera(cameraUpdate1);
        }
        return true;
    }
}
