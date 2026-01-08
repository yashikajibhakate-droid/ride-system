package com.example.ride_system.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.ride_system.domain.driver.*;
import com.example.ride_system.domain.ride.Location;

public class DriverTest {
    @Test
    void newDriverShouldBeOffline() {
        Driver driver = new Driver("John", "9876543210", "john@mail.com");
        assertEquals(DriverStatus.OFFLINE, driver.getStatus());
    }

    @Test
    void driverCanGoOnline() {
        Driver driver = new Driver("John", "9876543210", "john@mail.com");
        driver.goOnline();
        assertEquals(DriverStatus.ONLINE, driver.getStatus());
    }
    @Test
    void driverCanGoOffline() {
        Driver driver = new Driver("John", "9876543210", "john@mail.com");
        driver.goOffline();
        assertEquals(DriverStatus.OFFLINE, driver.getStatus());
    }

    @Test
    void driverLocationShouldUpdate() {
        Driver driver = new Driver("John", "9876543210", "john@mail.com");
        driver.updateLocation(new Location(12.9, 77.6));

        assertEquals(12.9, driver.getLocation().getLat());
        assertEquals(77.6, driver.getLocation().getLon());
    }
}
