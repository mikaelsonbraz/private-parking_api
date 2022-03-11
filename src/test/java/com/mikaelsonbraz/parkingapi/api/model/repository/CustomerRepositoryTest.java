package com.mikaelsonbraz.parkingapi.api.model.repository;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.model.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CustomerRepository repository;

    @Test
    @DisplayName("Must return true when exists a customer with declared CPF")
    public void shouldReturnTrueWhenCpfExists(){
        //cenario
        Customer customer = Customer.builder().name("João").cpf("222.222.222-22").build();
        entityManager.persist(customer);

        //execução
        boolean exists = repository.existsByCpf("222.222.222-22");

        //verificação
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Must return false when doesnt exists a customer with declared CPF")
    public void shouldReturnFalseWhenDoesntCpfExists(){
        //cenario

        //execução
        boolean exists = repository.existsByCpf("222.222.222-22");

        //verificação
        Assertions.assertThat(exists).isFalse();
    }
}
