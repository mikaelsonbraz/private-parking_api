package com.mikaelsonbraz.parkingapi.api.renting.service;

import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RentingService {
    Renting save(Renting renting);

    Optional<Renting> getById(Integer id);

    Renting update(Renting renting);
}
