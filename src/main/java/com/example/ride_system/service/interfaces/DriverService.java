package com.example.ride_system.service.interfaces;

import java.util.List;

import com.example.ride_system.domain.driver.DriverStatus;
import com.example.ride_system.domain.ride.Location;
import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.dto.request.DriverCreateRequest;

public interface DriverService {

    Long registerDriver(DriverCreateRequest request);

    void updateStatus(Long driverId, DriverStatus status);

    void updateLocation(Long driverId, Location location);

    List<Ride> getAvailableRides(Long driverId);

    Ride acceptRide(Long rideId, Long driverId);

    Ride beginRide(Long rideId, Long driverId);

    Ride endRide(Long rideId, Long driverId, Location location);

    void cancelRide(Long rideId, Long driverId);
}
