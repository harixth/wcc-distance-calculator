package com.example.wcc.distance.calculator.services;

import com.example.wcc.distance.calculator.exceptions.HttpException;
import com.example.wcc.distance.calculator.utils.CalculationHelper;
import com.example.wcc.distance.calculator.utils.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class GeoDistanceService {

    @Autowired
    private CalculationHelper calculationHelper;

    private final Map<String, String[]> postcodes = CsvReader.buildDataMapFromCSV();

    public BigDecimal calculateGeoDistance(String firstPostcode, String secondPostcode) throws HttpException {
        try {
            String[] firstCoordinate = postcodes.get(firstPostcode);
            String[] secondCoordinate = postcodes.get(secondPostcode);
            if (firstCoordinate == null || secondCoordinate == null) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Postcode not found");
            }
            return calculationHelper.calculateDistance(Double.parseDouble(firstCoordinate[0]),
                    Double.parseDouble(firstCoordinate[1]),
                    Double.parseDouble(secondCoordinate[0]),
                    Double.parseDouble(secondCoordinate[1]));
        } catch (HttpException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
