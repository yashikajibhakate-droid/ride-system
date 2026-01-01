package com.example.ride_system.dto.request;

import com.example.ride_system.domain.ride.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class CreateRideRequestDTO {
  @NotNull
    public Long riderId;

    @NotBlank
    public String pickup;

    @NotBlank
    public String dropoff;

    @Valid
    @NotNull
    public Location pickupLocation;

    @Valid
    @NotNull
    public Location dropoffLocation;

    public Long getRiderId() {
        return riderId;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Location getDropoffLocation() {
        return dropoffLocation;
    }
}
