package com.mikaelsonbraz.parkingapi.api.customer.model.repository;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
