package com.example.ride_system.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.ride_system.domain.ride.*;

public class RideTest {

    @Test
    void shouldCreateRideInRequestedState() {
        Ride ride = Ride.request(
                1L,
                "Office",
                "Home",
                new Location(190.0, 77.6),
                new Location(91, 77.5));

        assertEquals(RideStatus.REQUESTED, ride.getStatus());
        assertNotNull(ride.getRequestedAt());
    }

    @Test
    void shouldNotEndRideIfNotInProgress() {
        Ride ride = Ride.request(
                1L,
                "Office",
                "Home",
                new Location(190.0, 77.6),
                new Location(91, 77.5));

        Exception ex = assertThrows(
                RuntimeException.class,
                () -> ride.end(new Location(12.8, 77.5)));

        assertTrue(ex.getMessage().contains("Ride cannot be ended as it is not in progress"));
    }

    @Test
    void shouldEndRideOnlyAtDropLocation() {
        Ride ride = Ride.request(
                1L,
                "Office",
                "Home",
                new Location(190.0, 77.6),
                new Location(91, 77.5));

        ride.accept(10L);
        ride.begin();

        Exception ex = assertThrows(
                RuntimeException.class,
                () -> ride.end(new Location(23.32, 23.5)));

        assertTrue(ex.getMessage().contains("drop"));
    }

}
