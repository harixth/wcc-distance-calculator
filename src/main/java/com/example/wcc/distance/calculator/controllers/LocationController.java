package com.example.wcc.distance.calculator.controllers;

import com.example.wcc.distance.calculator.entities.Location;
import com.example.wcc.distance.calculator.exceptions.HttpException;
import com.example.wcc.distance.calculator.repositories.ILocationRepository;
import com.example.wcc.distance.calculator.validation.CoordinateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/location")
@RestController
public class LocationController {

    @Autowired
    private ILocationRepository locationRepository;

    @PutMapping("/postal/{code}")
    public ResponseEntity<String> updateLocationByPostcode(@PathVariable("code") String code,@Valid @RequestBody CoordinateDTO coordinate) {
        Location location = locationRepository.findLocationByPostalCode(code.replace(" ", ""));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (location != null) {
                location.setLatitude(coordinate.getLatitude());
                location.setLongitude(coordinate.getLongitude());
            } else {
                throw new HttpException(HttpStatus.NOT_FOUND, "Location with postcode " + code + " not found");
            }
            String jsonResponse = objectMapper.writeValueAsString(locationRepository.save(location));
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
