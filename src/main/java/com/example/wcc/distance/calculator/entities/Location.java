package com.example.wcc.distance.calculator.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private String postalCode;
    private String latitude;
    private String longitude;
}
