package com.mikaelsonbraz.parkingapi.api.ordered.model.entity;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Ordered {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    @Column
    private double amount;

    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "id_customer")
    @ManyToOne
    private Customer customer;

    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "id_renting")
    @OneToOne
    private Renting renting;
}
