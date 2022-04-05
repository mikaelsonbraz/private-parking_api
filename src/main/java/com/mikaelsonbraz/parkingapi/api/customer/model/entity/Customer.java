package com.mikaelsonbraz.parkingapi.api.customer.model.entity;

import com.mikaelsonbraz.parkingapi.api.address.model.entity.Address;
import com.mikaelsonbraz.parkingapi.api.order.model.entity.Order;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
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

    @OneToOne
    @JoinColumn(name = "id_address")
    private Address address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
