package com.mikaelsonbraz.parkingapi.api.parkingSpace.model.repository;

import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Integer> {
}
