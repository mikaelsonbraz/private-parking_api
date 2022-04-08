package com.mikaelsonbraz.parkingapi.api.resource;

import com.mikaelsonbraz.parkingapi.api.renting.dto.DepartureDateDTO;
import com.mikaelsonbraz.parkingapi.api.renting.dto.RentingDTO;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.renting.service.RentingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
@Api("RENTING API")
public class RentingController {

    private final RentingService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create a renting")
    public RentingDTO create(@RequestBody @Valid RentingDTO rentingDTO){

        Renting entity = modelMapper.map(rentingDTO, Renting.class);

        entity = service.save(entity);

        return modelMapper.map(entity, RentingDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get a renting")
    public RentingDTO get(@PathVariable Integer id){
        return service.getById(id)
                .map(renting -> modelMapper.map(renting, RentingDTO.class))
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update a renting")
    public void updateRenting(@PathVariable Integer id, @RequestBody DepartureDateDTO dto){
        Renting renting = service.getById(id).get();
        renting.setDepartureDate(dto.getDepartureDate());

        service.update(renting);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete a renting")
    public void deleteRenting(@PathVariable Integer id){
        Renting renting = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(renting);
    }
}
