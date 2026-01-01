package com.example.ride_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RiderCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @Email
    @NotBlank
    private String email;

    // Required by Jackson
    public RiderCreateRequest() {}

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
