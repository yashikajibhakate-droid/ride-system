package com.example.ride_system.service.impl;

import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.domain.rider.Rider;
import com.example.ride_system.dto.request.CreateRideRequestDTO;
import com.example.ride_system.dto.request.RiderCreateRequest;
import com.example.ride_system.exception.ApiErrorCode;
import com.example.ride_system.exception.BusinessException;
import com.example.ride_system.repository.RideRepository;
import com.example.ride_system.repository.RiderRepository;
import com.example.ride_system.service.interfaces.RiderService;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RiderServiceImpl implements RiderService {

    private final RideRepository rideRepository;
    private final RiderRepository riderRepository;

    public RiderServiceImpl(RideRepository rideRepository,
            RiderRepository riderRepository) {
        this.rideRepository = rideRepository;
        this.riderRepository = riderRepository;
    }

    @Override
    public Long registerRider(RiderCreateRequest request) {

        if (riderRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(
                    ApiErrorCode.RIDER_ALREADY_EXISTS,
                    "Rider with this email already exists",
                    HttpStatus.CONFLICT);
        }

        Rider rider = new Rider(
                request.getName(),
                request.getPhone(),
                request.getEmail());
        Rider savedRider = riderRepository.save(rider);

        // ✅ extract ID from entity
        return savedRider.getId();
    }

    @Override
    public Ride requestRide(CreateRideRequestDTO request) {

        if (!riderRepository.existsById(request.getRiderId())) {
            throw new IllegalArgumentException("Invalid riderId");
        }

        Ride ride = Ride.request(
                request.getRiderId(),
                request.getPickup(),
                request.getDropoff(),
                request.getPickupLocation(),
                request.getDropoffLocation());

        return rideRepository.save(ride);
    }

    @Override
    public Ride getRideDetails(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));
    }

    @Override
    public void cancelRide(Long rideId, Long riderId) {
        Ride ride = getRideDetails(rideId);
        ride.cancelByRider(riderId);
        rideRepository.save(ride);
    }
}
