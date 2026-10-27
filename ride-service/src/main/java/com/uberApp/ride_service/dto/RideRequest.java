package com.uberApp.ride_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {

    @NotBlank(message = "rider id is required")
    private String riderId;

    @NotNull(message = "latitude is required")
    private double pickUpLatitude;

    @NotNull(message = " longitude is required")
    private double pickUpLongitude;

    @NotNull(message = "pickupAddress  id is required")
    private String pickUpAddress;

    @NotNull(message = "dropLatitude is required")
    private double  dropLatitude;

    @NotNull(message = " dropLongitude is required")
    private double dropUpLongitude;

    @NotNull(message = "dropAddress  id is required")
    private String dropAddress;

}
