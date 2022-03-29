package com.mikaelsonbraz.parkingapi.api.renting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartureDateDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private LocalDateTime departureDate;
}
