package com.example.samplegooglemaps.events;

// Starts fetching address for tap coordinates.
public class StartFetchingAddressEvent {

    // Tap coordinates
    double lat;
    double lon;

    public StartFetchingAddressEvent(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
