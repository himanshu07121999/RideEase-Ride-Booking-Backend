package com.uberApp.matchingservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response received from location service
 * when querying for nearby driver
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearByDriverResponse {

    private String driverId;
    private double latitude;
    private double longitude;
    private double distanceInKm;
}
