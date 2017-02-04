package com.finalproject.andreivancea.ntviewer.model;

/**
 * Created by andrei.vancea on 1/31/2017.
 */

public class NetworkEndpoint {

    private String ipAddress;
    private double longitude;
    private double latitude;

    public NetworkEndpoint(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
