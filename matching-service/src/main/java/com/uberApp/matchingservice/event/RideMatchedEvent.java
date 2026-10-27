package com.uberApp.matchingservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event published to kafka topic : ride.matched
 * consumed by ride service to update ride with assigned driver
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RideMatchedEvent {
    private String rideId;
    private String riderId;
    private String driverId;
    private double driverLatitude;
    private double driverLongitude;
    private double distanceToPickUpKm;

}
