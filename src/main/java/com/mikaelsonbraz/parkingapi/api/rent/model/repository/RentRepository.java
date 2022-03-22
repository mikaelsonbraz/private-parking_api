package com.mikaelsonbraz.parkingapi.api.rent.model.repository;

import com.mikaelsonbraz.parkingapi.api.rent.model.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Integer> {
}
