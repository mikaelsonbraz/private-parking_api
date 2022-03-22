package com.mikaelsonbraz.parkingapi.api.rent.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortDurationRentDTO extends RentDTO{
    private static final long SerialVersionUID = 1L;

    @NotEmpty
    private Double hourPrice;
}
