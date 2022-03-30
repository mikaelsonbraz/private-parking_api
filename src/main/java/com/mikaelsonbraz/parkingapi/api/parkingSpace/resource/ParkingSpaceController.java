package com.mikaelsonbraz.parkingapi.api.parkingSpace.resource;

import com.mikaelsonbraz.parkingapi.api.parkingSpace.service.ParkingSpaceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spaces")
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService service;
    private final ModelMapper modelMapper;
}
