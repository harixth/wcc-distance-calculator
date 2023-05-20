package com.example.wcc.distance.calculator.utils;

import com.example.wcc.distance.calculator.entities.Location;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CsvHelperTests {

    @Test
    public void buildDataMapFromCSV() throws Exception {
        Map<String, Location> postcodes = CsvHelper.buildDataMapFromCSV();
        assertEquals(1780250, postcodes.size());
    }
}
