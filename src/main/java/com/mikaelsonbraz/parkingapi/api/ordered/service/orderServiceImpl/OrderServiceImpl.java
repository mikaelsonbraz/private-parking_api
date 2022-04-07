package com.mikaelsonbraz.parkingapi.api.ordered.service.orderServiceImpl;

import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;
import com.mikaelsonbraz.parkingapi.api.ordered.model.repository.OrderedRepository;
import com.mikaelsonbraz.parkingapi.api.ordered.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    OrderedRepository repository;
    public OrderServiceImpl(OrderedRepository repository){
        this.repository = repository;
    }
    @Override
    public Ordered save(Ordered ordered) {
        return this.repository.save(ordered);
    }

    @Override
    public Optional<Ordered> getById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Ordered ordered) {
        if (ordered == null || ordered.getIdOrder() == null){
            throw new IllegalArgumentException("Order id cannot be null");
        }
        this.repository.delete(ordered);
    }

    @Override
    public Ordered update(Ordered ordered) {
        if(ordered == null || ordered.getIdOrder() == null){
            throw new IllegalArgumentException("Order id cannot be null");
        }
        return repository.save(ordered);
    }

    @Override
    public Ordered updateAmount(Ordered ordered) {
        if(ordered.getRenting().getDepartureDate() == null){
            throw new BusinessException("Renting departure date in order cannot be null");
        }

        double amount;
        long hoursOfStay = ChronoUnit.HOURS.between(ordered.getRenting().getEntryDate(), ordered.getRenting().getDepartureDate());

        if (hoursOfStay >= 24){
            amount = ordered.getRenting().getDayPrice() * (hoursOfStay / 24);
        } else {
            amount = ordered.getRenting().getHourPrice() * hoursOfStay;
        }

        if (ordered.getRenting().getParkingSpace().getSpaceType().getCode() == 1){
            amount *= 0.8;
        }

        ordered.setAmount(amount);
        return this.repository.save(ordered);
    }
}
