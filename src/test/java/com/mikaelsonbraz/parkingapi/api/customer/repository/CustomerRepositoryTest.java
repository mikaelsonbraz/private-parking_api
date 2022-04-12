package com.mikaelsonbraz.parkingapi.api.customer.repository;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.model.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    @Test
    @DisplayName("Should return a customer by id")
    public void findByIdTest(){
        //cenario
        Customer customer = Customer.builder()
                .name("João")
                .cpf("111.111.111-11").build();
        entityManager.persist(customer);

        //execução
        Optional<Customer> foundCustomer = repository.findById(customer.getIdCustomer());

        //verificação
        Assertions.assertThat(foundCustomer.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should save a customer on db")
    public void saveCustomerTest(){
        //cenario
        Customer customer = Customer.builder().name("João").cpf("111.111.111-22").build();
        Customer customer2 = Customer.builder().name("João").cpf("111.111.111-22").build();
        Customer savedCustomer = repository.save(customer);
        Customer savedCustomer2 = repository.save(customer2);

        //execução
        System.out.println(savedCustomer.getIdCustomer());
        System.out.println(savedCustomer2.getIdCustomer());

        //verificação, verificando se o customer savedCustomer tem id
        Assertions.assertThat(savedCustomer.getIdCustomer()).isNotNull();

    }

    @Test
    @DisplayName("Should delete a customer on db")
    public void deleteCustomerTest(){
        //cenario
        Customer customer = Customer.builder().name("João").cpf("222.222.222-22").build();
        entityManager.persist(customer);

        //execução
        Customer savedCustomer = entityManager.find(Customer.class, customer.getIdCustomer());
        repository.delete(savedCustomer);
        Customer deletedCustomer = entityManager.find(Customer.class, customer.getIdCustomer());

        //verificação
        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(deletedCustomer).isNull();

    }

}
