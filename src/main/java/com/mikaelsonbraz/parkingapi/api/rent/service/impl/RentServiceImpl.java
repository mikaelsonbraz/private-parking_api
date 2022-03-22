package com.mikaelsonbraz.parkingapi.api.rent.service.impl;

import com.mikaelsonbraz.parkingapi.api.rent.model.repository.RentRepository;
import com.mikaelsonbraz.parkingapi.api.rent.service.RentService;
import org.springframework.stereotype.Service;

@Service
public class RentServiceImpl implements RentService {

    RentRepository repository;

    public RentServiceImpl(RentRepository repository){
        this.repository = repository;
    }
}
