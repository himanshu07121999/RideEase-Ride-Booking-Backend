package com.uberApp.ride_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    //rider who requested ride

    private String id;

    @Column(nullable = false)
    private String riderId;

    //who accepted the ride (null until match)

    private String driverId;

    @Column(nullable = false)
    private double pickUpLatitude;

    @Column(nullable = false)
    private double pickUpLongitude;

    @Column(nullable = false)
    private String pickUpAddress;

    @Column(nullable = false)
    private double dropLatitude;

    @Column(nullable = false)
    private double dropUpLongitude;

    @Column(nullable = false)
    private String dropAddress;

    //ride status -track the lifecycle
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideStatus status;

    // fare details
    private double estimatedFare;
    private double actualFare;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime startAt;
    private LocalDateTime completedAt;
}
