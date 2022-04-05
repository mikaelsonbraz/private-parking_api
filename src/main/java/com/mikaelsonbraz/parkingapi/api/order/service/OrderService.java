package com.mikaelsonbraz.parkingapi.api.order.service;

import com.mikaelsonbraz.parkingapi.api.order.model.entity.Order;

import java.util.Optional;

public interface OrderService {

    Order save(Order order);

    Optional<Order> getById(Integer id);

    void delete(Order order);

    Order update(Order order);
}
