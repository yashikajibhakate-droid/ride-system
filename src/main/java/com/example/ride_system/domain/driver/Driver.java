package com.example.ride_system.domain.driver;

import com.example.ride_system.domain.ride.Location;

import jakarta.persistence.*;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    

      @Embedded
    private Location location;

    protected Driver() {}

    public Driver(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = DriverStatus.OFFLINE; // DEFAULT
    }

    public void goOnline() {
        this.status = DriverStatus.ONLINE;
    }

    public void goOffline() {
        this.status = DriverStatus.OFFLINE;
    }

    // public void assignRide() {
    //     this.status = DriverStatus.ON_RIDE;
    // }

      public void updateLocation(Location location) {
        this.location = location;
    }

    public DriverStatus getStatus() {
        return status;
    }

     public Location getLocation() {
        return location;
    }

    

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }
}
