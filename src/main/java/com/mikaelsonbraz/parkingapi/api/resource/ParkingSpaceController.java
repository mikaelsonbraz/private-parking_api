package com.mikaelsonbraz.parkingapi.api.resource;

import com.mikaelsonbraz.parkingapi.api.parkingSpace.dto.ParkingSpaceDTO;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.service.ParkingSpaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/spaces")
@Api("PARKING SPACE API")
public class ParkingSpaceController {

    private final ParkingSpaceService service;
    private final ModelMapper modelMapper;

    public ParkingSpaceController(ParkingSpaceService service, ModelMapper Modelmapper) {
        this.service = service;
        this.modelMapper = Modelmapper;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create an space")
    public ParkingSpaceDTO create(@RequestBody @Valid ParkingSpaceDTO dto){

        ParkingSpace entity = modelMapper.map(dto, ParkingSpace.class);

        entity = service.save(entity);

        return modelMapper.map(entity, ParkingSpaceDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get a space")
    public ParkingSpaceDTO get(@PathVariable Integer id){
        return service.getById(id)
                .map(space -> modelMapper.map(space, ParkingSpaceDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete a space")
    public void delete(@PathVariable Integer id){
        ParkingSpace space = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(space);
    }
}
