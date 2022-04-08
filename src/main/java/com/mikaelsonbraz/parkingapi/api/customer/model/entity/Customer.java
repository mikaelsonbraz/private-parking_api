package com.mikaelsonbraz.parkingapi.api.customer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Customer {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCustomer;

    @Column
    private String name;

    @Column
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<Ordered> ordereds = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return idCustomer != null && Objects.equals(idCustomer, customer.idCustomer);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
