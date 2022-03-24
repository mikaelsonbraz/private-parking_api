package com.mikaelsonbraz.parkingapi.api.renting.service.impl;

import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.renting.model.repository.RentingRepository;
import com.mikaelsonbraz.parkingapi.api.renting.service.RentingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentingServiceImpl implements RentingService {

    RentingRepository repository;

    public RentingServiceImpl(RentingRepository repository){
        this.repository = repository;
    }

    @Override
    public Renting save(Renting renting) {
        return repository.save(renting);
    }

    @Override
    public Optional<Renting> getById(Integer id) {
        return this.repository.findById(id);
    }
}
