package com.example.ride_system.repository;

import com.example.ride_system.domain.driver.DriverLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverLocationRepository
        extends JpaRepository<DriverLocation, Long> {
}
