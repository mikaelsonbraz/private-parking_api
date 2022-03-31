package com.mikaelsonbraz.parkingapi.api.parkingSpace.service.impl;

import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.repository.ParkingSpaceRepository;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.service.ParkingSpaceService;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository repository;

    public ParkingSpaceServiceImpl(ParkingSpaceRepository repository){ this.repository = repository; }

    @Override
    public ParkingSpace save(ParkingSpace space) {
        if (space.getRenting() != null && space.getRenting().getDepartureDate() == null){
            space.setOccupied(true);
        }
        return repository.save(space);
    }

    @Override
    public Optional<ParkingSpace> getById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public ParkingSpace update(ParkingSpace space) {
        if (space.getRenting() != null && space.getRenting().getDepartureDate() != null){
            space.setOccupied(false);
            space.setRenting(null);
        }
        return repository.save(space);
    }
}
