package com.mikaelsonbraz.parkingapi.api.parkingSpace.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.dto.ParkingSpaceDTO;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.model.entity.ParkingSpace;
import com.mikaelsonbraz.parkingapi.api.parkingSpace.service.ParkingSpaceService;
import com.mikaelsonbraz.parkingapi.api.resource.ParkingSpaceController;
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

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ParkingSpaceController.class)
@AutoConfigureMockMvc
public class ParkingSpaceControllerTest {

    static String SPACE_API = "/api/spaces";

    @Autowired
    MockMvc mvc;

    @MockBean
    ParkingSpaceService service;

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public ParkingSpaceDTO createNewSpaceDTO(){
        return ParkingSpaceDTO.builder().idSpace(1).occupied(false).spaceType(1).build();
    }

    public ParkingSpace createNewSpace(){
        return ParkingSpace.builder().idSpace(1).occupied(false).spaceType(1).build();
    }

    @Test
    @DisplayName("Must create a Parking Space")
    public void createParkingSpaceTest() throws Exception{
        //cen??rio
        ParkingSpaceDTO spaceDTO = createNewSpaceDTO();
        ParkingSpace savedSpace = createNewSpace();
        BDDMockito.given(service.save(Mockito.any(ParkingSpace.class))).willReturn(savedSpace);
        String json = mapper.writeValueAsString(spaceDTO);

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(SPACE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("idSpace").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("occupied").value(savedSpace.isOccupied()))
                .andExpect(MockMvcResultMatchers.jsonPath("spaceType").value(savedSpace.getSpaceType().toString()));
    }

    @Test
    @DisplayName("Must return ParkingSpace details")
    public void getParkingSpaceDetails() throws Exception{
        //cenario
        ParkingSpace space = createNewSpace();
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.of(space));

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(SPACE_API.concat("/1"))
                .accept(MediaType.APPLICATION_JSON);

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("idSpace").value(space.getIdSpace()))
                .andExpect(MockMvcResultMatchers.jsonPath("occupied").value(space.isOccupied()))
                .andExpect(MockMvcResultMatchers.jsonPath("spaceType").value(space.getSpaceType().toString()));
    }

    @Test
    @DisplayName("Must return Http Status Not FOund when not found a parking space by id")
    public void parkingSpaceNotFoundTest() throws Exception{
        //cen??rio
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.empty());

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(SPACE_API.concat("/1"))
                .accept(MediaType.APPLICATION_JSON);

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Must delete a Parking Space")
    public void deleteParkingSpaceTest() throws Exception{
        //cen??rio
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.of(createNewSpace()));

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(SPACE_API.concat("/1"));

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Must return Http Status Not Found when trying delete not found parking space by id")
    public void deleteNonexistentParkingSPaceTest() throws Exception{
        //cen??rio
        BDDMockito.given(service.getById(Mockito.anyInt())).willReturn(Optional.empty());

        //execu????o
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(SPACE_API.concat("/1"));

        //verifica????o
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
