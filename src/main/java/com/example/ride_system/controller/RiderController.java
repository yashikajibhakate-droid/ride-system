package com.example.ride_system.controller;

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

import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.dto.request.CreateRideRequestDTO;
import com.example.ride_system.dto.request.RiderCreateRequest;
import com.example.ride_system.service.interfaces.RiderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rides")
public class RiderController {

    private final RiderService riderService;

    public RiderController(RiderService riderService) {
        this.riderService = riderService;
    }

     @PostMapping("/rider/register")
    public ResponseEntity<Map<String, Long>> register(
            @Valid @RequestBody RiderCreateRequest request) {

        Long riderId = riderService.registerRider(request);
        return ResponseEntity.status(201)
                .body(Map.of("riderId", riderId));
    }

    @PostMapping("/ride")
    public ResponseEntity<Ride> create(@Valid @RequestBody CreateRideRequestDTO dto) {
        return ResponseEntity.status(201).body(riderService.requestRide(dto));
    }

    @GetMapping("/{rideId}")
    public Ride get(@PathVariable Long rideId) {
        return riderService.getRideDetails(rideId);
    }

    @PatchMapping("/{rideId}/cancel")
    public void cancel(
            @PathVariable Long rideId,
            @RequestParam Long riderId) {

        riderService.cancelRide(rideId, riderId);
    }
}

