package com.example.wcc.distance.calculator.services;

import com.example.wcc.distance.calculator.entities.GeoDistance;
import com.example.wcc.distance.calculator.entities.Location;
import com.example.wcc.distance.calculator.exceptions.HttpException;
import com.example.wcc.distance.calculator.utils.CalculationHelper;
import com.example.wcc.distance.calculator.utils.CsvReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class GeoDistanceService {

    @Autowired
    private CalculationHelper calculationHelper;

    private final Map<String, String[]> postcodes = CsvReader.buildDataMapFromCSV();

    @Cacheable("distance")
    public GeoDistance calculateGeoDistance(String firstPostcode, String secondPostcode) throws HttpException {
        try {
            String[] firstCoordinate = postcodes.get(firstPostcode);
            String[] secondCoordinate = postcodes.get(secondPostcode);
            if (firstCoordinate == null || secondCoordinate == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Postcode not found");
            }
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
