package com.mikaelsonbraz.parkingapi.api.customer.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;
import javax.validation.constraints.Null;
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

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Ordered> ordereds = new ArrayList<>();
}
