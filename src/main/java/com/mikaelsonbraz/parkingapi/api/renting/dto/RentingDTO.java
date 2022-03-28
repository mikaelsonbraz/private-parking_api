package com.mikaelsonbraz.parkingapi.api.renting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Positive
    private double hourPrice;

    @Positive
    private double dayPrice;
}
