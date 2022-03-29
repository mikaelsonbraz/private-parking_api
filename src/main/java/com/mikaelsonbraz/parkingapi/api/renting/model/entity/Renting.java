package com.mikaelsonbraz.parkingapi.api.renting.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Renting {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRenting;

    @Column
    private LocalDateTime entryDate;

    @Column
    private LocalDateTime departureDate;

    @Column
    private double hourPrice;

    @Column
    private double dayPrice;
}
