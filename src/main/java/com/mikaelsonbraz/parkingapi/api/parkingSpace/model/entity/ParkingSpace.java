package com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ParkingSpace {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSpace;

    @Column
    private boolean occuppied;

    @Column
    private Integer spaceType;
}
