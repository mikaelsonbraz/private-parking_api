package com.mikaelsonbraz.parkingapi.api.resource;

import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.dto.ParkingSpaceDTO;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.service.ParkingSpaceService;
import com.mikaelsonbraz.parkingapi.api.renting.dto.RentingDTO;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/spaces")
public class ParkingSpaceController {

    private final ParkingSpaceService service;
    private final ModelMapper modelMapper;

    public ParkingSpaceController(ParkingSpaceService service, ModelMapper Modelmapper) {
        this.service = service;
        this.modelMapper = Modelmapper;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSpaceDTO create(@RequestBody @Valid ParkingSpaceDTO dto){

        ParkingSpace entity = modelMapper.map(dto, ParkingSpace.class);

        entity = service.save(entity);

        return modelMapper.map(entity, ParkingSpaceDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParkingSpaceDTO get(@PathVariable Integer id){
        return service.getById(id)
                .map(space -> modelMapper.map(space, ParkingSpaceDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        ParkingSpace space = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(space);
    }
}
