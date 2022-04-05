package com.mikaelsonbraz.parkingapi.api.order.model.repository;

import com.mikaelsonbraz.parkingapi.api.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
