package com.example.wcc.distance.calculator.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CsvHelperTests {

    @Test
    public void buildDataMapFromCSV() {
        Map<String, String[]> postcodes = CsvHelper.buildDataMapFromCSV();
        assertEquals(1780250, postcodes.size());
    }
}
