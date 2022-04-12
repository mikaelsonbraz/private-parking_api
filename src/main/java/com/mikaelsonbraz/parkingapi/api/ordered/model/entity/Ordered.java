package com.mikaelsonbraz.parkingapi.api.ordered.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordered")
@Entity
public class Ordered {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    @Column
    private double amount;

    @JsonIgnore
    @JoinColumn(name = "id_customer")
    @ManyToOne
    private Customer customer;

    @JsonIgnore
    @JoinColumn(name = "id_renting")
    @OneToOne
    private Renting renting;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ordered ordered = (Ordered) o;
        return idOrder != null && Objects.equals(idOrder, ordered.idOrder);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
