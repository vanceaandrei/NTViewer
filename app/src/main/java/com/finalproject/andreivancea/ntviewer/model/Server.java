package com.finalproject.andreivancea.ntviewer.model;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by andrei.vancea on 1/31/2017.
 */

public class Server extends NetworkNode {

    private MarkerOptions mapMarker;

    public Server(String ipAddress) {
        super(ipAddress);
    }

    public MarkerOptions getMapMarker() {
        return mapMarker;
    }

    public void setMapMarker(MarkerOptions mapMarker) {
        this.mapMarker = mapMarker;
    }

}
