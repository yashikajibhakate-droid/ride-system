package com.example.ride_system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ride_system.domain.driver.DriverStatus;
import com.example.ride_system.domain.ride.Location;
import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.dto.request.DriverCreateRequest;
import com.example.ride_system.service.interfaces.DriverService;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/register")
    public Map<String, Long> register(@RequestBody DriverCreateRequest request) {
        Long id = driverService.registerDriver(request);
        return Map.of("driverId", id);
    }

    @PatchMapping("/{driverId}/status")
    public void updateStatus(
            @PathVariable Long driverId,
            @RequestParam DriverStatus status) {
        driverService.updateStatus(driverId, status);
    }

    @PatchMapping("/{driverId}/location")
    public void updateLocation(
            @PathVariable Long driverId,
            @RequestBody Location location) {
        driverService.updateLocation(driverId, location);
    }

    @GetMapping("/{driverId}/rides")
    public List<Ride> availableRides(@PathVariable Long driverId) {
        return driverService.getAvailableRides(driverId);
    }

    @PostMapping("/{driverId}/rides/{rideId}/accept")
    public Ride acceptRide(
            @PathVariable Long driverId,
            @PathVariable Long rideId) {
        return driverService.acceptRide(rideId, driverId);
    }

    @PostMapping("/{driverId}/rides/{rideId}/start")
    public void startRide(
            @PathVariable Long driverId,
            @PathVariable Long rideId) {
        driverService.beginRide(rideId, driverId);
    }

    @PostMapping("/{driverId}/rides/{rideId}/end")
    public void endRide(
            @PathVariable Long driverId,
            @PathVariable Long rideId,
            @RequestBody Location location) {
        driverService.endRide(rideId, driverId, location);
    }
}
