package com.uberApp.ride_service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event published to kafka when a ride is requested
 * matching service consumes this event
 * TOPIC : ride.request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RideRequestEvent {

    private String rideId;
    private String riderId;
    //pickup
    private double pickUpLatitude;
    private double pickUpLongitude;
    private String pickUpAddress;
    //drop
    private double dropLatitude;
    private double dropUpLongitude;
    private String dropAddress;
}
