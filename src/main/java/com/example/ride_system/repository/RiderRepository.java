package com.example.ride_system.repository;

import com.example.ride_system.domain.rider.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {
}
