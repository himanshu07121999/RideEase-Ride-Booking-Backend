package com.uberApp.ride_service.model;


/**
 * FLOW->
 * REQUESTED -> MATCHING -> ACCPETED > DRIVER ARRIVING
 * > RIDE STARTED > COMPLETED
 * >CANCELLED (can happen at multiple stages)
 */
public enum RideStatus {

    REQUESTED,
    MATCHING,
    ACCEPTED,
    DRIVER_ARRIVING,
    RIDE_STARTED,
    COMPLETED,
    CANCELLED,


}
