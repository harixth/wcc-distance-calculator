package com.example.wcc.distance.calculator.services;

import com.example.wcc.distance.calculator.exceptions.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GeoDistanceService {
    public double calculateGeoDistance(String firstPostcode, String secondPostcode) throws HttpException {
        try {
            //TODO: Implement calculation of distance between two points on the Earth's surface
            return 0;
        } catch (Exception e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
