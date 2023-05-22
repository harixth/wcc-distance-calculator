package com.example.wcc.distance.calculator.services;

import com.example.wcc.distance.calculator.entities.GeoDistance;
import com.example.wcc.distance.calculator.entities.Location;
import com.example.wcc.distance.calculator.exceptions.HttpException;
import com.example.wcc.distance.calculator.repositories.ILocationRepository;
import com.example.wcc.distance.calculator.utils.CalculationHelper;
import com.example.wcc.distance.calculator.utils.CsvHelper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GeoDistanceService {

    @Autowired
    private CalculationHelper calculationHelper;

    @Autowired
    private ILocationRepository locationRepository;

    private Map<String, Location> locationDataMap;

    @PostConstruct
    public void init() {
        try {
            // Load data from CSV file and insert into database
            CsvHelper.loadLocationsFromCSV(locationRepository);
            locationDataMap = CsvHelper.buildDataMapFromCSV();
        } catch (Exception error) {
            log.error("Error while loading CSV data: " + error.getMessage());
        }
    }

    @Cacheable("distance")
    public GeoDistance calculateGeoDistance(String firstPostcode, String secondPostcode) throws HttpException {
        try {
            // Retrieve locations from database
            List<Location> locations = locationRepository.findLocationByPostalCodes(firstPostcode, secondPostcode);
            // If not exist in database, retrieve from CSV file as it might not be loaded yet
            if (locations.size() != 2) {
                if (locationDataMap.get(firstPostcode) != null && locationDataMap.get(secondPostcode) != null) {
                    locations.add(locationDataMap.get(firstPostcode));
                    locations.add(locationDataMap.get(secondPostcode));
                }
            }
            // Retrieve coordinates for both postcodes
            String[] firstCoordinate = locations.stream().filter(location -> location.getPostalCode().equals(firstPostcode)).map(location ->
                    new String[]{location.getLatitude(), location.getLongitude()}
            ).findFirst().orElse(null);
            String[] secondCoordinate = locations.stream().filter(location -> location.getPostalCode().equals(secondPostcode)).map(location ->
                    new String[]{location.getLatitude(), location.getLongitude()}
            ).findFirst().orElse(null);
            if (firstCoordinate == null || firstCoordinate[0] == null || firstCoordinate[1] == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Coordinate not found for "
                        + firstPostcode);
            }
            if (secondCoordinate == null || secondCoordinate[0] == null || secondCoordinate[1] == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Coordinate not found for "
                        + secondPostcode);
            }
            // Calculate distance between both coordinates
            BigDecimal distance = calculationHelper.calculateDistance(Double.parseDouble(firstCoordinate[0]),
                    Double.parseDouble(firstCoordinate[1]),
                    Double.parseDouble(secondCoordinate[0]),
                    Double.parseDouble(secondCoordinate[1]));
            log.info("Geographic distance between {} and {} is {}", firstPostcode, secondPostcode, distance);
            return new GeoDistance(
                    new Location(firstPostcode, firstCoordinate[0], firstCoordinate[1]),
                    new Location(secondPostcode, secondCoordinate[0], secondCoordinate[1]),
                    distance
            );
        } catch (Exception error) {
            log.error("Error while calculating distance between {} and {}: " + error.getMessage(),
                    firstPostcode, secondPostcode);
            throw error;
        }
    }
}
