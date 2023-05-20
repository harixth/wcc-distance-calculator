package com.example.wcc.distance.calculator.repositories;

import com.example.wcc.distance.calculator.entities.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class LocationRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ILocationRepository locationRepository;

    @Test
    void findLocationByPostalCode() {
        Location location = new Location("AB123CD","57.097987","-2.077447");
        entityManager.persist(location);
        Location persistedLoc = locationRepository.findLocationByPostalCode("AB123CD");
        assertThat(persistedLoc).isEqualTo(location);
    }
}
