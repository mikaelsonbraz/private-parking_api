package com.mikaelsonbraz.parkingapi.api.service;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.model.repository.CustomerRepository;
import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
import com.mikaelsonbraz.parkingapi.api.customer.service.impl.CustomerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CustomerServiceTest {

    CustomerService service;

    @MockBean
    CustomerRepository repository;

    @BeforeEach
    public void setUp(){
        this.service = new CustomerServiceImpl(repository);
    }

    @Test
    @DisplayName("Must save a Customer")
    public void saveCustomerTest(){
        //cenario
        Customer customer = Customer.builder().idCustomer(1).name("Mary").cpf("111.111.111-11").build();
        Mockito.when(repository.save(customer)).thenReturn(Customer.builder()
                .idCustomer(1)
                .name("Mary")
                .cpf("111.111.111-11")
                .build());

        //execuçãao
        Customer savedCustomer = service.save(customer);

        //verificação
        Assertions.assertThat(savedCustomer.getIdCustomer()).isNotNull();
        Assertions.assertThat(savedCustomer.getName()).isEqualTo(customer.getName());
        Assertions.assertThat(savedCustomer.getCpf()).isEqualTo(customer.getCpf());
    }
}
