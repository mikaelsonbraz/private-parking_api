package com.mikaelsonbraz.parkingapi.api.customer.resource;

import com.mikaelsonbraz.parkingapi.api.customer.dto.CustomerDTO;
import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
import com.mikaelsonbraz.parkingapi.api.exceptions.ApiErrors;
import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();

        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessExceptions(BusinessException exception){
        return new ApiErrors(exception);
    }
}
