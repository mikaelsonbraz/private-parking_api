package com.mikaelsonbraz.parkingapi.api.customer.dto;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

@Data //criar os getters, setters, toString e HashCodeAndEquals
@Builder //criar os construtores da classe
@NoArgsConstructor //criar o construtor vazio da classe
@AllArgsConstructor //criar o construtor povoado da classe
public class CustomerDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCustomer;

    @NotEmpty
    private String name;

    @NotEmpty
    private String cpf;

}
