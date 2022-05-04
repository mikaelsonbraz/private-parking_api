package com.mikaelsonbraz.parkingapi.api.parkingSpace.service.impl;

import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.repository.ParkingSpaceRepository;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.service.ParkingSpaceService;
import com.mikaelsonbraz.parkingapi.api.resource.renting.model.entity.Renting;
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
        return this.repository.save(space);
    }

    @Override
    public Optional<ParkingSpace> getById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public ParkingSpace update(ParkingSpace space) {
        if (space.getRenting() != null || space.getRenting().getDepartureDate() != null){
            space.setOccupied(false);
            space.setRenting(null);
        } else if (space.getRenting().getDepartureDate() == null){
            space.setOccupied(true);
        }
        return this.repository.save(space);
    }

    @Override
    public ParkingSpace setRentingOnParkingSpace(ParkingSpace space, Renting renting){
        space.setRenting(renting);
        space.setOccupied(renting != null);
        return this.repository.save(space);
    }

    @Override
    public void delete(ParkingSpace space) {
        if (space == null || space.getIdSpace() == null){
            throw  new IllegalArgumentException("Parking space id not be null");
        }
        this.repository.delete(space);
    }
}
