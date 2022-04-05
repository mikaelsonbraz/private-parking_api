package com.mikaelsonbraz.parkingapi.api.order.service.orderServiceImpl;

import com.mikaelsonbraz.parkingapi.api.order.model.entity.Order;
import com.mikaelsonbraz.parkingapi.api.order.service.OrderService;

import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public Optional<Order> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public Order update(Order order) {
        return null;
    }
}
