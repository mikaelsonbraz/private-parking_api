package com.mikaelsonbraz.parkingapi.api.order.resource;

import com.mikaelsonbraz.parkingapi.api.order.dto.OrderDTO;
import com.mikaelsonbraz.parkingapi.api.order.model.entity.Order;
import com.mikaelsonbraz.parkingapi.api.order.service.OrderService;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;
    private final ModelMapper modelMapper;

    public OrderController(OrderService service, ModelMapper modelMapper){
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@RequestBody @Valid OrderDTO orderDTO){
        Order entity = modelMapper.map(orderDTO, Order.class);

        entity = service.save(entity);

        return modelMapper.map(entity, OrderDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO get(@PathVariable Integer  id){
        return service.getById(id)
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
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
    public void delete(@PathVariable Integer id){
        Order order = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(order);
    }
}
