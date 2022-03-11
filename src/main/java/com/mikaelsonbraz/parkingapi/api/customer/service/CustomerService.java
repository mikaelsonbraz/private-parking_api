package com.mikaelsonbraz.parkingapi.api.customer.service;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    Customer save(Customer any);

    Optional<Customer> getById(Integer id);

    void delete(Customer customer);

    Customer update(Customer customer);
}
