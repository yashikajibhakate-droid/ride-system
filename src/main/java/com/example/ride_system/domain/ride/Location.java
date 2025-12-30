package com.example.ride_system.domain.ride;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

    private double lat;
    private double lon;

    protected Location() {}

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public boolean sameAs(Location other) {
        return Double.compare(lat, other.lat) == 0 &&
               Double.compare(lon, other.lon) == 0;
    }
}
