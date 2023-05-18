package com.example.wcc.distance.calculator.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GeoDistanceServiceTests {

    @Autowired
    private GeoDistanceService geoDistanceService;

    @Test
    public void testCalculateGeoDistance() {
        String firstPostcode = "SW1A 1AA";
        String secondPostcode = "EC2A 3LT";
        double distance = geoDistanceService.calculateGeoDistance(firstPostcode, secondPostcode);
        assertEquals(0.0, distance, distance);
    }
}
