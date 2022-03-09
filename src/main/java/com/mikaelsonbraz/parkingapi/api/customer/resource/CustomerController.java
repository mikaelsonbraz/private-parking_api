package com.mikaelsonbraz.parkingapi.api.customer.resource;

import com.mikaelsonbraz.parkingapi.api.customer.dto.CustomerDTO;
import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService service;
    private ModelMapper modelMapper;

    public CustomerController(CustomerService service, ModelMapper mapper) {
        this.service = service;
        this.modelMapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO create(@RequestBody CustomerDTO customerDTO){

        Customer entity = modelMapper.map(customerDTO, Customer.class);

        entity = service.save(entity);

        return modelMapper.map(entity, CustomerDTO.class);
    }
}
