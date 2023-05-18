package com.example.wcc.distance.calculator.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CsvHelper {
    public static Map<String, String[]> buildDataMapFromCSV() {
        Map<String, String[]> dataMap = new HashMap<>();
        Resource resource = new ClassPathResource("ukPostcodes.csv");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    String postcode = values[1].replace(" ", "");
                    String[] coordinates = {values[2], values[3]};
                    dataMap.put(postcode, coordinates);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}
