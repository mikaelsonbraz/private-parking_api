package com.mikaelsonbraz.parkingapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikaelsonbraz.parkingapi.api.customer.dto.CustomerDTO;
import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
import com.mikaelsonbraz.parkingapi.api.exceptions.BusinessException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@ExtendWith(SpringExtension.class) // pra criar um mini contexto de injeção de dependências
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    static String CUSTOMER_API = "/api/customers";

    @Autowired
    MockMvc mvc;

    @MockBean
    CustomerService service;

    @Test
    @DisplayName("Must create a customer")
    public void createCustomerTest() throws Exception{

        CustomerDTO customer = CustomerDTO.builder().name("John").cpf("000.000.000-00").build();
        Customer savedCustomer = Customer.builder().name("John").cpf("000.000.000-00").build();
        BDDMockito.given(service.save(Mockito.any(Customer.class))).willReturn(savedCustomer);
        String json = new ObjectMapper().writeValueAsString(customer); // recebe um objeto de qualquer tipo e transforma-o em json

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("idCustomer").value(customer.getIdCustomer()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(customer.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("cpf").value(customer.getCpf()));
    }

    @Test
    @DisplayName("Must throw an error when there is not enough customer data")
    public void createInvalidCustomerTest() throws Exception{

        String json = new ObjectMapper().writeValueAsString(new CustomerDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(2)));
    }

    @Test
    @DisplayName("Must throw an error when there is duplicate CPFs Customers")
    public void createCustomerWithDuplicateCpfTest() throws Exception{

        CustomerDTO customerDto = CustomerDTO.builder().name("John").cpf("000.000.000-00").build();

        String json = new ObjectMapper().writeValueAsString(customerDto);

        BDDMockito.given(service.save(Mockito.any(Customer.class)))
                .willThrow(new BusinessException("CPF já cadastrado"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("errors[0]").value("CPF já cadastrado"));
    }

    @Test
    @DisplayName("Must return the customer details")
    public void getCustomerDetailTest() throws Exception{
        //cenario
        Integer id = 1;
        Customer customer = Customer.builder()
                .idCustomer(id).name("João")
                .cpf("111.111.111-11").build();
        BDDMockito.given(service.getById(id)).willReturn(Optional.of(customer));

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        //verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("idCustomer").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(customer.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("cpf").value(customer.getCpf()));
    }

    @Test
    @DisplayName("Must thrown an error when there is no customer with the specified Id")
    public void customerNotFoundTest() throws Exception{
        //cenario
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.empty());

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        //verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Must delete a customer")
    public void deleteCustomerTest() throws Exception{
        //cenario
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.of(Customer.builder()
                .idCustomer(1).name("João")
                .cpf("111.111.111-11").build()));

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CUSTOMER_API.concat("/" + 1));

        //verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Must return resource not found when not found a costumer by Id to deleting")
    public void deleteInexistentCustomerTest() throws Exception{
        //cenario
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.empty());

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CUSTOMER_API.concat("/" + 1));

        //verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
