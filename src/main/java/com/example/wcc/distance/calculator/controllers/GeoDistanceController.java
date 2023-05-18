package com.example.wcc.distance.calculator.controllers;

import com.example.wcc.distance.calculator.exceptions.HttpException;
import com.example.wcc.distance.calculator.services.GeoDistanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/distance")
public class GeoDistanceController {

    @Autowired
    private GeoDistanceService geoDistanceService;

    @GetMapping("/{postcode1}/{postcode2}")
    public ResponseEntity<String> getDistanceBetweenPostcodes(@PathVariable("postcode1") String firstPostcode,
                                                      @PathVariable("postcode2") String secondPostcode) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(geoDistanceService.calculateGeoDistance(firstPostcode, secondPostcode));
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            if (e instanceof HttpException) {
                return ResponseEntity.status(((HttpException) e).getHttpStatus()).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
}
