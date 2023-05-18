package com.example.wcc.distance.calculator.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculationHelperTests {

    @Autowired
    private CalculationHelper calculationHelper;

    @Test
    public void testCalculateDistance() {
        double distance = calculationHelper.calculateDistance(51.5074, 0.1278, 52.5074, 0.1290);
        assertEquals(111.1949569759633, distance);
    }
}
