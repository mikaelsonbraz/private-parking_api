package com.mikaelsonbraz.parkingapi.api.renting.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Renting {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRenting;

    @Column
    private Date entryDate;

    @Column
    private Date departureDate;

    @Column
    private double hourPrice;

    @Column
    private double dayPrice;
}
