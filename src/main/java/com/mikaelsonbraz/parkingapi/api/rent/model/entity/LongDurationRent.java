package com.mikaelsonbraz.parkingapi.api.rent.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class LongDurationRent extends Rent{
    private static final long SerialVersionUID = 1L;

    @Column
    private Double dayPrice;

    @Column
    private Double hourPrice;
}
