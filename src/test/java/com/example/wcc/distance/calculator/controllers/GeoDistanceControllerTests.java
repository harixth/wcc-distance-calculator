package com.example.wcc.distance.calculator.controllers;

import com.example.wcc.distance.calculator.configurations.SecurityConfig;
import com.example.wcc.distance.calculator.entities.GeoDistance;
import com.example.wcc.distance.calculator.entities.Location;
import com.example.wcc.distance.calculator.repositories.ILocationRepository;
import com.example.wcc.distance.calculator.services.GeoDistanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes={SecurityConfig.class})
@WebMvcTest
@Import(GeoDistanceController.class)
public class GeoDistanceControllerTests {

    @MockBean
    private ILocationRepository locationRepository;

    @MockBean
    private GeoDistanceService geoDistanceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void calculateDistanceBetweenPostcodes() throws Exception {
        Location firstLocation = new Location("AB123FJ","57.097987","-2.077447");
        firstLocation.setId("577b6534-803a-4cb5-9ff5-e9446396ac62");
        Location secondLocation = new Location("AB217PW","57.255804","-2.172997");
        secondLocation.setId("124ac7ba-ef82-4a89-aa0f-c3bd1fe98deb");
        List<Location> locations = Arrays.asList(firstLocation, secondLocation);

        GeoDistance geoDistance = new GeoDistance(firstLocation, secondLocation, BigDecimal.valueOf(18.47));

        when(locationRepository.findLocationByPostalCodes("AB123FJ", "AB217PW")).thenReturn(locations);
        when(geoDistanceService.calculateGeoDistance("AB123FJ", "AB217PW")).thenReturn(geoDistance);
        mockMvc.perform(get("/distance/AB123FJ/AB217PW")).andExpect(status().isOk())
                .andExpect(jsonPath("$.distance").value(18.47))
                .andExpect(jsonPath("$.unit").value("KM"))
                .andDo(print());
    }
}
