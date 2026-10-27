package com.uberApp.ride_service.service;

import com.uberApp.ride_service.dto.RideRequest;
import com.uberApp.ride_service.dto.RideResponse;
import com.uberApp.ride_service.event.RideRequestEvent;
import com.uberApp.ride_service.model.Ride;
import com.uberApp.ride_service.model.RideStatus;
import com.uberApp.ride_service.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class RideService {
 private final RideRepository rideRepository;
 private final KafkaTemplate<String , RideRequestEvent> kafkaTemplate;

 private static final String RIDE_REQUESTED_TOPIC = "ride.request";

    /**
     * create ride in DB with REQUESTED STATUS
     */
    public RideResponse requestRide(RideRequest request) {
       log.info("Ride request from rider" , request.getRiderId());

       //Step1: save ride to DB

        Ride ride = new Ride();
        ride.setRiderId(request.getRiderId());
        ride.setPickUpLatitude(request.getPickUpLatitude());
        ride.setPickUpLongitude(request.getPickUpLongitude());
        ride.setPickUpAddress(request.getPickUpAddress());
        ride.setDropLatitude(request.getDropLatitude());
        ride.setDropUpLongitude(request.getDropUpLongitude());
        ride.setDropAddress(request.getDropAddress());
        ride.setStatus(RideStatus.REQUESTED);
        ride.setEstimatedFare(calculateEstimateFare(request));

        Ride savedRide = rideRepository.save(ride);

        // Step 2: publish event to kafka
        // matching service will consume this and find nearest driver

        RideRequestEvent event = new RideRequestEvent(
         savedRide.getId(),
         savedRide.getRiderId(),
        savedRide.getPickUpLatitude(),
        savedRide.getPickUpLongitude(),
        savedRide.getPickUpAddress(),
        savedRide.getDropLatitude(),
        savedRide.getDropUpLongitude(),
        savedRide.getDropAddress()

        );

        kafkaTemplate.send(RIDE_REQUESTED_TOPIC, savedRide.getId(), event);
        log.info("Ride requested event published to kafka for ride {}",savedRide.getId());

        //update status to matching

        savedRide.setStatus(RideStatus.MATCHING);
        rideRepository.save(savedRide);

        return mapToResponse(savedRide);

    }

    public void updateRideWithDriver(String rideId,String driverId) {
        Ride ride = rideRepository.findById(rideId).
                orElseThrow(() -> new RuntimeException("Ride with id " + rideId + " not found"));

        ride.setDriverId(driverId);
        ride.setStatus(RideStatus.ACCEPTED);
        rideRepository.save(ride);

    }

    public RideResponse startRide(String rideId){

        Ride ride = rideRepository.findById(rideId).
                orElseThrow(() -> new RuntimeException("Ride with id " + rideId + " not found"));

        if(ride.getStatus() != RideStatus.ACCEPTED){
            throw new RuntimeException("Ride cannot be started and current status " + ride.getStatus() );
        }

            ride.setStatus(RideStatus.RIDE_STARTED);
            ride.setStartAt(LocalDateTime.now());
            rideRepository.save(ride);


        return mapToResponse(ride);

    }

    public RideResponse completeRide(String rideId){
        Ride ride = rideRepository.findById(rideId).orElseThrow(
                () -> new RuntimeException("Ride with id " + rideId + "not found")
        );

        if(ride.getStatus() != RideStatus.RIDE_STARTED){
            throw new RuntimeException("Ride cannot be completed and current status " + ride.getStatus() );
        }

        ride.setStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());
        ride.setActualFare(ride.getEstimatedFare());
        rideRepository.save(ride);

        return mapToResponse(ride);
    }

    public RideResponse cancelRide(String rideId){
        Ride ride = rideRepository.findById(rideId).orElseThrow(
                () -> new RuntimeException("Ride with id " + rideId + "not found")
        );

        ride.setStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);
        return mapToResponse(ride);
    }

    public RideResponse getRideById(String riderId){
        Ride ride = rideRepository.findById(riderId).orElseThrow(
                () -> new RuntimeException("Ride with id " + riderId + "not found")
        );
        return mapToResponse(ride);
    }

    public List<RideResponse> getRidesByRider(String riderId){
        return rideRepository.findByRiderIdOrderByCreatedAtDesc(riderId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private double calculateEstimateFare(RideRequest rideRequest){
        //simplified Haversine distance calculation
        double lat1 =  Math.toRadians(rideRequest.getPickUpLatitude());
        double lat2 =  Math.toRadians(rideRequest.getDropLatitude());

        double lon1 =  Math.toRadians(rideRequest.getPickUpLongitude());
        double lon2 =  Math.toRadians(rideRequest.getDropUpLongitude());

        double dLast = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLast / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2*Math.asin(Math.sqrt(a));
        double distInKm =  6371 * c;

        // Base Fare 50rs + 12rs perKm
        double fare = 50 + (distInKm * 12);
        return Math.round(fare * 100.0) / 100.0;
    }

    private RideResponse mapToResponse(Ride ride) {
        RideResponse response = new RideResponse();

        response.setId(ride.getId());
        response.setRiderId(ride.getRiderId());
        response.setDriverId(ride.getDriverId());
        response.setPickUpLatitude(ride.getPickUpLatitude());
        response.setPickUpLongitude(ride.getPickUpLongitude());
        response.setPickUpAddress(ride.getPickUpAddress());
        response.setDropLatitude(ride.getDropLatitude());
        response.setDropUpLongitude(ride.getDropUpLongitude());
        response.setDropAddress(ride.getDropAddress());
        response.setStatus(ride.getStatus());
        response.setEstimatedFare(ride.getEstimatedFare());
        response.setActualFare(ride.getActualFare());
        response.setCreatedAt(ride.getCreatedAt());
        response.setStartAt(ride.getStartAt());
        response.setCompletedAt(ride.getCompletedAt());
        return response;

    }
}
