package com.example.ride_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ride_system.domain.driver.*;
import com.example.ride_system.domain.ride.Location;
import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.domain.ride.RideStatus;
import com.example.ride_system.exception.BusinessException;
import com.example.ride_system.repository.*;
import com.example.ride_system.service.impl.DriverServiceImpl;

@ExtendWith(MockitoExtension.class)

public class DriverServiceTest {

    @Mock
    DriverRepository driverRepository;

    @Mock
    RideRepository rideRepository;

    @InjectMocks
    DriverServiceImpl driverService;

    @Test
    void offlineDriverCannotAcceptRide() {
        Driver driver = new Driver("John", "9876543210", "john@mail.com");
        driver.setStatus(DriverStatus.OFFLINE);

        when(driverRepository.findById(1L))
                .thenReturn(Optional.of(driver));

        assertThrows(IllegalStateException.class,
                () -> driverService.acceptRide(2L, 1L));
    }

        @Test
    void onlineDriverCanAcceptRide() {
        Driver driver =new Driver("John", "9876543210", "john@mail.com");
        driver.goOnline();

        Ride ride = mock(Ride.class);

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(rideRepository.findById(2L)).thenReturn(Optional.of(ride));
        when(rideRepository.save(any())).thenReturn(ride);

        Ride result = driverService.acceptRide(2L, 1L);

        verify(ride).accept(1L);
        assertNotNull(result);
    }

    @Test
    void driverShouldUpdateStatusToOnline() {
        Driver driver = new Driver("John", "9876543210", "john@mail.com");
        
        when(driverRepository.findById(1L))
                .thenReturn(Optional.of(driver));
        driverService.updateStatus(1L, DriverStatus.ONLINE);
        assertEquals(DriverStatus.ONLINE, driver.getStatus());
    }

    @Test
    void driverShouldUpdateStatusToOffline() {
        Driver driver = new Driver("John", "9876543210", "john@mail.com");

        when(driverRepository.findById(1L))
                .thenReturn(Optional.of(driver));
        driverService.updateStatus(1L, DriverStatus.OFFLINE);
        assertEquals(DriverStatus.OFFLINE, driver.getStatus());
    }

    @Test
    void driverShouldUpdateLocation() {
        Driver driver = new Driver("John", "9876543210", "john@gmail.com");

        when(driverRepository.findById(1L))
                .thenReturn(Optional.of(driver));
        Location newLocation = new Location(12.9, 77.6);
        driverService.updateLocation(1L, newLocation);  
        assertEquals(12.9, driver.getLocation().getLat());
        assertEquals(77.6, driver.getLocation().getLon());
    }

 
}