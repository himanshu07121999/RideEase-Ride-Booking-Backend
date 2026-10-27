package com.uberApp.ride_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    //Topic where ride service publishes ride request
    // Matching Service subscribers to this topic

    @Bean
    public NewTopic rideRequestTopic() {
        return TopicBuilder.name("ride.request")
                .partitions(3)
                .replicas(1)
                .build();
    }
    //Topic where matching service publishes match results
    //ride service subscribers to this topic

    @Bean
    public NewTopic rideMatchesTopic() {
        return TopicBuilder.name("ride.Matched")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
