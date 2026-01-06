package com.example.ride_system.domain.ride;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long riderId;
    private Long driverId;

    private String pickup;
    private String dropoff;

    private double pickupLat;
    private double pickupLon;

    private double dropoffLat;
    private double dropoffLon;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private Instant requestedAt;
    private Instant acceptedAt;
    private Instant startedAt;
    private Instant endedAt;

        public Long getId() { return id; }
    public String getPickup() { return pickup; }
    public String getDropoff() { return dropoff; }
    public Long getRiderId() { return riderId; }
    public Long getDriverId() { return driverId; }
    public Instant getRequestedAt() { return requestedAt; }
    public Instant getAcceptedAt() { return acceptedAt; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getEndedAt() { return endedAt; }
    public RideStatus getStatus() { return status; }
    public double getPickupLat() { return pickupLat; }
    public double getPickupLon() { return pickupLon; }
    public double getDropoffLat() { return dropoffLat; }
    public double getDropoffLon() { return dropoffLon; }


    protected Ride() {}

    /* Factory method – ride creation */
    public static Ride request(
            Long riderId,
            String pickup,
            String dropoff,
            Location pickupLocation,
            Location dropoffLocation
    ) {
        Ride ride = new Ride();
        ride.riderId = riderId;
        ride.pickup = pickup;
        ride.dropoff = dropoff;
        ride.pickupLat = pickupLocation.getLat();
        ride.pickupLon = pickupLocation.getLon();
        ride.dropoffLat = dropoffLocation.getLat();
        ride.dropoffLon = dropoffLocation.getLon();
        ride.status = RideStatus.REQUESTED;
        ride.requestedAt = Instant.now();
        return ride;
    }

    /* ---- State Transitions ---- */

    public void accept(Long driverId) {
        if (status != RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride cannot be accepted");
        }
        this.driverId = driverId;
        this.status = RideStatus.ACCEPTED;
        this.acceptedAt = Instant.now();
    }

    public void begin() {
        if (status == RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride not accepted yet");
        }
        if(status == RideStatus.IN_PROGRESS) {
            throw new IllegalStateException("Ride already in progress");
        }
        if(status == RideStatus.COMPLETED) {
            throw new IllegalStateException("Ride already completed");
        }
        if(status == RideStatus.CANCELLED) {
            throw new IllegalStateException("Ride is cancelled");
        }
        this.status = RideStatus.IN_PROGRESS;
        this.startedAt = Instant.now();
    }

    public void end(Location currentLocation) {
        if (status != RideStatus.IN_PROGRESS) {
            throw new IllegalStateException("Ride cannot be ended as it is not in progress");
        }

        if (!isAtDropLocation(currentLocation)) {
            throw new IllegalStateException("Driver not at drop location");
        }

        this.status = RideStatus.COMPLETED;
        this.endedAt = Instant.now();
    }

   public void cancelByRider(Long riderId) {
    if (!this.riderId.equals(riderId)) {
        throw new IllegalStateException("Rider not authorized");
    }
    if (status != RideStatus.REQUESTED) {
        throw new IllegalStateException("Ride cannot be cancelled now");
    }
    this.status = RideStatus.CANCELLED;
}


    public void cancelByDriver(Long driverId) {
        if (!this.driverId.equals(driverId)) {
            throw new IllegalArgumentException("Invalid driver");
        }
        if (status == RideStatus.COMPLETED) {
            throw new IllegalStateException("Completed ride cannot be cancelled");
        }
        this.status = RideStatus.DRIVER_CANCELLED;
    }

    private boolean isAtDropLocation(Location location) {
        return Double.compare(location.getLat(), dropoffLat) == 0
            && Double.compare(location.getLon(), dropoffLon) == 0;
    }
}
