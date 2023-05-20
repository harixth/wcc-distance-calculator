package com.example.wcc.distance.calculator.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculationHelperTests {

    @Autowired
    private CalculationHelper calculationHelper;

    @Test
    public void calculateDistance1() {
        assertEquals(BigDecimal.valueOf(111.19),
                calculationHelper.calculateDistance(51.5074, 0.1278, 52.5074, 0.1290));
    }

    @Test
    public void calculateDistance2() {
        assertEquals(BigDecimal.valueOf(18.47),
                calculationHelper.calculateDistance(57.097987, -2.077447, 57.255804, -2.172997));
    }
}
