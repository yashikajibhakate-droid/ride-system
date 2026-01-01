package com.example.ride_system.domain.driver;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "driver_location")
public class DriverLocation {

    @Id
    private Long driverId;

    private double lat;
    private double lon;

    private Instant updatedAt;

    protected DriverLocation() {}

    public DriverLocation(Long driverId, double lat, double lon) {
        this.driverId = driverId;
        this.lat = lat;
        this.lon = lon;
        this.updatedAt = Instant.now();
    }

    public void update(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.updatedAt = Instant.now();
    }
}
