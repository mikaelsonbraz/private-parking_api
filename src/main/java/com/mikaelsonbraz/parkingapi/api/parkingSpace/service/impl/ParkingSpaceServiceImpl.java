package com.mikaelsonbraz.parkingapi.api.parkingSpace.service.impl;

import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.repository.ParkingSpaceRepository;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.service.ParkingSpaceService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private ParkingSpaceRepository repository;

    public ParkingSpaceServiceImpl(ParkingSpaceRepository repository){ this.repository = repository; }

    @Override
    public ParkingSpace save(ParkingSpace space) {
        return null;
    }

    @Override
    public Optional<ParkingSpace> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public ParkingSpace update(ParkingSpace space) {
        return null;
    }
}
