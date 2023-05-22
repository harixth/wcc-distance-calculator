package com.example.wcc.distance.calculator.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CoordinateDTO {
    @NotBlank(message = "[Coordinate] latitude city is required.")
    private String latitude;

    @NotBlank(message = "[Coordinate] longitude is required.")
    private String longitude;
}
