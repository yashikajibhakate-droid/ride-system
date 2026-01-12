package com.example.ride_system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    @PostMapping("/register")
    public Map<String, Long> register(@Valid @RequestBody DriverCreateRequest request) {
        Long id = driverService.registerDriver(request);
        return Map.of("driverId", id);
    }

    @PatchMapping("/{driverId}/status")
    public ResponseEntity<Map<String, String>> updateStatus(
            @PathVariable("driverId") Long driverId,
            @RequestParam DriverStatus status) {

        driverService.updateStatus(driverId, status);
        return ResponseEntity.ok(
                Map.of("message", "Driver status updated successfully"));
    }

    @PatchMapping("/{driverId}/location")
    public ResponseEntity<Map<String, String>> updateLocation(
            @PathVariable("driverId") Long driverId,
            @Valid @RequestBody Location location) {

        driverService.updateLocation(driverId, location);

        return ResponseEntity.ok(
                Map.of("message", "Location updated successfully"));
    }

    @GetMapping("/{driverId}/rides")
    public List<Ride> availableRides(@PathVariable("driverId") Long driverId) {
        return driverService.getAvailableRides(driverId);
    }

    @PostMapping("/{driverId}/rides/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(
            @PathVariable("driverId") Long driverId,
            @PathVariable("rideId") Long rideId) {
        return ResponseEntity.ok(driverService.acceptRide(rideId, driverId));
    }

    @PatchMapping("/{driverId}/rides/{rideId}/cancel")
    public ResponseEntity<Map<String, String>> cancelRide(
            @PathVariable("driverId") Long driverId,
            @PathVariable("rideId") Long rideId) {

        driverService.cancelRide(rideId, driverId);
        return ResponseEntity.ok().body(Map.of("message", "Ride cancelled successfully"));
    }

    @PostMapping("/{driverId}/rides/{rideId}/start")
    public ResponseEntity<Ride> startRide(
            @PathVariable("driverId") Long driverId,
            @PathVariable("rideId") Long rideId) {
        return ResponseEntity.ok(driverService.beginRide(rideId, driverId));
    }

    @PostMapping("/{driverId}/rides/{rideId}/end")
    public ResponseEntity<Ride> endRide(
            @PathVariable("driverId") Long driverId,
            @PathVariable("rideId") Long rideId,
            @RequestBody Location location) {

        Ride updatedRide = driverService.endRide(rideId, driverId, location);
        return ResponseEntity.ok(updatedRide);
    }
}
