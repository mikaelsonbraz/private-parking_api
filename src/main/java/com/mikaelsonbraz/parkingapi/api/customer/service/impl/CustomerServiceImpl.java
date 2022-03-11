package com.mikaelsonbraz.parkingapi.api.customer.service.impl;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.model.repository.CustomerRepository;
import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository){
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        if (repository.existsByCpf(customer.getCpf())){
            throw new BusinessException("CPF já cadastrado");
        }
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public Customer update(Customer customer) {

        return customer;
    }
}
