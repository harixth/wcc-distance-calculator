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
    public void calculateDistance() {
        assertEquals(BigDecimal.valueOf(111.19),
                calculationHelper.calculateDistance(51.5074, 0.1278, 52.5074, 0.1290));
    }
}
