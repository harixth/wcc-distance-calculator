package com.example.wcc.distance.calculator;

import com.example.wcc.distance.calculator.configurations.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class WccDistanceCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WccDistanceCalculatorApplication.class, args);
    }
}
