package com.example.ride_system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ride_system.domain.driver.Driver;
import com.example.ride_system.domain.driver.DriverStatus;
import com.example.ride_system.domain.ride.Location;
import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.domain.ride.RideStatus;
import com.example.ride_system.domain.rider.Rider;
import com.example.ride_system.dto.request.DriverCreateRequest;
import com.example.ride_system.repository.DriverRepository;
import com.example.ride_system.repository.RideRepository;
import com.example.ride_system.service.interfaces.DriverService;
import com.example.ride_system.utils.DistanceUtil;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;

    public DriverServiceImpl(RideRepository rideRepository,
            DriverRepository driverRepository) {
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public Long registerDriver(DriverCreateRequest request) {
        Driver driver = new Driver(
                request.name,
                request.email,
                request.phone);
        return driverRepository.save(driver).getId();
    }

    @Override
    public void updateStatus(Long driverId, DriverStatus status) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow();
        driver.setStatus(status);
    }

    @Override
    public void updateLocation(Long driverId, Location location) {
        Driver driver = driverRepository.findById(driverId).orElseThrow();
        driver.updateLocation(location);
    }

       @Override
    public List<Ride> getAvailableRides(Long driverId) {

        // 1️⃣ Fetch driver
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Driver not found"));

        // 2️⃣ Driver must have location
        Location driverLocation = driver.getLocation();
        if (driverLocation == null) {
            throw new IllegalStateException("Driver location not set");
        }

        // 3️⃣ Fetch all REQUESTED rides
        List<Ride> requestedRides =
                rideRepository.findByStatus(RideStatus.REQUESTED);

        // 4️⃣ Filter by distance <= 50 meters
        return requestedRides.stream()
                .filter(ride -> {
                    double distance =
                            DistanceUtil.distanceInMeters(
                                    driverLocation.getLat(),
                                    driverLocation.getLon(),
                                    ride.getPickupLat(),
                                    ride.getPickupLon()
                            );
                    return distance <= 50;
                })
                .toList();
    }

    @Override
    public Ride acceptRide(Long rideId, Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow();

        if (driver.getStatus() != DriverStatus.ONLINE) {
            throw new IllegalStateException("Driver must be ONLINE");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow();

        ride.accept(driverId);
        return rideRepository.save(ride);
    }

    @Override
    public Ride beginRide(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow();
        ride.begin();
        return rideRepository.save(ride);
    }

    @Override
    public Ride endRide(Long rideId, Long driverId, Location location) {
        Ride ride = rideRepository.findById(rideId).orElseThrow();
        ride.end(location);
        return rideRepository.save(ride);
    }

    @Override
    public void cancelRide(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow();
        ride.cancelByDriver(driverId);
        rideRepository.save(ride);
    }
}
