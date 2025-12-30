package com.example.ride_system.domain.ride;

import com.example.ride_system.domain.driver.Driver;
import com.example.ride_system.domain.rider.Rider;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "ride")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Driver driver;

    @Embedded
    private Location pickupLocation;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "lat", column = @Column(name = "dropoff_lat")),
        @AttributeOverride(name = "lon", column = @Column(name = "dropoff_lon"))
    })
    private Location dropLocation;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private Instant requestedAt;
    private Instant startedAt;
    private Instant completedAt;

    protected Ride() {}

    public Ride(Rider rider, Location pickup, Location drop) {
        this.rider = rider;
        this.pickupLocation = pickup;
        this.dropLocation = drop;
        this.status = RideStatus.REQUESTED;
        this.requestedAt = Instant.now();
    }

    public void accept(Driver driver) {
        if (status != RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride cannot be accepted");
        }
        this.driver = driver;
        this.status = RideStatus.ACCEPTED;
    }

    public void start() {
        if (status != RideStatus.ACCEPTED) {
            throw new IllegalStateException("Ride not accepted");
        }
        this.status = RideStatus.IN_PROGRESS;
        this.startedAt = Instant.now();
    }

    public void end(Location currentLocation) {
        if (!currentLocation.sameAs(dropLocation)) {
            throw new IllegalStateException("Not at drop location");
        }
        this.status = RideStatus.COMPLETED;
        this.completedAt = Instant.now();
    }
}
