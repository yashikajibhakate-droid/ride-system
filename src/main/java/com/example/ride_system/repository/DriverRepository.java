package com.example.ride_system.repository;

import com.example.ride_system.domain.driver.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
