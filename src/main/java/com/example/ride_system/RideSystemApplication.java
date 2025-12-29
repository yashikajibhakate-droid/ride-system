package com.example.ride_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RideSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideSystemApplication.class, args);
		System.out.println("Ride Hailing API is running...");
	}

}
