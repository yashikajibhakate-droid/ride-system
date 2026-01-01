package com.example.ride_system.service.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ride_system.domain.driver.Driver;
import com.example.ride_system.domain.driver.DriverStatus;
import com.example.ride_system.domain.ride.Location;
import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.repository.DriverRepository;
import com.example.ride_system.repository.RideRepository;
import com.example.ride_system.service.interfaces.DriverService;

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
    public void beginRide(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow();
        ride.begin();
        rideRepository.save(ride);
    }

    @Override
    public void endRide(Long rideId, Long driverId, Location location) {
        Ride ride = rideRepository.findById(rideId).orElseThrow();
        ride.end(location);
        rideRepository.save(ride);
    }

    @Override
    public void cancelRide(Long rideId, Long driverId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow();
        ride.cancelByDriver(driverId);
        rideRepository.save(ride);
    }
}

