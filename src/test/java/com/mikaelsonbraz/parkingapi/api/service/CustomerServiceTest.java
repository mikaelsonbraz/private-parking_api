package com.mikaelsonbraz.parkingapi.api.service;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.model.repository.CustomerRepository;
import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
import com.mikaelsonbraz.parkingapi.api.customer.service.impl.CustomerServiceImpl;
import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
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
        Mockito.when(repository.existsByCpf(Mockito.anyString())).thenReturn(false);
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

    @Test
    @DisplayName("Must throw an error when tried to save customer with duplicated CPF")
    public void shouldNotSaveCustomerWithDuplicatedCPF(){
        //cenario
        Customer customer = Customer.builder().name("João").cpf("222.222.222-22").build();
        Mockito.when(repository.existsByCpf(Mockito.anyString())).thenReturn(true);

        //execução
        Throwable exception = Assertions.catchThrowable(() -> service.save(customer));

        //verificações
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("CPF já cadastrado");

        Mockito.verify(repository, Mockito.never()).save(customer);

    }
}
