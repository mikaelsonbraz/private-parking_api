package com.mikaelsonbraz.parkingapi.api.customer.service;

import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.model.repository.CustomerRepository;
import com.mikaelsonbraz.parkingapi.api.customer.service.impl.CustomerServiceImpl;
import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

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

    @Test
    @DisplayName("Should obtain the customer by id")
    public void getByIdTest() throws Exception{
        //cenario
        Integer id = 1;
        Customer customer = Customer.builder()
                .idCustomer(id)
                .name("João")
                .cpf("123.123.123-12").build();
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(customer));

        //execução
        Optional<Customer> foundCustomer = service.getById(id);

        //verificação
        Assertions.assertThat(foundCustomer.isPresent()).isTrue();
        Assertions.assertThat(foundCustomer.get().getIdCustomer()).isEqualTo(id);
        Assertions.assertThat(foundCustomer.get().getName()).isEqualTo(customer.getName());
        Assertions.assertThat(foundCustomer.get().getCpf()).isEqualTo(customer.getCpf());
    }

    @Test
    @DisplayName("Should return null when search a nonexistent customer by id")
    public void customerNotFoundByIdTest() throws Exception{
        //cenario
        Integer id = 1;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //execução
        Optional<Customer> notFoundCustomer = service.getById(id);

        //verificação
        Assertions.assertThat(notFoundCustomer.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should delete a customer on database")
    public void deleteCustomerTest(){
        //cenário
        Customer customer = Customer.builder().idCustomer(1).name("João").cpf("111.111.111-22").build();

        //execução
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> service.delete(customer));

        //verificação
        Mockito.verify(repository, Mockito.times(1)).delete(customer);
    }

    @Test
    @DisplayName("Should return IllegalArgumentException when try  delete the customer on db")
    public void deleteInvalidCustomerTest(){
        //cenario
        Customer customer = new Customer();

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(customer));

        //verificação
        Mockito.verify(repository, Mockito.never()).delete(customer);
    }

    @Test
    @DisplayName("Should update a customer on database")
    public void updateCustomerTest(){
        //cenario
        Customer updatingCustomer = Customer.builder().idCustomer(1).name("João").cpf("333.333.333-33").build();
        Customer updatedCustomer = Customer.builder().idCustomer(1).name("José").cpf("444.444.444-44").build();

        Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(updatedCustomer);

        //execução
        Customer customer = service.update(updatingCustomer);

        //verificações
        Assertions.assertThat(customer.getIdCustomer()).isEqualTo(updatedCustomer.getIdCustomer());
        Assertions.assertThat(customer.getName()).isEqualTo(updatedCustomer.getName());
        Assertions.assertThat(customer.getCpf()).isEqualTo(updatedCustomer.getCpf());

    }

    @Test
    @DisplayName("Should return IllegalArgumentException when try update a nonexistent customer on db")
    public void updateInvalidCustomerTest(){
        //cenario
        Customer customer = new Customer();

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(customer));

        //verificação
        Mockito.verify(repository, Mockito.never()).save(customer);
    }

    @Test
    @DisplayName("Should test find() method on CustomerServiceImpl")
    public void findCustomerTest(){
        //cenario
        Customer customer = Customer.builder().name("João").cpf("111.111.111-11").build();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<Customer>(Arrays.asList(customer), pageRequest, 1);
        Mockito.when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        //execução
        Page<Customer> result = service.find(customer, pageRequest);

        //verificação
        Assertions.assertThat(result.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(result.getContent()).isEqualTo(Arrays.asList(customer));
        Assertions.assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        Assertions.assertThat(result.getPageable().getPageSize()).isEqualTo(10);

    }
}
