package com.mikaelsonbraz.parkingapi.api.renting.resource;

import com.mikaelsonbraz.parkingapi.api.renting.dto.RentingDTO;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.renting.service.RentingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentingController {

    private final RentingService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentingDTO create(@RequestBody @Valid RentingDTO rentingDTO){

        Renting entity = modelMapper.map(rentingDTO, Renting.class);

        entity = service.save(entity);

        return modelMapper.map(entity, RentingDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RentingDTO get(@PathVariable Integer id){
        return service.getById(id)
                .map(renting -> modelMapper.map(renting, RentingDTO.class))
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}
