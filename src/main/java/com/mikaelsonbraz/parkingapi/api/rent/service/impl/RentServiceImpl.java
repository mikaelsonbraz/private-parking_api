package com.mikaelsonbraz.parkingapi.api.rent.service.impl;

import com.mikaelsonbraz.parkingapi.api.rent.model.entity.Rent;
import com.mikaelsonbraz.parkingapi.api.rent.model.repository.RentRepository;
import com.mikaelsonbraz.parkingapi.api.rent.service.RentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentServiceImpl implements RentService {

    RentRepository repository;

    public RentServiceImpl(RentRepository repository){
        this.repository = repository;
    }

    @Override
    public Rent save(Rent rent) {
        return null;
    }

    @Override
    public Optional<Rent> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Rent rent) {

    }

    @Override
    public Rent update(Rent rent) {
        return null;
    }

    @Override
    public Page<Rent> find(Rent filter, Pageable pageRequest) {
        return null;
    }
}
