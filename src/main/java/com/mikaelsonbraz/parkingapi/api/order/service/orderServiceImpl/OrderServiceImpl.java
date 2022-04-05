package com.mikaelsonbraz.parkingapi.api.order.service.orderServiceImpl;

import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import com.mikaelsonbraz.parkingapi.api.order.model.entity.Order;
import com.mikaelsonbraz.parkingapi.api.order.model.repository.OrderRepository;
import com.mikaelsonbraz.parkingapi.api.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository repository;
    public OrderServiceImpl(OrderRepository repository){
        this.repository = repository;
    }
    @Override
    public Order save(Order order) {
        return this.repository.save(order);
    }

    @Override
    public Optional<Order> getById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Order order) {
        if (order == null || order.getIdOrder() == null){
            throw new IllegalArgumentException("Order id cannot be null");
        }
        this.repository.delete(order);
    }

    @Override
    public Order update(Order order) {
        if(order == null || order.getIdOrder() == null){
            throw new IllegalArgumentException("Order id cannot be null");
        }
        return repository.save(order);
    }

    @Override
    public Order updateAmount(Order order) {
        if(order.getRenting().getDepartureDate() == null){
            throw new BusinessException("Renting departure date in order cannot be null");
        }

        double amount;
        long hoursOfStay = ChronoUnit.HOURS.between(order.getRenting().getEntryDate(), order.getRenting().getDepartureDate());

        if (hoursOfStay >= 24){
            amount = order.getRenting().getDayPrice() * (hoursOfStay / 24);
        } else {
            amount = order.getRenting().getHourPrice() * hoursOfStay;
        }

        if (order.getRenting().getParkingSpace().getSpaceType().getCode() == 1){
            amount *= 0.8;
        }

        order.setAmount(amount);
        return this.repository.save(order);
    }
}
