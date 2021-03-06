package com.mikaelsonbraz.parkingapi.api.ordered.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mikaelsonbraz.parkingapi.api.customer.model.entity.Customer;
import com.mikaelsonbraz.parkingapi.api.ordered.dto.OrderDTO;
import com.mikaelsonbraz.parkingapi.api.ordered.model.entity.Ordered;
import com.mikaelsonbraz.parkingapi.api.ordered.service.OrderService;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.resource.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.resource.OrderedController;
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

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = OrderedController.class)
public class OrderedControllerTest {

    static String ORDER_API = "/api/orders";

    @Autowired
    MockMvc mvc;

    @MockBean
    OrderService service;

    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public Customer createCustomer(){
        return Customer.builder().idCustomer(1).name("Jo??o").cpf("111.222.333-44").build();
    }

    public ParkingSpace createSpace(){
        return ParkingSpace.builder().idSpace(1).spaceType(1).occupied(true).build();
    }

    public Renting createRenting(){
        return Renting.builder().idRenting(1).entryDate(LocalDateTime.of(2022, 4, 4, 12, 30)).hourPrice(2).dayPrice(40).parkingSpace(createSpace()).build();
    }

    public Ordered createOrder(){
        return Ordered.builder().idOrder(1).customer(createCustomer()).renting(createRenting()).build();
    }

    public OrderDTO createOrderDTO(){
        return OrderDTO.builder().idOrder(1).customer(createCustomer()).renting(createRenting()).build();
    }

    @Test
    @DisplayName("Must create a customer and return a customerDTO")
    public void createOrderTesst() throws Exception{
        //cen??rio
        OrderDTO orderDTO = createOrderDTO();
        Ordered ordered = createOrder();
        BDDMockito.given(service.save(Mockito.any(Ordered.class))).willReturn(ordered);
        String json = objectMapper.writeValueAsString(orderDTO);

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ORDER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("idOrder").value(orderDTO.getIdOrder()));
    }

    @Test
    @DisplayName("Must get a order by id")
    public void getOrderTest() throws Exception{
        //cen??rio
        Ordered ordered = createOrder();
        BDDMockito.given(service.getById(ordered.getIdOrder())).willReturn(Optional.of(ordered));

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ORDER_API.concat("/" + ordered.getIdOrder()))
                .accept(MediaType.APPLICATION_JSON);

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("idOrder").value(ordered.getIdOrder()));
    }

    @Test
    @DisplayName("Must throw Exception when not found an order by id")
    public void orderNotFoundTest() throws Exception{
        //cen??rio
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.empty());

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ORDER_API.concat("/1"))
                .accept(MediaType.APPLICATION_JSON);

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Must update an order")
    public void updateOrderTest() throws Exception{
        //cen??rio
        OrderDTO updatedOrderDTO = OrderDTO.builder().idOrder(1).customer(Customer.builder().idCustomer(2).build()).renting(Renting.builder().idRenting(2).build()).build();
        Ordered ordered = createOrder();
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.of(ordered));
        String json = new ObjectMapper().writeValueAsString(updatedOrderDTO);

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(ORDER_API.concat("/" + updatedOrderDTO.getIdOrder()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("idOrder").value(updatedOrderDTO.getIdOrder()))
                .andExpect(MockMvcResultMatchers.jsonPath("customer").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("renting").isEmpty());
    }

    @Test
    @DisplayName("Update invalid order test")
    public void updateInvalidOrderTest() throws Exception{
        //cen??rio
        OrderDTO orderDTO = new OrderDTO();
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.empty());
        String json = new ObjectMapper().writeValueAsString(orderDTO);

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(ORDER_API.concat("/1"))
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Must delete a order by id")
    public void deleteOrderTest() throws Exception{
        //cen??rio
        Ordered ordered = createOrder();
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.of(ordered));

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(ORDER_API.concat("/1"));

        //execu????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Must throw ResponseStatusException when not found order by id to delete")
    public void deleteInvalidOrderTest() throws Exception{
        //cen??rio
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.empty());

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(ORDER_API.concat("/1"));

        //execu????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
