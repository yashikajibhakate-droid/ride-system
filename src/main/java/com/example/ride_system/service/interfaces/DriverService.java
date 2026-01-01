package com.example.ride_system.service.interfaces;

import com.example.ride_system.domain.ride.Location;
import com.example.ride_system.domain.ride.Ride;

public interface DriverService {

    Ride acceptRide(Long rideId, Long driverId);

    void beginRide(Long rideId, Long driverId);

    void endRide(Long rideId, Long driverId, Location location);

    void cancelRide(Long rideId, Long driverId);
}
