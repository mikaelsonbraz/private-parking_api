package com.mikaelsonbraz.parkingapi.api.renting.resource;

import com.mikaelsonbraz.parkingapi.api.renting.service.RentingService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rents")
public class RentingController {

    private ModelMapper modelMapper;

    public RentingController(RentingService service, ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
}
