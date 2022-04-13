package com.mikaelsonbraz.parkingapi.api.renting.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_ParkingSpace")
    private ParkingSpace parkingSpace;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_order")
    private Ordered ordered;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Renting renting = (Renting) o;
        return Double.compare(renting.hourPrice, hourPrice) == 0 && Double.compare(renting.dayPrice, dayPrice) == 0 && Objects.equals(idRenting, renting.idRenting) && Objects.equals(entryDate, renting.entryDate) && Objects.equals(departureDate, renting.departureDate) && Objects.equals(parkingSpace, renting.parkingSpace) && Objects.equals(ordered, renting.ordered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRenting, entryDate, departureDate, hourPrice, dayPrice, parkingSpace, ordered);
    }
}
