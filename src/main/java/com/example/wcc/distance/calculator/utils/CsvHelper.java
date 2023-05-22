package com.example.wcc.distance.calculator.utils;

import com.example.wcc.distance.calculator.entities.Location;
import com.example.wcc.distance.calculator.repositories.ILocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CsvHelper {

    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static Map<String, Location> buildDataMapFromCSV() throws Exception {
        Map<String, Location> dataMap = new HashMap<>();
        Resource resource = new ClassPathResource("ukPostcodes.csv");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    String postcode = values[1].replace(" ", "");
                    dataMap.put(postcode, new Location(postcode, values[2], values[3]));
                }
            }
        } catch (IOException e) {
            throw new Exception("Error while reading CSV file: " + e.getMessage());
        }
        return dataMap;
    }

    public static void loadLocationsFromCSV(ILocationRepository locationRepository) throws Exception {
        Resource resource = new ClassPathResource("ukPostcodes.csv");
        log.debug("Available processors: {}", THREAD_POOL_SIZE);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                try {
                    Integer.parseInt(values[0]);
                } catch (NumberFormatException e) {
                    continue;
                }
                String postalCode = values[1].replace(" ", "");
                executorService.execute(() -> {
                    Location existingLocations = locationRepository.findLocationByPostalCode(postalCode);
                    if (existingLocations == null) {
                        if (values.length >= 4) {
                            locationRepository.save(new Location(postalCode, values[2], values[3]));
                        } else {
                            if (!postalCode.isEmpty()) {
                                locationRepository.save(new Location(postalCode));
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new Exception("Error while reading CSV file: " + e.getMessage());
        } finally {
            executorService.shutdown();
            // TODO: Delete csv file after all data has been loaded
            if (executorService.isTerminated()) {
                log.debug("CSV data loaded");
            }
        }
    }
}
