package com.mikaelsonbraz.parkingapi.api.renting.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mikaelsonbraz.parkingapi.api.renting.dto.RentingDTO;
import com.mikaelsonbraz.parkingapi.api.renting.model.entity.Renting;
import com.mikaelsonbraz.parkingapi.api.renting.service.RentingService;
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

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = RentingController.class)
@AutoConfigureMockMvc
public class RentingControllerTest {

    static String RENTING_API = "/api/rents";

    @Autowired
    MockMvc mvc;

    @MockBean
    RentingService service;

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    @DisplayName("Must create a rent")
    public void createRentingTest() throws Exception{
        //cenário
        LocalDateTime rentDate = LocalDateTime.of(2020, 8, 19, 12, 30, 15);
        RentingDTO dto = RentingDTO.builder().idRenting(1).entryDate(rentDate).hourPrice(10).dayPrice(200).build();
        Renting savedRenting = Renting.builder().idRenting(1).entryDate(rentDate).hourPrice(10).dayPrice(200).build();

        BDDMockito.given(service.save(Mockito.any(Renting.class))).willReturn(savedRenting);
        String json = mapper.writeValueAsString(dto);

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RENTING_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("idRenting").value(dto.getIdRenting()))
                .andExpect(MockMvcResultMatchers.jsonPath("entryDate").value(dto.getEntryDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("hourPrice").value(dto.getHourPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("dayPrice").value(dto.getDayPrice()));
    }

    @Test
    @DisplayName("Must return renting details by id")
    public void getRentingDetailsTest() throws Exception{
        //cenário
        Integer id = 1;
        Renting renting = Renting.builder()
                .idRenting(id)
                .entryDate(LocalDateTime.of(2020, 8, 12, 15, 30, 15))
                .hourPrice(2)
                .dayPrice(20)
                .build();
        BDDMockito.given(service.getById(id)).willReturn(Optional.of(renting));

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(RENTING_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        //verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("idRenting").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("entryDate").value(renting.getEntryDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("hourPrice").value(renting.getHourPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("dayPrice").value(renting.getDayPrice()));
    }

    @Test
    @DisplayName("Must throw Http Status NOT FOUND when ther is no Renting with the specified id")
    public void rentingNotFoundTest() throws Exception{
        //cenário
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.empty());

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(RENTING_API.concat("/1"))
                .accept(MediaType.APPLICATION_JSON);

        //verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Must throw BAD REQUEST when there is no entryDate Renting data on requisition")
    public void createInvalidRentingTest() throws Exception{
        //cenario
        String json = new ObjectMapper().writeValueAsString(new RentingDTO());

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RENTING_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(3)));
    }
}
