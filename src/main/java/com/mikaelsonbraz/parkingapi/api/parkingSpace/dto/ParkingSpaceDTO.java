package com.mikaelsonbraz.parkingapi.api.parkingSpace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpaceDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSpace;

    private boolean occupied;

    @NotNull
    private Integer spaceType;

    @JsonIgnore
    @OneToOne(mappedBy = "parkingSpace")
    private Renting renting;

}
