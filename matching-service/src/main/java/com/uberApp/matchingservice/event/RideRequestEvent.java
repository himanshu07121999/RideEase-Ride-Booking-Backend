package com.uberApp.matchingservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * event consumed from kafka topic : ride.request
 * published by ride service when a rider request a ride
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestEvent {
    private String riderId;
    private String rideId;
    private double pickUpLatitude;
    private double pickUpLongitude;
    private String pickUpAddress;
    private double dropLatitude;
    private double dropLongitude;
    private String dropAddress;
}
