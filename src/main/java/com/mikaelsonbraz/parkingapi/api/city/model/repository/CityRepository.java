package com.mikaelsonbraz.parkingapi.api.city.model.repository;

import com.mikaelsonbraz.parkingapi.api.city.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}
