package com.mikaelsonbraz.parkingapi.api.ordered.service;

import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;

import java.util.Optional;

public interface OrderService {

    Ordered save(Ordered ordered);

    Optional<Ordered> getById(Integer id);

    void delete(Ordered ordered);

    Ordered update(Ordered ordered);

    Ordered updateAmount(Ordered ordered);
}
