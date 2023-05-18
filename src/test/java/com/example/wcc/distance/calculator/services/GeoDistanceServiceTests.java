package com.example.wcc.distance.calculator.services;

import com.example.wcc.distance.calculator.entities.GeoDistance;
import com.example.wcc.distance.calculator.exceptions.HttpException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GeoDistanceServiceTests {

    @Autowired
    private GeoDistanceService geoDistanceService;

    @Test
    public void calculateGeoDistanceWithCorrectPostcodes() {
        GeoDistance geoDistance = geoDistanceService.calculateGeoDistance("SW1A 1AA", "EC2A 3LT");
        assertEquals(BigDecimal.valueOf(4.83), geoDistance.getDistance());
    }

    @Test
    public void calculateGeoDistanceWithWrongPostcodes() {
        assertThrows(HttpException.class,
                () -> geoDistanceService.calculateGeoDistance("SW1A", "EC2A"));
    }

    @Test
    public void calculateGeoDistanceWithDifferentPostcodes() {
        GeoDistance geoDistance = geoDistanceService.calculateGeoDistance("AB10 1XG", "AB21 7LN");
        assertEquals(BigDecimal.valueOf(8.44), geoDistance.getDistance());
    }
}
