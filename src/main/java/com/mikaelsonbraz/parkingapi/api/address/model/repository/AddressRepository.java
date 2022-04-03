package com.mikaelsonbraz.parkingapi.api.address.model.repository;

import com.mikaelsonbraz.parkingapi.api.address.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
