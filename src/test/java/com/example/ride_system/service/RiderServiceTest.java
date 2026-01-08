package com.example.ride_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ride_system.domain.ride.Location;
import com.example.ride_system.domain.ride.Ride;
import com.example.ride_system.domain.ride.RideStatus;
import com.example.ride_system.dto.request.CreateRideRequestDTO;
import com.example.ride_system.exception.BusinessException;
import com.example.ride_system.repository.*;
import com.example.ride_system.service.impl.RiderServiceImpl;

@ExtendWith(MockitoExtension.class)


public class RiderServiceTest {


     @Mock
    private RideRepository rideRepository;

    @Mock
    private RiderRepository riderRepository;

    @InjectMocks
    private RiderServiceImpl riderService;

     @Test
    void shouldThrowIfRiderDoesNotExist() {
        CreateRideRequestDTO dto = new CreateRideRequestDTO();
        dto.setRiderId(1L);

        when(riderRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> riderService.requestRide(dto));
    }

    @Test
     void shouldCreateRideSuccessfully() {
        CreateRideRequestDTO dto = new CreateRideRequestDTO();
        dto.setRiderId(1L);
        dto.setPickup("Home");
        dto.setDropoff("Office");
        dto.setPickupLocation(new Location(12.9, 77.6));
        dto.setDropoffLocation(new Location(12.8, 77.5));

        when(riderRepository.existsById(1L)).thenReturn(true);
        when(rideRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Ride ride = riderService.requestRide(dto);

        assertEquals("Home", ride.getPickup());
        assertEquals("Office", ride.getDropoff());
    }

    @Test
    void riderShouldCancelRideSuccessfully() {
        Long rideId = 1L;
        Long riderId = 1L;

        Ride ride = Ride.request(
                riderId,
                "Home",
                "Office",
                new Location(12.9, 77.6),
                new Location(12.8, 77.5));

        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        riderService.cancelRide(rideId, riderId);

        assertEquals(RideStatus.CANCELLED, ride.getStatus());
    }


}
