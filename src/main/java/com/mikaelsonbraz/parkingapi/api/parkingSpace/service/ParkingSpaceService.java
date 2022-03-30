package com.mikaelsonbraz.parkingapi.api.parkingSpace.service;

import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;

import java.util.Optional;

public interface ParkingSpaceService {

    ParkingSpace save(ParkingSpace space);

    Optional<ParkingSpace> getById(Integer id);

    ParkingSpace update(ParkingSpace space);
}
