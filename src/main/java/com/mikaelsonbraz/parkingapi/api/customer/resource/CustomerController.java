package com.mikaelsonbraz.parkingapi.api.customer.resource;

import com.mikaelsonbraz.parkingapi.api.customer.dto.CustomerDTO;
import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService service, ModelMapper Modelmapper) {
        this.service = service;
        this.modelMapper = Modelmapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO create(@RequestBody @Valid CustomerDTO customerDTO){

        Customer entity = modelMapper.map(customerDTO, Customer.class);

        entity = service.save(entity);

        return modelMapper.map(entity, CustomerDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO get(@PathVariable Integer id){

        return service.getById(id)
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CustomerDTO> find(CustomerDTO dto, Pageable pageRequest){
        Customer filter = modelMapper.map(dto, Customer.class);
        return service
                .find(filter, pageRequest)
                .map(entity -> modelMapper.map(entity, CustomerDTO.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        Customer customer = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(customer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO update(@PathVariable Integer id, CustomerDTO customerDTO){
        return service.getById(id).map(customer -> {

            customer.setName(customerDTO.getName());
            customer.setCpf(customerDTO.getCpf());
            customer = service.update(customer);
            return modelMapper.map(customer, CustomerDTO.class);

        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

}
