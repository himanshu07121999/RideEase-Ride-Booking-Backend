package com.uberApp.matchingservice.service;

import com.uberApp.matchingservice.event.RideRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class RideEventConsumer {
    private final MatchingService matchingService;

    /**
     * Listens to ride requested kafka topic
     * triggered every time ride service published a new ride event
     *
     * FLOW:
     * Ride service -> kafka (ride.request) -> this consumer -> matchingService
     *
     */
    @KafkaListener(
            topics = "ride.request",
            groupId = "matching-service-group"
    )
    public void consumerRideRequestedEvent(RideRequestEvent event){
        try {
            matchingService.matchDriverForRide(event);
        }
        catch (Exception e) {
           log.error("error processing ride request",
                   event.getRideId(),e.getMessage());

            // In production : send to dead letter queue for retry
        }
    }
}

