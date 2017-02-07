package com.finalproject.andreivancea.ntviewer.model;

/**
 * Created by andrei.vancea on 1/31/2017.
 */

public class NetworkNode {

    private String ipAddress;
    private boolean active;

    public NetworkNode(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
