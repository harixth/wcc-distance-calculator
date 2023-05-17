package com.example.wcc.distance.calculator.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
@Getter
@Setter
public class AppConfig {
    private String name;
}
