package com.mikaelsonbraz.parkingapi.api.resource.renting.model.repository;

import com.mikaelsonbraz.parkingapi.api.resource.renting.model.entity.Renting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingRepository extends JpaRepository<Renting, Integer> {

    default boolean existsDepartureDateByIdRenting(Integer id){
        return getById(id).getDepartureDate() != null;
    }
}
