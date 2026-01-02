package com.example.ride_system.repository;

import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.domain.ride.RideStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByStatus(RideStatus status);
}
