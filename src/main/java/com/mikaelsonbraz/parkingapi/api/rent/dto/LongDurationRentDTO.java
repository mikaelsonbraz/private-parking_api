package com.mikaelsonbraz.parkingapi.api.rent.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LongDurationRentDTO extends RentDTO{
    private static final long SerialVersionUID = 1L;

    @NotEmpty
    private Double dayPrice;

    @NotEmpty
    private Double hourPrice;
}
