package com.mikaelsonbraz.parkingapi.api.renting.service.impl;

import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.renting.model.repository.RentingRepository;
import com.mikaelsonbraz.parkingapi.api.renting.service.RentingService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RentingServiceImpl implements RentingService {

    RentingRepository repository;

    public RentingServiceImpl(RentingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Renting save(Renting renting) {
        if (renting.getEntryDate().isAfter(LocalDateTime.now())) {
            throw new BusinessException("Data inválida");
            } else if (renting.getDayPrice() < renting.getHourPrice()){
            throw new BusinessException("Valor do dia de estacionamento não pode ser menor que o valor da hora");
        }
        return repository.save(renting);
    }

    @Override
    public Optional<Renting> getById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public Renting update(Renting renting) {
        if (renting.getEntryDate().isAfter(renting.getDepartureDate())){
            throw new BusinessException("Data de partida não pode ser anterior a data de entrada");
        }
        return repository.save(renting);
    }


}


