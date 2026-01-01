package com.example.ride_system.service.interfaces;

import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.dto.request.CreateRideRequestDTO;
import com.example.ride_system.dto.request.RiderCreateRequest;

public interface RiderService {

    Long registerRider(RiderCreateRequest request);

    Ride requestRide(CreateRideRequestDTO request);

    Ride getRideDetails(Long rideId);

    void cancelRide(Long rideId, Long riderId);
}
