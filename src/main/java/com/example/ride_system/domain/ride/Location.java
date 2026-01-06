package com.example.ride_system.domain.ride;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Embeddable
public class Location {

     @DecimalMin("-90.0") @DecimalMax("90.0")
    private double lat;

        @DecimalMin("-180.0") @DecimalMax("180.0")

    private double lon;

    protected Location() {}

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean sameAs(Location other) {
        return Double.compare(lat, other.lat) == 0 &&
               Double.compare(lon, other.lon) == 0;
    }
}
