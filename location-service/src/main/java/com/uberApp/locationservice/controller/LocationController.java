package com.uberApp.locationservice.controller;

import com.uberApp.locationservice.dto.DriverLocationRequest;
import com.uberApp.locationservice.dto.NearByDriverResponse;
import com.uberApp.locationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@Slf4j
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    //driver phone calls this every 3 second

    @PostMapping("/drivers/update")
    public ResponseEntity<String> updateDriverLocation(
            @RequestBody DriverLocationRequest driverLocationRequest){
    locationService.updateDriverLocation(driverLocationRequest);
    return ResponseEntity.ok("Driver Location Updated");
    }

    //Matching service calls this when ride is requested
    @GetMapping("/drivers/nearby")
    public ResponseEntity<List<NearByDriverResponse>> getNearByDrivers(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam (defaultValue = "5.0" ) double radius ){
        return ResponseEntity.ok(locationService.findNearByDriver(latitude , longitude , radius));
    }

    //when driver goes offline
    @DeleteMapping("/drivers/{driverID}")
    public ResponseEntity<String>removeDriver(@PathVariable String driverId){
        locationService.removeDriver(driverId);
        return ResponseEntity.ok("Driver Removed Successfully");
    }
}


