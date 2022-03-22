package com.mikaelsonbraz.parkingapi.api.rent.resource;

import com.mikaelsonbraz.parkingapi.api.rent.service.RentService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rents")
public class RentController {

    private RentService service;
    private ModelMapper modelMapper;

    public RentController(RentService service, ModelMapper modelMapper){
        this.service = service;
        this.modelMapper = modelMapper;
    }
}
