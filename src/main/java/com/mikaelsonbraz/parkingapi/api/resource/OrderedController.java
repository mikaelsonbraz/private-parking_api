package com.mikaelsonbraz.parkingapi.api.resource;

import com.mikaelsonbraz.parkingapi.api.ordered.dto.OrderDTO;
import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;
import com.mikaelsonbraz.parkingapi.api.ordered.service.OrderService;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Api("ORDEREDS API")
public class OrderedController {

    private final OrderService service;
    private final ModelMapper modelMapper;

    public OrderedController(OrderService service, ModelMapper modelMapper){
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create an order")
    public OrderDTO create(@RequestBody @Valid OrderDTO orderDTO){
        Ordered entity = modelMapper.map(orderDTO, Ordered.class);

        entity = service.save(entity);

        return modelMapper.map(entity, OrderDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get an order")
    public OrderDTO get(@PathVariable Integer  id){
        return service.getById(id)
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update an order")
    public OrderDTO put(@PathVariable Integer id, OrderDTO orderDTO){
        return service.getById(id)
                .map(order -> {
                    order.setCustomer(orderDTO.getCustomer());
                    order.setRenting(orderDTO.getRenting());
                    return modelMapper.map(order, OrderDTO.class);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update a order with renting departure date")
    public OrderDTO patch(@PathVariable Integer id, OrderDTO orderDTO){
        Renting renting = orderDTO.getRenting();
        return service.getById(id)
                .map(order -> {;
                    order.getRenting().setDepartureDate(renting.getDepartureDate());
                    order = service.updateAmount(order);
                    return modelMapper.map(order, OrderDTO.class);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete an order")
    public void delete(@PathVariable Integer id){
        Ordered ordered = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(ordered);
    }
}
