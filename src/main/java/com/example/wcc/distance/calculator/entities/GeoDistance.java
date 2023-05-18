package com.example.wcc.distance.calculator.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GeoDistance {
    private Location startLocation;
    private Location endLocation;
    private BigDecimal distance;
    private String unit;

    public GeoDistance(Location startLocation, Location endLocation, BigDecimal distance) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.unit = "KM";
    }
}
