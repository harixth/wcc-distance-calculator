package com.example.wcc.distance.calculator.repositories;

import com.example.wcc.distance.calculator.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByPostalCode(String postalCode);
    @Query("SELECT l FROM Location l WHERE l.postalCode = :firstCode OR l.postalCode = :secondCode")
    List<Location> findLocationByPostalCodes(@Param("firstCode") String firstCode, @Param("secondCode") String secondCode);
}
