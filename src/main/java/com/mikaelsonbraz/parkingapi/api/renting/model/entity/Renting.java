package com.mikaelsonbraz.parkingapi.api.renting.model.entity;

import com.mikaelsonbraz.parkingapi.api.order.model.entity.Order;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @OneToOne(mappedBy = "renting")
    @JoinColumn(name = "id_ParkingSpace")
    private ParkingSpace parkingSpace;

    @OneToOne(mappedBy = "renting")
    @JoinColumn(name = "id_order")
    private Order order;
}
