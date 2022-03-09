package com.mikaelsonbraz.parkingapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikaelsonbraz.parkingapi.api.customer.dto.CustomerDTO;
import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.customer.service.CustomerService;
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
    @DisplayName("Must throw a error when there is not enough customer data")
    public void createInvalidCustomerTest(){

    }
}
