package com.mikaelsonbraz.parkingapi.api.resource.renting.dto;

import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentingDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRenting;

    @NotNull
    @Past
    private LocalDateTime entryDate;

    @Null
    private LocalDateTime departureDate;

    @NotNull
    @Positive
    private double hourPrice;

    @NotNull
    @Positive
    private double dayPrice;

    private ParkingSpace space;

}
