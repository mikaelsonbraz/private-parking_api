package com.mikaelsonbraz.parkingapi.api.rent.service;

import com.mikaelsonbraz.parkingapi.api.rent.model.entity.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RentService {

    Rent save(Rent rent);

    Optional<Rent> getById(Integer id);

    void delete(Rent rent);

    Rent update(Rent rent);

    Page<Rent> find(Rent filter, Pageable pageRequest);
}
